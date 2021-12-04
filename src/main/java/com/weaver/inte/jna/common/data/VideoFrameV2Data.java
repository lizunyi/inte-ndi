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
}
