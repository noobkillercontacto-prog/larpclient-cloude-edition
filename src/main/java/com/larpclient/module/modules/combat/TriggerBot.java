package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;

public class TriggerBot extends Module {
    private final SliderSetting delay = new SliderSetting("Delay ms", 50.0, 0.0, 300.0);
    private long lastHit = 0;

    public TriggerBot() {
        super("TriggerBot", "Attack the entity you aim at", Category.COMBAT);
        settings.add(delay);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (!(mc.targetedEntity instanceof LivingEntity target)) return;
        long now = System.currentTimeMillis();
        if (now - lastHit < delay.intValue()) return;
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        lastHit = now;
    }
}
