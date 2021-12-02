package com.weaver.inte.jna.common.data;

import com.sun.jna.Native;

/**
 * @author lzy
 * @version v1.0
 * @description
 * @date:21/12/01 17:51
 */
public class LibCore {
    public final static String lib_path = "D:\\Processing.NDI.Lib.x64.dll";

    public static <T> T loadLibrary(Class<T> className){
        return (T) Native.loadLibrary(LibCore.lib_path, className);
    }
}
