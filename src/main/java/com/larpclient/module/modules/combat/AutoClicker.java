package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import net.minecraft.util.Hand;

public class AutoClicker extends Module {
    private final SliderSetting  cps      = new SliderSetting("CPS", 12.0, 1.0, 20.0);
    private final BooleanSetting onlyHeld = new BooleanSetting("Only when held", true);
    private long lastClick = 0;

    public AutoClicker() {
        super("AutoClicker", "Customizable attack clicking", Category.COMBAT);
        settings.add(cps); settings.add(onlyHeld);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (onlyHeld.isEnabled() && !mc.options.attackKey.isPressed()) return;
        long now = System.currentTimeMillis();
        if (now - lastClick < (long)(1000.0 / cps.getValue())) return;
        if (mc.targetedEntity != null) {
            mc.interactionManager.attackEntity(mc.player, mc.targetedEntity);
            mc.player.swingHand(Hand.MAIN_HAND);
        }
        lastClick = now;
    }
}
