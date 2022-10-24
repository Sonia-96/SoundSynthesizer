package com.example.synthesizer;

import java.util.ArrayList;

public class Mixer implements AudioComponent{
    ArrayList<AudioComponent> inputs;

    Mixer() {
        inputs = new ArrayList<>();
    }

    @Override
    public AudioClip getClip() {
        AudioClip result = new AudioClip();
        ArrayList<AudioClip> clips = new ArrayList<>();
        for (AudioComponent input : inputs) {
            clips.add(input.getClip());
        }
        int numSamples = AudioClip.SAMPLE_RATE * AudioClip.DURATION;
        for (int i = 0; i < numSamples; i++) {
            int sum = 0;
            for (AudioClip clip : clips) {
                sum += clip.getSample(i);
            }
            result.setSample(i, clamp(sum));
        }
        return result;
    }

    @Override
    public boolean hasInput() {
        return inputs.size() == 0;
    }

    @Override
    public void connectInput(AudioComponent input) {
        inputs.add(input);
    }

    public void disconnectInput(AudioComponent input) {
        inputs.remove(input);
    }
}
