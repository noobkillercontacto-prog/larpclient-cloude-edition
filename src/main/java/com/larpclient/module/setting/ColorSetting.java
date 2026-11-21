package com.larpclient.module.setting;
public class ColorSetting extends Setting<Integer> {
    public ColorSetting(String name, int defaultARGB) { super(name, defaultARGB); }
    public int getR() { return (value >> 16) & 0xFF; }
    public int getG() { return (value >> 8)  & 0xFF; }
    public int getB() { return value          & 0xFF; }
    public int getA() { return (value >> 24) & 0xFF; }
}
