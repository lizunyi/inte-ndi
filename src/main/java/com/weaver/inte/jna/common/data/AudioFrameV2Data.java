package com.weaver.inte.jna.common.data;

import com.sun.jna.Structure;
import com.sun.jna.ptr.FloatByReference;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/26 16:11
 */
public class AudioFrameV2Data extends Structure {
    public int sample_rate;
    public int no_channels;
    public int no_samples;
    public long timecode;
    public FloatByReference p_data;
    public int channel_stride_in_bytes;
    public String p_metadata;
    public long timestamp;
}
