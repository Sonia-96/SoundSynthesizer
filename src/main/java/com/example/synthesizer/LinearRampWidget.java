package com.example.synthesizer;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class LinearRampWidget extends AudioComponentWidget {
    LinearRamp linearRamp;
    Slider startSlider_;
    Slider endSlider_;

    LinearRampWidget(AnchorPane parent) {
        super(parent, "Linear Ramp", "");
        setTitle();
        setStartSlider();
        this.setLayoutX(300);
        this.setLayoutY(200);
        linearRamp = new LinearRamp(50, 2000);
        audioComponent_ = linearRamp;
    }

    @Override
    protected void setSlider(double min, double max, double initial) {
        super.setSlider(min, max, initial);
    }

    protected  void setStartSlider() {
        HBox startBox = new HBox();
        Label  startCaption = new Label("start: ");
        startSlider_ = new Slider(50, 10000, 50);
        Label startValue = new Label(Integer.toString((int) startSlider_.getValue()));
        startSlider_.setOnMouseDragged(e -> setSliderChanged(startSlider_, startValue, "start"));
        startSlider_.setOnMouseClicked(e -> setSliderChanged(startSlider_, startValue, "start"));
        startBox.getChildren().add(startCaption);
        startBox.getChildren().add(startSlider_);
        startBox.getChildren().add(startValue);


        HBox endBox = new HBox();
        Label  endCaption = new Label("end: ");
        endSlider_ = new Slider(50, 10000, 2000);
        Label endValue = new Label(Integer.toString((int) endSlider_.getValue()));
        endSlider_.setOnMouseDragged(e -> setSliderChanged(endSlider_, endValue, "end"));
        endSlider_.setOnMouseClicked(e -> setSliderChanged(endSlider_, endValue, "end"));
        endBox.getChildren().add(endCaption);
        endBox.getChildren().add(endSlider_);
        endBox.getChildren().add(endValue);

        center_.getChildren().add(startBox);
        center_.getChildren().add(endBox);

    }

    private void setSliderChanged(Slider slider, Label value, String type) {
        value.setText(String.valueOf((int) slider.getValue()));
        if (type.equals("start")) {
            linearRamp.setStart((int) startSlider_.getValue());
        } else {
            linearRamp.setStop((int) endSlider_.getValue());
        }
    }
}
