package com.weaver.inte.jna.common.data;

import com.sun.jna.Structure;
import com.sun.jna.Union;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/11/25 11:42
 */
public class NdiSourceData extends Structure {

    public String p_ndi_name;
    public String p_url_address;
    public String p_ip_address;
}