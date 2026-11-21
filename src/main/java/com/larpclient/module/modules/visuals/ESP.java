package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import com.larpclient.event.events.EventRender3D;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class ESP extends Module {
    private final ModeSetting    mode    = new ModeSetting("Mode", "Box", "Box", "Corner");
    private final BooleanSetting players = new BooleanSetting("Players", true);
    private final BooleanSetting mobs    = new BooleanSetting("Mobs", false);
    private final ColorSetting   color   = new ColorSetting("Color", 0xFF00AAFF);
    private final SliderSetting  lineW   = new SliderSetting("Line Width", 1.5, 0.5, 3.0);

    public ESP() {
        super("ESP", "See entities through walls", Category.VISUALS);
        settings.add(mode); settings.add(players); settings.add(mobs);
        settings.add(color); settings.add(lineW);
    }

    @Override
    public void onRender3D(EventRender3D event) {
        if (!nullCheck()) return;
        Vec3d cam = event.getCamera().getPos();

        for (Entity e : mc.world.getEntities()) {
            if (e == mc.player) continue;
            if (e instanceof PlayerEntity && !players.isEnabled()) continue;
            if (!(e instanceof PlayerEntity) && !mobs.isEnabled()) continue;
            if (!(e instanceof LivingEntity le)) continue;
            if (le.isDead()) continue;

            renderBox(e, cam, event);
        }
    }

    private void renderBox(Entity e, Vec3d cam, EventRender3D event) {
        float tickDelta = event.getTickDelta();
        double x = e.prevX + (e.getX() - e.prevX) * tickDelta - cam.x;
        double y = e.prevY + (e.getY() - e.prevY) * tickDelta - cam.y;
        double z = e.prevZ + (e.getZ() - e.prevZ) * tickDelta - cam.z;

        Box bb = e.getBoundingBox();
        double w = (bb.maxX - bb.minX) / 2.0;
        double h = bb.maxY - bb.minY;

        // WorldRenderer.drawBox draws an outlined box — alias for reference
        // Actual GL rendering requires matrix injection from the mixin
        // Store for overlay render pass in MixinWorldRenderer
    }
}
