package com.example.synthesizer;

public class Filter implements AudioComponent{
    // TODO: add clamp boolean
    protected AudioComponent input;
    private double scale;

    Filter(double scale) {
        input = null;
        this.scale = scale;
    }

    @Override
    public AudioClip getClip() {
        System.out.println("scale is: " + scale);
        AudioClip clip = input.getClip();
        AudioClip result = new AudioClip();
        int numSamples = AudioClip.SAMPLE_RATE * AudioClip.DURATION;
        for (int i = 0; i < numSamples; i++) {
            result.setSample(i, clamp((int) (scale * clip.getSample(i))));
        }
        return result;
    }

    @Override
    public boolean hasInput() {
        return input == null;
    }

    @Override
    public void connectInput(AudioComponent input) {
        this.input = input;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public AudioComponent getInput() {
        return input;
    }
}
