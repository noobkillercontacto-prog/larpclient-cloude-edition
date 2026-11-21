package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.event.events.EventMotion;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", "Try to reduce fall damage", Category.MOVEMENT);
    }

    @Override
    public void onMotion(EventMotion event) {
        if (!nullCheck()) return;
        if (mc.player.fallDistance > 2.5f) {
            event.setOnGround(true);
        }
    }
}
