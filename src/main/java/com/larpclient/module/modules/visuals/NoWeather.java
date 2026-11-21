package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;

public class NoWeather extends Module {
    public NoWeather() {
        super("NoWeather", "Hide rain and snow", Category.VISUALS);
    }
    // Applied in MixinBackgroundRenderer — skips precipitation render when enabled
}
