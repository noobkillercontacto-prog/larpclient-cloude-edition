package com.larpclient.module.modules.visuals;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import com.larpclient.event.events.EventRender3D;
import net.minecraft.entity.player.PlayerEntity;

public class Nametags extends Module {
    private final BooleanSetting health = new BooleanSetting("Health", true);
    private final BooleanSetting gear   = new BooleanSetting("Gear",   true);

    public Nametags() {
        super("Nametags", "Show names, health and gear above entities", Category.VISUALS);
        settings.add(health); settings.add(gear);
    }

    public boolean showHealth() { return health.isEnabled(); }
    public boolean showGear()   { return gear.isEnabled(); }
    // Rendering applied in MixinWorldRenderer entity label hook
}
