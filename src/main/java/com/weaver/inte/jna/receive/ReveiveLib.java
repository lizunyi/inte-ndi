package com.weaver.inte.jna.receive;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.AudioFrameV2Data;
import com.weaver.inte.jna.common.data.AudioFrameV3Data;
import com.weaver.inte.jna.common.data.LibCore;
import com.weaver.inte.jna.common.data.MetadataFrameData;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.common.data.VideoFrameV2Data;

import java.io.IOException;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/25 16:58
 */
public class ReveiveLib implements AutoCloseable {
    public final long insPoint;
    static ReveiveInterface instance;

    static {
        instance =  LibCore.loadLibrary(ReveiveInterface.class);
    }

    public ReveiveLib(ReceiveData ins) {
        System.out.println("init reveice");
        this.insPoint = instance.NDIlib_recv_create_v3(ins);
    }

    public void connect(NdiSourceData sourceData) {
        System.out.println("connect reveice");
        instance.NDIlib_recv_connect(this.insPoint, sourceData);
    }

    public IntByReference recvCaptureV2(VideoFrameV2Data p_video_data, AudioFrameV2Data p_audio_data, MetadataFrameData p_metadata, int timeout_in_ms) {
        System.out.println("reveice data");
        return instance.NDIlib_recv_capture_v2(this.insPoint, p_video_data, p_audio_data, p_metadata, timeout_in_ms);
    }

    public void recvFreeVideoV2(VideoFrameV2Data p_video_data) {
        instance.NDIlib_recv_free_video_v2(this.insPoint, p_video_data);
    }

    public int recvCaptureV3(VideoFrameV2Data p_video_data, AudioFrameV3Data p_audio_data, MetadataFrameData p_metadata, int timeout_in_ms) {
        return instance.NDIlib_recv_capture_v3(this.insPoint, p_video_data, p_audio_data, p_metadata, timeout_in_ms);
    }

    public String recvGetWebControl() {
        return instance.NDIlib_recv_get_web_control(this.insPoint);
    }

    public void recvFreeString(String p_string) {
        instance.NDIlib_recv_free_string(this.insPoint,p_string);
    }

    @Override
    public void close() throws IOException {
        instance.NDIlib_recv_destroy(this.insPoint);
        System.out.println("destroy receive");
    }

    public interface ReveiveInterface extends Library {

        long NDIlib_recv_create_v3(ReceiveData ins);

        void NDIlib_recv_destroy(long insPoint);

        void NDIlib_recv_connect(long insPoint, NdiSourceData sourceData);

        IntByReference NDIlib_recv_capture_v2(long insPoint, VideoFrameV2Data p_video_data, AudioFrameV2Data p_audio_data, MetadataFrameData p_metadata, int timeout_in_ms);

        void NDIlib_recv_free_video_v2(long insPoint, VideoFrameV2Data p_video_data);

        int NDIlib_recv_capture_v3(long insPoint, VideoFrameV2Data p_video_data, AudioFrameV3Data p_audio_data, MetadataFrameData p_metadata, int timeout_in_ms);

        String NDIlib_recv_get_web_control(long insPoint);

        void NDIlib_recv_free_string(long insPoint,String p_string);

    }
}
