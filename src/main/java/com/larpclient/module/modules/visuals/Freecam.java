package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;
import net.minecraft.util.math.Vec3d;

public class Freecam extends Module {
    private final SliderSetting speed = new SliderSetting("Speed", 1.0, 0.1, 5.0);
    private Vec3d savedPos;
    private float savedYaw, savedPitch;

    public Freecam() {
        super("Freecam", "Explore with a detached camera", Category.VISUALS);
        settings.add(speed);
    }

    @Override
    public void onEnable() {
        if (!nullCheck()) return;
        savedPos   = mc.player.getPos();
        savedYaw   = mc.player.getYaw();
        savedPitch = mc.player.getPitch();
        mc.player.getAbilities().flying = true;
        mc.player.getAbilities().allowFlying = true;
        mc.player.noClip = true;
    }

    @Override
    public void onDisable() {
        if (!nullCheck()) return;
        mc.player.noClip = false;
        if (!mc.player.isCreative() && !mc.player.isSpectator()) {
            mc.player.getAbilities().flying = false;
            mc.player.getAbilities().allowFlying = false;
        }
        if (savedPos != null) {
            mc.player.setPosition(savedPos.x, savedPos.y, savedPos.z);
            mc.player.setYaw(savedYaw);
            mc.player.setPitch(savedPitch);
        }
    }
}
