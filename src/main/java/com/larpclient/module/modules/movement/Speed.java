package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;

public class Speed extends Module {
    private final ModeSetting   mode  = new ModeSetting("Mode", "Strafe", "Strafe", "Boost");
    private final SliderSetting value = new SliderSetting("Speed", 0.4, 0.1, 2.0);

    public Speed() {
        super("Speed", "Move faster", Category.MOVEMENT);
        settings.add(mode); settings.add(value);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (!mc.player.isOnGround()) return;
        if (mode.is("Strafe")) {
            mc.player.getAbilities().setWalkSpeed(value.floatValue());
        } else {
            if (mc.player.isSprinting()) {
                mc.player.addVelocity(
                    -Math.sin(Math.toRadians(mc.player.getYaw())) * value.getValue() * 0.1,
                    0,
                    Math.cos(Math.toRadians(mc.player.getYaw())) * value.getValue() * 0.1
                );
            }
        }
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) return;
        mc.player.getAbilities().setWalkSpeed(0.1f);
    }
}
