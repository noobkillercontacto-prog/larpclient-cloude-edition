package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.ColorSetting;

public class BlockOverlay extends Module {
    private final ColorSetting color = new ColorSetting("Color", 0x550099FF);

    public BlockOverlay() {
        super("BlockOverlay", "Highlight the block you aim at", Category.VISUALS);
        settings.add(color);
    }
    // Applied in MixinWorldRenderer — overrides block outline color
    public int getColor() { return color.getValue(); }
}
