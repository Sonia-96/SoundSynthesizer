package com.example.synthesizer;

public class SineWave implements AudioComponent{
    private int frequency;

    SineWave(int pitch) {
        frequency = pitch;
    }

    @Override
    public AudioClip getClip() {
        System.out.println("frequency is: " + frequency);
        int numSamples = AudioClip.SAMPLE_RATE * AudioClip.DURATION;
        AudioClip clip = new AudioClip();
        for (int i = 0; i < numSamples; i++) {
            clip.setSample(i, (int) (Short.MAX_VALUE * Math.sin(2 * Math.PI * frequency * i / AudioClip.SAMPLE_RATE)));
        }
        return clip;
    }

    @Override
    public boolean hasInput() {
        return false;
    }

    @Override
    public void connectInput(AudioComponent input) {
        assert(false); // TODO: why???
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int f) {
        frequency = f;
    }
}
