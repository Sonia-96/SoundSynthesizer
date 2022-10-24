package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;

public class WhiteNoiseWidget extends AudioComponentWidget{
    WhiteNoise whiteNoise;

    WhiteNoiseWidget(AnchorPane parent) {
        super(parent, "White Noise", "");
        setTitle();
        this.setLayoutX(50);
        this.setLayoutY(200);
        whiteNoise = new WhiteNoise();
        audioComponent_ = whiteNoise;
    }
}
