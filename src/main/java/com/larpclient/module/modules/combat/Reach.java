package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;

public class Reach extends Module {
    private final SliderSetting attackReach = new SliderSetting("Attack Reach", 3.5, 3.0, 6.0);
    private final SliderSetting blockReach  = new SliderSetting("Block Reach",  4.5, 4.0, 6.0);

    public Reach() {
        super("Reach", "Adjust attack and block reach", Category.COMBAT);
        settings.add(attackReach); settings.add(blockReach);
    }

    public float getAttackReach() { return attackReach.floatValue(); }
    public float getBlockReach()  { return blockReach.floatValue(); }
}
