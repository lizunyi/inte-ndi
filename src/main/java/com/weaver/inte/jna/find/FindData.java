package com.weaver.inte.jna.find;

import com.sun.jna.Structure;

public class FindData extends Structure {

    public static class ByReference extends FindData implements Structure.ByReference {
    }

    public static class ByValue extends FindData implements Structure.ByValue {
    }

    public boolean show_local_sources;
    public String p_groups;
    public String p_extra_ips;

    public FindData() {
    }

    public FindData(boolean show_local_sources, String p_groups, String p_extra_ips) {
        this.show_local_sources = show_local_sources;
        this.p_groups = p_groups;
        this.p_extra_ips = p_extra_ips;
    }
}
