package com.larpclient.module.modules.player;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.setting.SliderSetting;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class AutoEat extends Module {
    private final SliderSetting threshold = new SliderSetting("Hunger threshold", 16.0, 1.0, 20.0);

    public AutoEat() {
        super("AutoEat", "Automatically eat food", Category.PLAYER);
        settings.add(threshold);
    }

    @Override
    public void onTick() {
        if (!nullCheck()) return;
        if (mc.player.getHungerManager().getFoodLevel() > threshold.intValue()) return;

        // Find best food in hotbar
        int bestSlot = -1;
        int bestFood = -1;
        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.player.getInventory().getStack(i);
            if (!stack.isFood()) continue;
            FoodComponent food = stack.getItem().getFoodComponent();
            if (food == null) continue;
            if (food.getHunger() > bestFood) {
                bestFood = food.getHunger();
                bestSlot = i;
            }
        }

        if (bestSlot == -1) return;
        mc.player.getInventory().selectedSlot = bestSlot;
        mc.interactionManager.interactItem(mc.player, Hand.MAIN_HAND);
    }
}
