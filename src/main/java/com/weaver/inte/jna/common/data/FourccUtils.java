package com.weaver.inte.jna.common.data;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/12/02 18:55
 */
public class FourccUtils {
    public static int getFourCC(char c1, char c2, char c3, char c4) {
        return (c1 & 0xFF) | ((c2 & 0xFF) << 8) | ((c3 & 0xFF) << 16) | ((c4 & 0xFF) << 24);
    }
}
