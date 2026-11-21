package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;

public class ElytraFly extends Module {
    private final ModeSetting   mode  = new ModeSetting("Mode", "Boost", "Boost", "Glide");
    private final SliderSetting speed = new SliderSetting("Speed", 1.8, 0.5, 5.0);

    public ElytraFly() {
        super("ElytraFly", "Control elytra speed and flight", Category.MOVEMENT);
        settings.add(mode); settings.add(speed);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (!mc.player.isFallFlying()) return;

        if (mode.is("Boost")) {
            double yaw = Math.toRadians(mc.player.getYaw());
            double pitch = Math.toRadians(mc.player.getPitch());
            double sp = speed.getValue();
            mc.player.setVelocity(
                -Math.sin(yaw) * Math.cos(pitch) * sp,
                -Math.sin(pitch) * sp,
                 Math.cos(yaw) * Math.cos(pitch) * sp
            );
        }
    }
}
