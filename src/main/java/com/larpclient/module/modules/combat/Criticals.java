package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.ModeSetting;

public class Criticals extends Module {
    private final ModeSetting mode = new ModeSetting("Mode", "Jump", "Jump", "Packet");

    public Criticals() {
        super("Criticals", "Help time jumps for critical hits", Category.COMBAT);
        settings.add(mode);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (!mc.player.isOnGround() || mc.player.isInFluid()) return;
        if (mc.options.attackKey.isPressed() && mode.is("Jump")) mc.player.jump();
    }
}
