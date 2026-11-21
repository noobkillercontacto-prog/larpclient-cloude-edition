package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import org.lwjgl.glfw.GLFW;

import java.util.Comparator;
import java.util.List;

public class Aura extends Module {

    private final SliderSetting  range      = new SliderSetting("Range", 3.5, 1.0, 6.0);
    private final SliderSetting  cps        = new SliderSetting("CPS", 12.0, 1.0, 20.0);
    private final ModeSetting    targetMode = new ModeSetting("Target", "Closest", "Closest", "Health", "Angle");
    private final BooleanSetting players    = new BooleanSetting("Players", true);
    private final BooleanSetting mobs       = new BooleanSetting("Mobs", false);
    private final BooleanSetting rotate     = new BooleanSetting("Rotate", true);

    private long lastAttack = 0;

    public Aura() {
        super("Aura", "Automatically attacks nearby entities", Category.COMBAT, GLFW.GLFW_KEY_R);
        settings.add(range); settings.add(cps); settings.add(targetMode);
        settings.add(players); settings.add(mobs); settings.add(rotate);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        long now = System.currentTimeMillis();
        long delay = (long) (1000.0 / cps.getValue());
        if (now - lastAttack < delay) return;

        Entity target = findTarget();
        if (target == null) return;

        if (rotate.isEnabled()) rotateToEntity(target);
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastAttack = now;
    }

    private Entity findTarget() {
        double r = range.getValue();
        Box box = mc.player.getBoundingBox().expand(r);
        List<Entity> candidates = mc.world.getOtherEntities(mc.player, box, e -> {
            if (!(e instanceof LivingEntity le)) return false;
            if (le.isDead() || le.getHealth() <= 0) return false;
            if (e instanceof PlayerEntity && !players.isEnabled()) return false;
            if (!(e instanceof PlayerEntity) && !mobs.isEnabled()) return false;
            return mc.player.distanceTo(e) <= r;
        });
        if (candidates.isEmpty()) return null;
        return switch (targetMode.getValue()) {
            case "Health" -> candidates.stream()
                .min(Comparator.comparingDouble(e -> ((LivingEntity) e).getHealth())).orElse(null);
            case "Angle"  -> candidates.stream()
                .min(Comparator.comparingDouble(this::getAngleTo)).orElse(null);
            default       -> candidates.stream()
                .min(Comparator.comparingDouble(e -> mc.player.distanceTo(e))).orElse(null);
        };
    }

    private void rotateToEntity(Entity target) {
        double dx = target.getX() - mc.player.getX();
        double dy = (target.getY() + target.getHeight() / 2.0)
                  - (mc.player.getY() + mc.player.getEyeHeight(mc.player.getPose()));
        double dz = target.getZ() - mc.player.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);
        mc.player.setYaw((float) Math.toDegrees(Math.atan2(dz, dx)) - 90f);
        mc.player.setPitch((float) -Math.toDegrees(Math.atan2(dy, dist)));
    }

    private double getAngleTo(Entity e) {
        double dx = e.getX() - mc.player.getX();
        double dz = e.getZ() - mc.player.getZ();
        double targetYaw = Math.toDegrees(Math.atan2(dz, dx)) - 90;
        double diff = Math.abs(targetYaw - mc.player.getYaw()) % 360;
        return diff > 180 ? 360 - diff : diff;
    }
}
