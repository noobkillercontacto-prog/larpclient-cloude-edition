package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

import java.util.Comparator;
import java.util.List;

public class AimAssist extends Module {
    private final SliderSetting  range  = new SliderSetting("Range", 5.0, 1.0, 8.0);
    private final SliderSetting  speed  = new SliderSetting("Speed", 3.0, 0.5, 10.0);
    private final BooleanSetting onlyHeld = new BooleanSetting("Only when aiming", true);

    public AimAssist() {
        super("AimAssist", "Smooth visible aim correction while you actively aim", Category.COMBAT);
        settings.add(range); settings.add(speed); settings.add(onlyHeld);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (onlyHeld.isEnabled() && !mc.options.attackKey.isPressed()) return;

        double r = range.getValue();
        Box box = mc.player.getBoundingBox().expand(r);
        List<Entity> candidates = mc.world.getOtherEntities(mc.player, box, e ->
            e instanceof PlayerEntity && mc.player.distanceTo(e) <= r);
        if (candidates.isEmpty()) return;

        Entity target = candidates.stream()
            .min(Comparator.comparingDouble(e -> mc.player.distanceTo(e))).orElse(null);
        if (target == null) return;

        double dx = target.getX() - mc.player.getX();
        double dy = (target.getY() + ((LivingEntity) target).getHeight() / 2.0)
                  - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double dz = target.getZ() - mc.player.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);

        float targetYaw   = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90f;
        float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, dist));

        float sp = speed.floatValue();
        float newYaw   = mc.player.getYaw()   + (targetYaw   - mc.player.getYaw())   / (20f / sp);
        float newPitch = mc.player.getPitch() + (targetPitch - mc.player.getPitch()) / (20f / sp);

        mc.player.setYaw(newYaw);
        mc.player.setPitch(newPitch);
    }
}
