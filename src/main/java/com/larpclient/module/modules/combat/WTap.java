package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import net.minecraft.entity.LivingEntity;

public class WTap extends Module {
    private boolean released = false;

    public WTap() {
        super("WTap", "Briefly release forward after a charged sprint attack", Category.COMBAT);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        // When player attacks and is sprinting, briefly cancel forward movement next tick
        // to reset sprint attack charge, then resume
        if (mc.options.attackKey.isPressed() && mc.player.isSprinting()) {
            if (!released) {
                // Inject forward=false for one tick via motion manipulation
                mc.player.forwardSpeed = 0;
                released = true;
            } else {
                released = false;
            }
        }
    }
}
