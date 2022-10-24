package com.example.synthesizer;

import java.util.Random;

public class WhiteNoise implements AudioComponent{
    @Override
    public AudioClip getClip() {
        AudioClip res = new AudioClip();
        Random rand = new Random();
        int numSamples = AudioClip.SAMPLE_RATE * AudioClip.DURATION;
        for (int i = 0; i < numSamples; i++) {
            res.setSample(i, rand.nextInt(Short.MAX_VALUE - Short.MIN_VALUE + 1) + Short.MIN_VALUE);
        }
        return res;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        assert(false);
    }
}
