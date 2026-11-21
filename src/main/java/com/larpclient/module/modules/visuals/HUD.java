package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import com.larpclient.event.events.EventRender2D;
import com.larpclient.LarpClient;
import net.minecraft.client.gui.DrawContext;

import java.util.List;
import java.util.stream.Collectors;

public class HUD extends Module {
    private final BooleanSetting showModules = new BooleanSetting("Module List", true);
    private final BooleanSetting showCoords  = new BooleanSetting("Coordinates", true);
    private final BooleanSetting showFPS     = new BooleanSetting("FPS",         true);
    private final BooleanSetting showSpeed   = new BooleanSetting("Speed",       false);
    private final ColorSetting   accentColor = new ColorSetting("Accent", 0xFF00AAFF);

    public HUD() {
        super("HUD", "Choose on-screen information", Category.VISUALS);
        settings.add(showModules); settings.add(showCoords);
        settings.add(showFPS); settings.add(showSpeed); settings.add(accentColor);
    }

    @Override
    public void onRender2D(EventRender2D event) {
        if (!nullCheck()) return;
        DrawContext ctx = event.getContext();
        int col = accentColor.getValue();
        int white = 0xFFFFFFFF;
        int y = 2;

        // Watermark
        ctx.drawTextWithShadow(mc.textRenderer, "LarpClient Free", 2, y, col);
        y += 10;

        // Module list
        if (showModules.isEnabled()) {
            List<com.larpclient.module.Module> enabled = LarpClient.getInstance()
                .getModuleManager().getModules().stream()
                .filter(com.larpclient.module.Module::isEnabled)
                .filter(m -> !(m instanceof HUD))
                .collect(Collectors.toList());
            for (com.larpclient.module.Module m : enabled) {
                ctx.drawTextWithShadow(mc.textRenderer, m.getName(), 2, y, white);
                y += 10;
            }
        }

        // Coords
        if (showCoords.isEnabled() && mc.player != null) {
            String coords = String.format("XYZ: %.1f / %.1f / %.1f",
                mc.player.getX(), mc.player.getY(), mc.player.getZ());
            ctx.drawTextWithShadow(mc.textRenderer, coords, 2, y, white);
            y += 10;
        }

        // FPS
        if (showFPS.isEnabled()) {
            ctx.drawTextWithShadow(mc.textRenderer, "FPS: " + mc.getCurrentFps(), 2, y, white);
            y += 10;
        }

        // Speed
        if (showSpeed.isEnabled() && mc.player != null) {
            double spd = Math.sqrt(
                mc.player.getVelocity().x * mc.player.getVelocity().x +
                mc.player.getVelocity().z * mc.player.getVelocity().z);
            ctx.drawTextWithShadow(mc.textRenderer, String.format("Speed: %.2f", spd), 2, y, white);
        }
    }
}
