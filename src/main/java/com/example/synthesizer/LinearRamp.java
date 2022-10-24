package com.example.synthesizer;

public class LinearRamp implements AudioComponent{
    private int start;
    private int stop;

    LinearRamp(int s, int e) {
        start = s;
        stop = e;
    }
    @Override
    public AudioClip getClip() {
        AudioClip res = new AudioClip();
        int numSamples = AudioClip.SAMPLE_RATE * AudioClip.DURATION;
        for (int i = 0; i < numSamples; i++) {
            res.setSample(i, (( start * ( numSamples - i ) + stop * i )/ numSamples));
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

    public void setStop(int s) {
        stop = s;
    }

    public void setStart(int s) {
        start = s;
    }
}
