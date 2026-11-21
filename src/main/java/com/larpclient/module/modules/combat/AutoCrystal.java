package com.larpclient.module.modules.combat;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.*;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Comparator;
import java.util.List;

public class AutoCrystal extends Module {
    private final SliderSetting  placeRange  = new SliderSetting("Place Range", 4.0, 2.0, 6.0);
    private final SliderSetting  breakRange  = new SliderSetting("Break Range", 4.0, 2.0, 6.0);
    private final SliderSetting  delay       = new SliderSetting("Delay ms",    50.0, 0.0, 500.0);
    private final BooleanSetting antiSelf    = new BooleanSetting("Anti Self Damage", true);

    private long lastAction = 0;

    public AutoCrystal() {
        super("AutoCrystal", "Automatically place and break end crystals", Category.COMBAT);
        settings.add(placeRange); settings.add(breakRange);
        settings.add(delay); settings.add(antiSelf);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        long now = System.currentTimeMillis();
        if (now - lastAction < delay.intValue()) return;

        // Break nearby crystals first
        Box breakBox = mc.player.getBoundingBox().expand(breakRange.getValue());
        List<EndCrystalEntity> crystals = mc.world.getEntitiesByClass(
            EndCrystalEntity.class, breakBox,
            c -> mc.player.distanceTo(c) <= breakRange.getValue());

        if (!crystals.isEmpty()) {
            EndCrystalEntity nearest = crystals.stream()
                .min(Comparator.comparingDouble(c -> mc.player.distanceTo(c))).orElse(null);
            if (nearest != null) {
                mc.interactionManager.attackEntity(mc.player, nearest);
                mc.player.swingHand(Hand.MAIN_HAND);
                lastAction = now;
                return;
            }
        }

        // Find target player
        Box targetBox = mc.player.getBoundingBox().expand(placeRange.getValue());
        List<PlayerEntity> targets = mc.world.getEntitiesByClass(
            PlayerEntity.class, targetBox,
            p -> p != mc.player && mc.player.distanceTo(p) <= placeRange.getValue());
        if (targets.isEmpty()) return;

        PlayerEntity target = targets.stream()
            .min(Comparator.comparingDouble(p -> mc.player.distanceTo(p))).orElse(null);
        if (target == null) return;

        // Find best obsidian/bedrock face near target to place crystal
        BlockPos targetPos = target.getBlockPos().down();
        for (Direction dir : Direction.values()) {
            BlockPos placePos = targetPos.offset(dir);
            if (!mc.world.getBlockState(placePos).isAir()) continue;
            BlockPos below = placePos.down();
            if (mc.world.getBlockState(below).isAir()) continue;

            // Check crystal doesn't hurt us too much
            if (antiSelf.isEnabled()) {
                double selfDist = mc.player.distanceTo(Vec3d.ofCenter(placePos));
                if (selfDist < 2.5) continue;
            }

            // Switch to crystal in hotbar
            int crystalSlot = -1;
            for (int i = 0; i < 9; i++) {
                if (mc.player.getInventory().getStack(i).getItem() == Items.END_CRYSTAL) {
                    crystalSlot = i; break;
                }
            }
            if (crystalSlot == -1) return;
            mc.player.getInventory().selectedSlot = crystalSlot;

            // Place
            mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND,
                new BlockHitResult(Vec3d.ofCenter(below), Direction.UP, below, false));
            lastAction = now;
            return;
        }
    }
}
