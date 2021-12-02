package com.weaver.inte.jna.frame;

import com.sun.jna.Library;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.LibCore;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/12/02 13:31
 */
public class FrameLib implements AutoCloseable {
    final long insPoint;
    static FrameInterface instance;

    static {
        instance = LibCore.loadLibrary(FrameInterface.class);
    }

    public FrameLib(long recvInsPoint) {
        System.out.println("init frame");
        this.insPoint = instance.NDIlib_framesync_create(recvInsPoint);
    }

    public void captureAudio(AudioFrameV2Data p_audio_data, int sample_rate, int no_channels, int no_samples) {
        instance.NDIlib_framesync_capture_audio(this.insPoint, p_audio_data, sample_rate, no_channels, no_samples);
    }

    @Override
    public void close() throws Exception {
        instance.NDIlib_framesync_destroy(this.insPoint);
        System.out.println("destroy frame");
    }

    interface FrameInterface extends Library {
        long NDIlib_framesync_create(long recvInsPoint);

        void NDIlib_framesync_destroy(long insPoint);

        void NDIlib_framesync_capture_audio(long insPoint, AudioFrameV2Data p_audio_data, int sample_rate, int no_channels, int no_samples);

        void NDIlib_framesync_capture_audio_v2(long insPoint, AudioFrameV3Data p_audio_data, int sample_rate, int no_channels, int no_samples);
    }
}
