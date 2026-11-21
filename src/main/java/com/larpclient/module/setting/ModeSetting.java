package com.larpclient.module.setting;
import java.util.*;
public class ModeSetting extends Setting<String> {
    private final List<String> modes;
    public ModeSetting(String name, String def, String... modes) {
        super(name, def); this.modes = Arrays.asList(modes);
    }
    public List<String> getModes() { return modes; }
    public boolean is(String mode) { return value.equalsIgnoreCase(mode); }
    public void cycle() {
        int idx = modes.indexOf(value);
        value = modes.get((idx + 1) % modes.size());
    }
}
