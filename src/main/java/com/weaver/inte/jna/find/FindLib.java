package com.weaver.inte.jna.find;

import com.sun.jna.Library;
import com.sun.jna.ptr.IntByReference;
import com.weaver.inte.jna.common.data.LibCore;
import com.weaver.inte.jna.common.data.NdiSourceData;
import com.weaver.inte.jna.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FindLib implements AutoCloseable {
    final long insPoint;
    static FindInterface instance;

    static {
        instance =  LibCore.loadLibrary(FindInterface.class);
    }

    public FindLib(FindData ins) {
        System.out.println("init find");
        this.insPoint = instance.NDIlib_find_create_v2(ins);
    }

    public List<NdiSourceData> findCurrentSources(int waitTime) {
        Set<NdiSourceData> sourceDataList = new HashSet<>();
        long startTills = System.currentTimeMillis();
        System.out.println("find...");
        for (; System.currentTimeMillis() - startTills < 2000; ) {
            if (!instance.NDIlib_find_wait_for_sources(this.insPoint, waitTime)) {
                continue;
            }
            IntByReference numSources = new IntByReference();
            //搜索NDI源
            NdiSourceData sr = instance.NDIlib_find_get_current_sources(this.insPoint, numSources);
            if (StringUtils.isNotNull(sr.p_ndi_name) && !sourceDataList.contains(sr)) {
                System.out.println(sr);
                sourceDataList.add(sr);
            }
        }
        System.out.println("find end");
        return new ArrayList<>(sourceDataList);
    }

    @Override
    public void close() throws IOException {
        instance.NDIlib_find_destroy(this.insPoint);
        System.out.println("destroy find");
    }

    interface FindInterface extends Library {

        int NDIlib_find_create_v2(FindData ins);

        void NDIlib_find_destroy(long insPoint);

        boolean NDIlib_find_wait_for_sources(long insPoint, int timeout);

        NdiSourceData NDIlib_find_get_current_sources(long insPoint, IntByReference numSources);

    }
}
