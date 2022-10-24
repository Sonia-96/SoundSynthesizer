package com.example.synthesizer;

public interface AudioComponent {
    AudioClip getClip();
    boolean hasInput();
    // connect another device to this input
    void connectInput(AudioComponent input);

    default int clamp(int volume) {
        if (volume > Short.MAX_VALUE) {
            return Short.MAX_VALUE;
        } else if (volume < Short.MIN_VALUE) {
            return Short.MIN_VALUE;
        }
        return volume;
    }
}
