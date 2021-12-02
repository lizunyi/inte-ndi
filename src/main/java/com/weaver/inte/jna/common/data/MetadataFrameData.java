package com.weaver.inte.jna.common.data;

import com.sun.jna.Structure;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/12/01 13:41
 */
public class MetadataFrameData extends Structure {
    public int length;
    public long timecode;
    public String p_data;

    public MetadataFrameData() {
    }

    public MetadataFrameData(int length, long timecode, String p_data) {
        this.length = length;
        this.timecode = timecode;
        this.p_data = p_data;
    }
}
