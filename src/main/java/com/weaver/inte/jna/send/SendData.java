package com.weaver.inte.jna.send;

import com.sun.jna.Structure;
import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.NdiSourceData;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/25 17:00
 */
public class SendData extends Structure {
    public String p_ndi_name;
    public String p_groups;
    public boolean clock_video;
    public boolean clock_audio;

    public SendData(String p_ndi_name, String p_groups, boolean clock_video, boolean clock_audio) {
        this.p_ndi_name = p_ndi_name;
        this.p_groups = p_groups;
        this.clock_video = clock_video;
        this.clock_audio = clock_audio;
    }
}
