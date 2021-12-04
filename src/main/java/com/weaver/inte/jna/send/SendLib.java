package com.weaver.inte.jna.send;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.LibCore;
import com.weaver.inte.jna.common.data.MetadataFrameData;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.common.data.VideoFrameV2Data;
import com.weaver.inte.jna.receive.ReceiveData;

import java.io.IOException;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/25 16:58
 */
public class SendLib implements AutoCloseable {
    public final long insPoint;
    static SendInterface instance;

    static {
        instance =  LibCore.loadLibrary(SendInterface.class);
    }

    public SendLib(SendData ins) {
        System.out.println("init send");
        this.insPoint = instance.NDIlib_send_create(ins);
    }

    public void sendVideoV2(VideoFrameV2Data p_video_data){
        instance.NDIlib_send_send_video_v2(this.insPoint,p_video_data);
    }

    public void sendAudioV2(AudioFrameV2Data p_audio_data) {
        instance.NDIlib_send_send_audio_v2(this.insPoint, p_audio_data);
    }

    public void sendMetadata(MetadataFrameData p_metadata) {
        instance.NDIlib_send_send_metadata(this.insPoint, p_metadata);
    }

    public void sendAddConnectionMetadata(MetadataFrameData p_metadata) {
        instance.NDIlib_send_add_connection_metadata(this.insPoint, p_metadata);
    }

    @Override
    public void close() throws IOException {
        instance.NDIlib_send_destroy(this.insPoint);
        System.out.println("destroy send");
    }

    public interface SendInterface extends Library {

        long NDIlib_send_create(SendData ins);

        void NDIlib_send_destroy(long insPoint);

        void NDIlib_send_send_video_v2(long insPoint, VideoFrameV2Data p_video_data);

        void NDIlib_send_send_audio_v2(long insPoint, AudioFrameV2Data p_video_data);

        void NDIlib_send_send_metadata(long insPoint, MetadataFrameData p_metadata);

        IntByReference NDIlib_send_capture(long insPoint, MetadataFrameData p_metadata,int timeout_in_ms);

        void NDIlib_send_add_connection_metadata(long insPoint, MetadataFrameData p_metadata);
    }
}
