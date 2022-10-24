package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;

public class SquareWaveWidget extends AudioComponentWidget{
    SquareWave squareWave;

    SquareWaveWidget(AnchorPane parent) {
        super(parent, "Square Wave", "Hz");
        setSlider(220, 880, 440);
        setTitle();
        this.setLayoutX(300);
        this.setLayoutY(80);
        squareWave = new SquareWave(440);
        audioComponent_ = squareWave;
    }

    protected void handleSliderDragged() {
        super.handleSliderDragged();
        squareWave.setFrequency((int) slider_.getValue());
    }
}
