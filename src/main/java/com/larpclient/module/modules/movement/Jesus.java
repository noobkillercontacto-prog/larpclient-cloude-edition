package com.larpclient.module.modules.movement;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;

public class Jesus extends Module {
    public Jesus() {
        super("Jesus", "Walk on water surfaces; hold sneak to swim normally", Category.MOVEMENT);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (mc.player.isSneaking()) return;
        if (!mc.player.isTouchingWater()) return;

        BlockPos pos = mc.player.getBlockPos();
        boolean waterBelow = mc.world.getBlockState(pos.down()).isOf(Blocks.WATER);
        if (waterBelow && mc.player.getVelocity().y < 0) {
            mc.player.setVelocity(mc.player.getVelocity().x, 0.1, mc.player.getVelocity().z);
        }
    }
}
