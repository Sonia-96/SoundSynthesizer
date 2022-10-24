package com.example.synthesizer;

public class SquareWave implements AudioComponent {
    private int frequency;

    SquareWave(int pitch) {
        frequency = pitch;
    }

    @Override
    public AudioClip getClip() {
        AudioClip res = new AudioClip();
        int numSamples = AudioClip.SAMPLE_RATE * AudioClip.DURATION;
        for (int i = 0; i < numSamples; i++) {
            if( (frequency * i * 1.0/ AudioClip.SAMPLE_RATE) % 1  > 0.5) {
                res.setSample(i, Short.MAX_VALUE);
            } else {
                res.setSample(i, Short.MIN_VALUE);
            }
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

    public void setFrequency(int f) {
        frequency = f;
    }
}
