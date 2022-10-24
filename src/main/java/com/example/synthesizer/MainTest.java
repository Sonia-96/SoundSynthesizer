package com.example.synthesizer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.LineUnavailableException;

public class MainTest {
    AudioComponent sin1 = new SineWave(440);
    AudioComponent sin2 = new SineWave(200);
    AudioComponent square = new SquareWave(440);

    @Test
    public void testAudioClip() {
        AudioClip clip = new AudioClip();
        for (int value = Short.MIN_VALUE, j = 0; value <= Short.MAX_VALUE; value++, j++) {
            clip.setSample(j, value);
            Assertions.assertEquals(value, clip.getSample(j));
        }
    }

    @Test
    public void testSineWave() throws LineUnavailableException {
        Main.playAudioClip(sin1.getClip());
    }

    @Test
    public void testSquareWave() throws LineUnavailableException {
        Main.playAudioClip(square.getClip());
    }

    @Test
    public void testFilter() throws LineUnavailableException {
        AudioComponent volumeAdjuster = new Filter(0.5f);
        volumeAdjuster.connectInput(sin1);
        Main.playAudioClip(volumeAdjuster.getClip());
    }

    @Test
    public void testMixer() throws LineUnavailableException {
        AudioComponent volumeAdjuster1 = new Filter(0.2f);
        volumeAdjuster1.connectInput(sin1);
        AudioComponent volumeAdjuster2 = new Filter(0.3f);
        volumeAdjuster2.connectInput(sin2);
        Mixer mixer = new Mixer();
        mixer.connectInput(volumeAdjuster1);
        mixer.connectInput(volumeAdjuster2);
        Main.playAudioClip(mixer.getClip());
    }

    @Test
    public void testVFWave() throws LineUnavailableException {
        LinearRamp ramp = new LinearRamp(50, 2000);
        VFSineWave wave = new VFSineWave();
        wave.connectInput(ramp);
        Main.playAudioClip(wave.getClip());
    }
}
