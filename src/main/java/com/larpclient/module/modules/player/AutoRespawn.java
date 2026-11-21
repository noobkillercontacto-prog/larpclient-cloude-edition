package com.larpclient.module.modules.player;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import net.minecraft.client.gui.screen.DeathScreen;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", "Respawn automatically", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (mc.currentScreen instanceof DeathScreen) {
            mc.player.requestRespawn();
            mc.setScreen(null);
        }
    }
}
