package com.weaver.inte.jna.common.data;

import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;


/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/26 16:11
 */
public class VideoFrameV2Data extends Structure {
    public int xres;
    public int yres;
    public IntByReference FourCC;
    public int frame_rate_N;
    public int frame_rate_D;
    public float picture_aspect_ratio;
    public IntByReference frame_format_type;
    public long timecode;
    public byte[] p_data = new byte[64];
    public int line_stride_in_bytes;
    public int data_size_in_bytes;
    public String p_metadata;
    public long timestamp;

    public VideoFrameV2Data(){

    }
//    public VideoFrameV2Data(int xres, int yres, IntByReference fourCC, int frame_rate_N, int frame_rate_D,
//                            float picture_aspect_ratio, IntByReference frame_format_type,
//                            long timecode, byte[] p_data, int line_stride_in_bytes,
//                            String p_metadata, long timestamp) {
//        this.xres = xres;
//        this.yres = yres;
//        FourCC = fourCC;
//        this.frame_rate_N = frame_rate_N;
//        this.frame_rate_D = frame_rate_D;
//        this.picture_aspect_ratio = picture_aspect_ratio;
//        this.frame_format_type = frame_format_type;
//        this.timecode = timecode;
//        this.p_data = p_data;
//        this.line_stride_in_bytes = line_stride_in_bytes;
//        this.data_size_in_bytes = data_size_in_bytes;
//        this.p_metadata = p_metadata;
//        this.timestamp = timestamp;
//    }
}
