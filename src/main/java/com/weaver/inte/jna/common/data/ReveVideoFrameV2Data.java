package com.weaver.inte.jna.common.data;

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

import java.nio.ByteBuffer;


/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/26 16:11
 */
public class ReveVideoFrameV2Data extends Structure {
    public int xres = 0;
    public int yres = 0;
    public IntByReference FourCC;
    public int frame_rate_N = 30000;
    public int frame_rate_D = 1001;
    public float picture_aspect_ratio;
    public IntByReference frame_format_type = new IntByReference(1);
    public long timecode;
    public byte[] p_data = new byte[8];
    public int line_stride_in_bytes;
    public int data_size_in_bytes;
    public String p_metadata;
    public long timestamp;
}
