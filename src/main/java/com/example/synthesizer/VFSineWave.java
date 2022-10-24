package com.example.synthesizer;

public class VFSineWave implements AudioComponent{
    AudioComponent input = null;

    @Override
    public AudioClip getClip() {
        AudioClip inputClip = input.getClip();
        AudioClip res = new AudioClip();
        int numSamples = AudioClip.DURATION * AudioClip.SAMPLE_RATE;
        float phase = 0; //TODO what is integer division overflow?
        for (int i = 0; i < numSamples; i++) {
            phase += 2 * Math.PI * inputClip.getSample(i) / AudioClip.SAMPLE_RATE;
            res.setSample(i, (int) (Short.MAX_VALUE * Math.sin(phase)));
        }
        return res;
    }

    @Override
    public boolean hasInput() {
        return input == null;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input = input;
    }
}
