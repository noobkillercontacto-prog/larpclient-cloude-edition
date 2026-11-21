package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;

public class AntiKnockback extends Module {
    private final SliderSetting horizontal = new SliderSetting("Horizontal %", 0.0, 0.0, 100.0);
    private final SliderSetting vertical   = new SliderSetting("Vertical %",   0.0, 0.0, 100.0);

    public AntiKnockback() {
        super("AntiKnockback", "Reduce knockback from hits and explosions", Category.COMBAT);
        settings.add(horizontal); settings.add(vertical);
    }

    // Called by MixinClientPlayerEntity on velocity update
    public double modifyH(double vel) { return vel * (horizontal.getValue() / 100.0); }
    public double modifyV(double vel) { return vel * (vertical.getValue()   / 100.0); }
}
