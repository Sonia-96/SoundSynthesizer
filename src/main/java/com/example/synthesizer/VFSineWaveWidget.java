package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;

public class VFSineWaveWidget extends AudioComponentWidget{
    VFSineWave vfSineWave;

    VFSineWaveWidget(AnchorPane parent) {
        super(parent, "VF Sine Wave", "");
        setTitle();
        this.setLayoutX(300);
        this.setLayoutY(350);
        vfSineWave = new VFSineWave();
        audioComponent_ = vfSineWave;
    }

    @Override
    public boolean acceptInput() {
        return true;
    }
}
