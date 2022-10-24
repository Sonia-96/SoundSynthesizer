package com.example.synthesizer;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javafx.scene.input.MouseEvent;

import java.util.Formatter;

public abstract class AudioComponentWidget extends Pane implements Widget { // this object is a Pane!
    protected Widget input_;
    protected AudioComponent audioComponent_;
    private final AnchorPane parent_;
    protected VBox center_;
    protected Slider slider_;
    protected Label title_;
    private final String name_;
    private final String unit_;
    private double mouseStartDragX_, mouseStartDragY_, widgetStartDragX_, widgetStartDragY_;
    protected Line line_;
    private Circle inputCircle;
    private Circle outputCircle;
    private Widget connectedTo;

    AudioComponentWidget(AnchorPane parent, String name, String unit) {
        parent_ = parent;
        name_ = name;
        unit_ = unit;

        HBox baseLayout = new HBox();
        baseLayout.setStyle("-fx-border-color: darkgrey; -fx-border-width: 3; -fx-background-color: white");

        // set left side
        VBox leftSide = new VBox();
        leftSide.setSpacing(5);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setPadding(new Insets(5));
        if (acceptInput()) {
            inputCircle = new Circle(8, Color.PINK);
            leftSide.getChildren().add(inputCircle);
        }

        //set center
        center_ = new VBox();
        center_.setAlignment(Pos.CENTER);
        center_.setOnMousePressed(this::startDrag);
        center_.setOnMouseDragged(this::handleWidgetDragged);

        // set right side
        VBox rightSide = new VBox();
        rightSide.setSpacing(5);
        rightSide.setAlignment(Pos.CENTER);
        rightSide.setPadding(new Insets(5));
        Button closeBtn = new Button("x");
        closeBtn.setOnMousePressed(e -> closeWidget());
        outputCircle = new Circle(8, Color.LIGHTBLUE);
        outputCircle.setOnMousePressed(e -> startConnection(e, outputCircle));
        outputCircle.setOnMouseDragged(this::moveConnection);
        outputCircle.setOnMouseReleased(this::endConnection);
        rightSide.getChildren().add(closeBtn);
        rightSide.getChildren().add(outputCircle);

        baseLayout.getChildren().add(leftSide);
        baseLayout.getChildren().add(center_);
        baseLayout.getChildren().add(rightSide);

        this.getChildren().add(baseLayout);
        parent.getChildren().add(this);
    }

    protected void setTitle() {
        title_ = new Label();
        title_.setMouseTransparent(true);
        if (slider_ != null) {
            title_.setText(name_ + " (" + slider_.getValue() + unit_ + ")");
        } else {
            title_.setText(name_);
        }
        center_.getChildren().add(title_);
        if (slider_ != null) {
            center_.getChildren().add(slider_);
        }
    }

    protected void setSlider(double min, double max, double initial) {
        slider_ = new Slider(min, max, initial);
        slider_.setOnMouseDragged(e -> handleSliderDragged());
        slider_.setOnMouseClicked(e -> handleSliderDragged());
    }

    protected void handleSliderDragged() {
        Formatter fm = new Formatter();
        double value = slider_.getValue();
        fm.format("%.1f", value);
        title_.setText(name_ + " (" + fm + unit_ + ")");
    }

    private void startConnection(MouseEvent e, Circle circle) {
        if (line_ != null) {
            parent_.getChildren().remove(line_);
            connectedTo.disconnectInput(this);
        }

        Bounds parentBounds = parent_.getBoundsInParent();
        Bounds bounds = circle.localToScene(circle.getBoundsInLocal());
        // set line
        line_ = new Line();
        line_.setStrokeWidth(2.5);
        line_.setStartX(bounds.getCenterX() - parentBounds.getMinX()); // TODO why use pane bounds?
        line_.setStartY(bounds.getCenterY() - parentBounds.getMinY());
        line_.setEndX(e.getSceneX());
        line_.setEndY(e.getSceneY());
        // show the line
        parent_.getChildren().add(line_);
    }

