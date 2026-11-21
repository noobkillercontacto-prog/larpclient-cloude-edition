package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;

public class Zoom extends Module {
    private final SliderSetting zoomFov   = new SliderSetting("Zoom FOV", 10.0, 1.0, 50.0);
    private final SliderSetting smoothing = new SliderSetting("Smoothing", 5.0, 1.0, 10.0);
    private float currentFov  = -1;
    private float defaultFov  = 70;

    public Zoom() {
        super("Zoom", "Hold a key to zoom smoothly, with wheel adjustment", Category.VISUALS);
        settings.add(zoomFov); settings.add(smoothing);
    }

    @Override
    public void onEnable() {
        if (!nullCheck()) return;
        defaultFov  = mc.options.getFov().getValue();
        currentFov  = defaultFov;
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) return;
        mc.options.getFov().setValue((int) defaultFov);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        float target = zoomFov.floatValue();
        currentFov += (target - currentFov) / smoothing.floatValue();
        mc.options.getFov().setValue((int) currentFov);
    }
}
