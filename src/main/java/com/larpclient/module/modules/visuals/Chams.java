package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;

public class Chams extends Module {
    private final ModeSetting    mode      = new ModeSetting("Mode", "Outline", "Outline", "Fill", "Glow");
    private final BooleanSetting visible   = new BooleanSetting("Through walls", true);
    private final ColorSetting   wallColor = new ColorSetting("Wall Color", 0xFF0040FF);
    private final ColorSetting   normColor = new ColorSetting("Normal Color", 0xFF00AAFF);

    public Chams() {
        super("Chams", "Animated gradient outlines, fill and glow on entities", Category.VISUALS);
        settings.add(mode); settings.add(visible); settings.add(wallColor); settings.add(normColor);
    }
    // Applied in MixinWorldRenderer — renders entity with custom shader/color when visible=true
    public boolean throughWalls() { return visible.isEnabled(); }
    public int getWallColor()     { return wallColor.getValue(); }
    public int getNormColor()     { return normColor.getValue(); }
    public String getMode()       { return mode.getValue(); }
}
