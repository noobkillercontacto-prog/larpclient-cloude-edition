package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.BooleanSetting;

public class NoSlowdown extends Module {
    private final BooleanSetting items  = new BooleanSetting("Item use slowdown", true);
    private final BooleanSetting blocks = new BooleanSetting("Block slowdown", true);

    public NoSlowdown() {
        super("NoSlowdown", "Reduce item and block slowdown", Category.MOVEMENT);
        settings.add(items); settings.add(blocks);
    }

    // Applied in MixinClientPlayerEntity — module checked there
    public boolean cancelItemSlow()  { return isEnabled() && items.isEnabled(); }
    public boolean cancelBlockSlow() { return isEnabled() && blocks.isEnabled(); }
}
