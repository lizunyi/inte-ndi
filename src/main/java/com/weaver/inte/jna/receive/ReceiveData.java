package com.weaver.inte.jna.receive;

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.NdiSourceData;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/25 17:00
 */
public class ReceiveData extends Structure {
    public NdiSourceData source_to_connect_to;
    public IntByReference color_format;
    public IntByReference bandwidth;
    public boolean allow_video_fields = true;
    public String p_ndi_recv_name;

    public static class ByReference extends ReceiveData implements Structure.ByReference {
    }

    public static class ByValue extends ReceiveData implements Structure.ByValue {
    }

    public ReceiveData() {

    }

    public ReceiveData(NdiSourceData source_to_connect_to, IntByReference color_format, IntByReference bandwidth, boolean allow_video_fields, String p_ndi_recv_name) {
        this.source_to_connect_to = source_to_connect_to;
        this.color_format = color_format;
        this.bandwidth = bandwidth;
        this.allow_video_fields = allow_video_fields;
        this.p_ndi_recv_name = p_ndi_recv_name;
    }
}
