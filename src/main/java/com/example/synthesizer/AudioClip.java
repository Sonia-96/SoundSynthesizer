package com.example.synthesizer;

import java.util.Arrays;

public class AudioClip {
    static final int DURATION = 2;
    static final int SAMPLE_RATE = 44100;
    private byte[] data;

    public AudioClip() {
        data = new byte[DURATION * SAMPLE_RATE * 2];
    }

    // return index * 2 and index * 2 + 1 byte
    public int getSample(int index) {
//        int upper = data[index * 2 + 1] << 8; // maintain the sign
//        int lower = data[index * 2] & 0xFF;
//        return upper | lower; // x | 0 = x
        return (data[index * 2 + 1]) << 8 | (data[index * 2] & 0xFF);
    }

    public void setSample(int index, int value) {
        data[index * 2] = (byte) (value & 0xFF);
        data[index * 2 + 1] = (byte) ((value & 0xFF00) >>> 8); // unsigned right shift
    }

    public byte[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    public int getLength() {
        return data.length;
    }
}
