package com.larpclient.module.modules.player;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;
import net.minecraft.item.*;
import net.minecraft.screen.slot.SlotActionType;

import java.util.Set;

public class InvCleaner extends Module {
    private final SliderSetting delay = new SliderSetting("Delay ms", 100.0, 50.0, 1000.0);
    private long last = 0;

    // Items considered junk — extend as needed
    private static final Set<Item> JUNK = Set.of(
        Items.GRAVEL, Items.DIRT, Items.SAND, Items.ROTTEN_FLESH,
        Items.POISONOUS_POTATO, Items.BONE, Items.STRING
    );

    public InvCleaner() {
        super("InvCleaner", "Automatically drop junk items", Category.PLAYER);
        settings.add(delay);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (mc.player.currentScreenHandler == null) return;
        long now = System.currentTimeMillis();
        if (now - last < delay.intValue()) return;

        for (int i = 9; i < 45; i++) {
            ItemStack stack = mc.player.currentScreenHandler.slots.get(i).getStack();
            if (stack.isEmpty()) continue;
            if (JUNK.contains(stack.getItem())) {
                mc.interactionManager.clickSlot(
                    mc.player.currentScreenHandler.syncId, i, 1, SlotActionType.THROW, mc.player);
                last = now;
                return;
            }
        }
    }
}
