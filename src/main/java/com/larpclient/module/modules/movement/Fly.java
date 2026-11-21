package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import org.lwjgl.glfw.GLFW;

public class Fly extends Module {
    private final ModeSetting    mode   = new ModeSetting("Mode", "Vanilla", "Vanilla", "Creative");
    private final SliderSetting  speed  = new SliderSetting("Speed", 1.0, 0.1, 5.0);

    public Fly() {
        super("Fly", "Fly or glide slowly", Category.MOVEMENT, GLFW.GLFW_KEY_F);
        settings.add(mode); settings.add(speed);
    }

    @Override
    public void onEnable() {
        if (!nullCheck()) return;
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().allowFlying = true;
        mc.player.sendAbilitiesUpdate();
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) return;
        if (!mc.player.isCreative() && !mc.player.isSpectator()) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().allowFlying = false;
            mc.player.sendAbilitiesUpdate();
        }
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        mc.player.getAbilities().flySpeed = speed.floatValue() * 0.05f;
    }
}
