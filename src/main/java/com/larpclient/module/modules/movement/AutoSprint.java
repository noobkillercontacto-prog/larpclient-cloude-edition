package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;

public class AutoSprint extends Module {
    public AutoSprint() {
        super("AutoSprint", "Sprint automatically without holding the sprint key", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (mc.player.isMoving() && !mc.player.isInFluid() && !mc.player.isSneaking()) {
            mc.player.setSprinting(true);
        }
    }
}
