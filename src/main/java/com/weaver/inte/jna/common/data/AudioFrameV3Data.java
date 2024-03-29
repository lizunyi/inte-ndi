package com.weaver.inte.jna.common.data;

import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/26 16:11
 */
public class AudioFrameV3Data extends Structure {
    public int sample_rate;
    public int no_channels;
    public int no_samples;
    public long timecode;
    public IntByReference FourCC;
    public byte[] p_data = new byte[8];
    public int channel_stride_in_bytes;
    public int data_size_in_bytes;
    public String p_metadata;
    public long timestamp;
}
