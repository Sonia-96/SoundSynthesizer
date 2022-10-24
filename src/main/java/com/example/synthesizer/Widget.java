package com.example.synthesizer;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public interface Widget {
    AudioComponent getAudioComponent();
    boolean hasInput();
    void connectInput(Widget input);
    void disconnectInput(Widget input);
    boolean acceptInput();
    Circle getInputCircle();
    Line getLine();
}
