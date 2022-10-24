package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class SpeakerWidget implements Widget{
    protected ArrayList<Widget> inputs_;
    protected Circle speakerCircle_;
    private Mixer mixer_;

    SpeakerWidget(AnchorPane parent) {
        speakerCircle_ = new Circle(20);
        speakerCircle_.setFill(Color.LIGHTPINK);
        speakerCircle_.setLayoutX(600);
        speakerCircle_.setLayoutY(200);
        parent.getChildren().add(speakerCircle_);
        inputs_ = new ArrayList<>();
        mixer_ = new Mixer();
    }

    @Override
    public AudioComponent getAudioComponent() {
        return mixer_;
    }

    @Override
    public boolean hasInput() {
        return mixer_.hasInput();
    }

    @Override
    public void connectInput(Widget input) {
        inputs_.add(input);
        mixer_.connectInput(input.getAudioComponent());
    }

    @Override
    public void disconnectInput(Widget input) {
        inputs_.remove(input);
        mixer_.disconnectInput(input.getAudioComponent());
    }

    @Override
    public boolean acceptInput() {
        return true;
    }

    @Override
    public Circle getInputCircle() {
        return speakerCircle_;
    }

    @Override
    public Line getLine() {
        return null;
    }
}
