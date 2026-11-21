package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;

public class Fullbright extends Module {
    private float prevGamma = 0;

    public Fullbright() {
        super("Fullbright", "Brighten dark areas", Category.VISUALS);
    }

    @Override
    public void onEnable() {
        if (!nullCheck()) return;
        prevGamma = mc.options.getGamma().getValue().floatValue();
        mc.options.getGamma().setValue(100.0);
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) return;
        mc.options.getGamma().setValue((double) prevGamma);
    }
}
