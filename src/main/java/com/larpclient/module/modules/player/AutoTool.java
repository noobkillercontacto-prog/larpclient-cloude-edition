package com.larpclient.module.modules.player;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class AutoTool extends Module {
    public AutoTool() {
        super("AutoTool", "Select the best hotbar tool while mining", Category.PLAYER);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (!mc.options.attackKey.isPressed()) return;
        if (!(mc.crosshairTarget instanceof BlockHitResult bhr)) return;

        BlockState state = mc.world.getBlockState(bhr.getBlockPos());
        int best = mc.player.getInventory().selectedSlot;
        float bestSpeed = -1;

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            float speed = stack.getMiningSpeedMultiplier(state);
            if (speed > bestSpeed) { bestSpeed = speed; best = i; }
        }

        mc.player.getInventory().selectedSlot = best;
    }
}
