package com.larpclient.module.modules.misc;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;

public class FakeFPS extends Module {
    private final SliderSetting fps = new SliderSetting("FPS", 240.0, 1.0, 1000.0);

    public FakeFPS() {
        super("FakeFPS", "Shows a different FPS number on the HUD", Category.MISC);
        settings.add(fps);
    }

    public int getFakeFPS() { return fps.intValue(); }
    // Applied in MixinMinecraftClient — returns fake value from getCurrentFps()
}
