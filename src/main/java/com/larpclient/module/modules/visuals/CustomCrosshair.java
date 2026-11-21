package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import com.larpclient.event.events.EventRender2D;
import net.minecraft.client.gui.DrawContext;

public class CustomCrosshair extends Module {
    private final ModeSetting    style = new ModeSetting("Style", "Cross", "Cross", "Circle", "Dot");
    private final SliderSetting  size  = new SliderSetting("Size", 5.0, 1.0, 20.0);
    private final SliderSetting  gap   = new SliderSetting("Gap", 2.0, 0.0, 10.0);
    private final ColorSetting   color = new ColorSetting("Color", 0xFFFFFFFF);

    public CustomCrosshair() {
        super("CustomCrosshair", "Customize your crosshair", Category.VISUALS);
        settings.add(style); settings.add(size); settings.add(gap); settings.add(color);
    }

    @Override
    public void onRender2D(EventRender2D event) {
        if (!nullCheck()) return;
        DrawContext ctx = event.getContext();
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        int cx = sw / 2, cy = sh / 2;
        int col = color.getValue();
        int s = size.intValue();
        int g = gap.intValue();

        // Hide vanilla crosshair via mixin
        if (style.is("Cross")) {
            ctx.fill(cx - s - g, cy - 1, cx - g, cy + 1, col);  // left
            ctx.fill(cx + g,     cy - 1, cx + s + g, cy + 1, col); // right
            ctx.fill(cx - 1, cy - s - g, cx + 1, cy - g, col);  // top
            ctx.fill(cx - 1, cy + g,     cx + 1, cy + s + g, col); // bottom
        } else if (style.is("Dot")) {
            ctx.fill(cx - 1, cy - 1, cx + 1, cy + 1, col);
        }
        // Circle: requires tessellator arc — FILL via mixin helper
    }
}
