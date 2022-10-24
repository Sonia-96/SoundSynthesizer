package com.example.synthesizer;
import javafx.scene.layout.AnchorPane;

public class SineWaveWidget extends AudioComponentWidget{
    SineWave sineWave;
    SineWaveWidget(AnchorPane parent) {
        super(parent, "Sine Wave", " Hz");
        setSlider(220, 880, 440);
        setTitle();
        this.setLayoutX(50);
        this.setLayoutY(80);
        sineWave = new SineWave(440);
        audioComponent_ = sineWave;
    }

    protected void handleSliderDragged() {
        super.handleSliderDragged();
        sineWave.setFrequency((int) slider_.getValue());
    }
}
