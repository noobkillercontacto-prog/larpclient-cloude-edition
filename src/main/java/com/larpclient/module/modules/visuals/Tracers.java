package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import com.larpclient.event.events.EventRender2D;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class Tracers extends Module {
    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting mobs    = new BooleanSetting("Mobs", false);
    private final ColorSetting   color   = new ColorSetting("Color", 0xFF00FFAA);

    public Tracers() {
        super("Tracers", "Draw lines to entities", Category.VISUALS);
        settings.add(players); settings.add(mobs); settings.add(color);
    }

    @Override
    public void onRender2D(EventRender2D event) {
        if (!nullCheck()) return;
        DrawContext ctx = event.getContext();
        int sw = mc.getWindow().getScaledWidth();
        int sh = mc.getWindow().getScaledHeight();
        int cx = sw / 2, cy = sh / 2;
        int col = color.getValue();

        for (Entity e : mc.world.getEntities()) {
            if (e == mc.player) continue;
            if (e instanceof PlayerEntity && !players.isEnabled()) continue;
            if (!(e instanceof PlayerEntity) && !mobs.isEnabled()) continue;
            if (!(e instanceof LivingEntity le) || le.isDead()) continue;

            int[] screen = worldToScreen(e.getPos().add(0, e.getHeight() / 2.0, 0), sw, sh, event.getTickDelta());
            if (screen == null) continue;

            ctx.drawHorizontalLine(cx, screen[0], cy, col);
            ctx.drawVerticalLine(screen[0], cy, screen[1], col);
        }
    }

    private int[] worldToScreen(Vec3d world, int sw, int sh, float td) {
        // Uses MC's camera projection — simplified; full impl via MixinGameRenderer matrix capture
        // Returns null when behind camera
        return null; // FILL: inject actual projection matrix via mixin
    }
}
