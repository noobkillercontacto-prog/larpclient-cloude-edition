package com.larpclient.module.setting;
public class BooleanSetting extends Setting<Boolean> {
    public BooleanSetting(String name, boolean defaultValue) { super(name, defaultValue); }
    public void toggle() { value = !value; }
    public boolean isEnabled() { return value; }
}
