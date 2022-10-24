package com.example.synthesizer;

import javafx.scene.layout.AnchorPane;

public class FilterWidget extends AudioComponentWidget{
    Filter filter;

    FilterWidget(AnchorPane parent) {
        super(parent, "Volume", "");
        setSlider(0.0, 1.0, 0.5);
        setTitle();
        this.setLayoutX(50);
        this.setLayoutY(350);
        filter = new Filter(0.5);
        audioComponent_ = filter;
    }

    protected void handleSliderDragged() {
        super.handleSliderDragged();
        filter.setScale(slider_.getValue());
    }

    @Override
    public boolean acceptInput() {
        return true;
    }
}
