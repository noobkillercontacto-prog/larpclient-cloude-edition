package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;

public class NoJumpDelay extends Module {
    public NoJumpDelay() {
        super("NoJumpDelay", "Remove the delay between grounded jumps", Category.MOVEMENT);
    }
    // Applied in MixinClientPlayerEntity — clears jumpingCooldown when module enabled
}