    private void moveConnection(MouseEvent e) {
        Bounds parentBounds = parent_.getBoundsInParent();
        line_.setEndX(e.getSceneX() - parentBounds.getMinX());
        line_.setEndY(e.getSceneY() - parentBounds.getMinX());
//        line_.setEndX(e.getSceneX());
//        line_.setEndY(e.getSceneY());
    }

    private void endConnection(MouseEvent e) {
        double minDist = 100.0;
        Widget minDistWidget = null;
        for (Widget widget: SynthesizeApplication.widgets_) {
            Circle inputCircle = widget.getInputCircle();
            if (inputCircle != null) {
                Bounds inputBounds = inputCircle.localToScene(inputCircle.getBoundsInLocal());
                double distance = Math.sqrt(Math.pow(inputBounds.getCenterX() - e.getSceneX(), 2.0) + Math.pow(inputBounds.getCenterY() - e.getSceneY(), 2.0));
                System.out.println(distance);
                if (distance < minDist) {
                    minDist = distance;
                    minDistWidget = widget;
                }
            }
        }

        System.out.println(minDist);
        boolean connected = false;
        if (minDist < 10) {
            connected = connectOutput(minDistWidget);
        }
        if (connected) {
            line_.setOnMouseClicked(this::removeConnection);
        } else {
            parent_.getChildren().remove(line_);
            line_ = null;
        }
    }

    // double click to disconnect two widgets
    private void removeConnection(MouseEvent e) {
        if(e.getButton().equals(MouseButton.PRIMARY)){
            if(e.getClickCount() == 2){
                parent_.getChildren().remove(line_);
                connectedTo.disconnectInput(this);
            }
        }
    }

    /*
        Try to connect this widget with an output widget
        Under these cases, the connection will fail and return false:
        - The output widget is a filter and already has an input
        Otherwise, connection succeed and return true.
     */
    private boolean connectOutput(Widget widget) {
        if (widget instanceof FilterWidget && widget.hasInput()) {
            System.out.println("The filter already has an input!");
            return false;
        }
        connectedTo = widget;
        connectedTo.connectInput(this);
        return true;
    }

    private void startDrag(MouseEvent e) {
        mouseStartDragX_ = e.getSceneX();
        mouseStartDragY_ = e.getSceneY();
        widgetStartDragX_ = this.getLayoutX();
        widgetStartDragY_ = this.getLayoutY();
    }

    private void handleWidgetDragged(MouseEvent e) {
        double mouseDelX = e.getSceneX() - mouseStartDragX_;
        double mouseDelY = e.getSceneY() - mouseStartDragY_;
        this.relocate(widgetStartDragX_ + mouseDelX, widgetStartDragY_ + mouseDelY);
        // change the position of line
        if (hasInput()) {
            Line line = input_.getLine();
            Bounds inputBounds = inputCircle.localToScene(inputCircle.getBoundsInLocal());
            line.setEndX(inputBounds.getCenterX());
            line.setEndY(inputBounds.getCenterY());
        }
        if (line_ != null) {
            Bounds outputBounds = outputCircle.localToScene(outputCircle.getBoundsInLocal());
            line_.setStartX(outputBounds.getCenterX());
            line_.setStartY(outputBounds.getCenterY());
        }
    }

    private void closeWidget() {
        parent_.getChildren().remove(this);
        parent_.getChildren().remove(line_);
        if (hasInput()) {
            parent_.getChildren().remove(input_.getLine());
        }
        SynthesizeApplication.widgets_.remove(this); // TODO terrible way
    }

    public Circle getInputCircle() {
        return inputCircle;
    }

    public Line getLine() {return line_;}

    @Override
    public AudioComponent getAudioComponent() {
        return audioComponent_;
    }

    @Override
    public boolean hasInput() {
        return input_ != null;
    }

    @Override
    public void connectInput(Widget input) {
        input_ = input;
        audioComponent_.connectInput(input_.getAudioComponent());
    }

    @Override
    public void disconnectInput(Widget input) {
        input_ = null;
        audioComponent_.connectInput(null);
    }

    @Override
    public boolean acceptInput() {
        return false;
    }
}
