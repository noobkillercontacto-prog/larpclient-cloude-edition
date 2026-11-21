package com.larpclient.module.setting;
public class SliderSetting extends Setting<Double> {
    private final double min, max;
    public SliderSetting(String name, double def, double min, double max) {
        super(name, def); this.min = min; this.max = max;
    }
    @Override
    public void setValue(Double value) { this.value = Math.max(min, Math.min(max, value)); }
    public double getMin() { return min; }
    public double getMax() { return max; }
    public float floatValue() { return value.floatValue(); }
    public int intValue() { return value.intValue(); }
}
