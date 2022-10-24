package com.example.synthesizer;

import javax.sound.sampled.*;

public class Main {
    static Clip c;

    // Get properties from the system about samples rates, etc.
    // AudioSystem is a class from the Java standard library.
    static {
        try {
            c = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    static AudioFormat format16 = new AudioFormat( 44100, 16, 1, true, false );

    static void playAudioClip(AudioClip clip) throws LineUnavailableException {
        c.open( format16, clip.getData(), 0, clip.getData().length ); // Reads data from our byte array to play it.
        System.out.println( "About to play ..." );
        c.start(); // Plays it.
        c.loop( 1 ); // Plays it 2 more times if desired, so 6 seconds total
        // Makes sure the program doesn't quit before the sound plays.
        while( c.getFramePosition() < AudioClip.SAMPLE_RATE || c.isActive() || c.isRunning() ){
            // Do nothing while we wait for the note to play.
        }
        c.close();
    }
    public static void main(String[] args) throws LineUnavailableException {
        System.out.println( "Done." );
    }
}
