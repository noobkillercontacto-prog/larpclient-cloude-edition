package com.larpclient.gui;

import com.larpclient.module.Category;
import com.larpclient.module.Module;
import com.larpclient.module.ModuleManager;
import com.larpclient.module.setting.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.List;

public class ClickGUI extends Screen {

    private static final int PANEL_W   = 100;
    private static final int PANEL_H   = 14;
    private static final int MODULE_H  = 12;
    private static final int PADDING   = 4;
    private static final int BG_COLOR  = 0xCC1A1A2E;
    private static final int HEADER_ON = 0xFF0066CC;
    private static final int HEADER_OFF= 0xFF2D2D44;
    private static final int TEXT_COLOR = 0xFFFFFFFF;
    private static final int ACCENT    = 0xFF00AAFF;

    private final ModuleManager moduleManager;
    private boolean open = false;

    // Dragging state
    private int dragCatIdx = -1;
    private int dragOffX, dragOffY;
    private int[] catX, catY;

    public ClickGUI(ModuleManager moduleManager) {
        super(Text.literal("LarpClient"));
        this.moduleManager = moduleManager;
        Category[] cats = Category.values();
        catX = new int[cats.length];
        catY = new int[cats.length];
        for (int i = 0; i < cats.length; i++) {
            catX[i] = 5 + i * (PANEL_W + 5);
            catY[i] = 5;
        }
    }

    public void toggle() {
        open = !open;
        if (open) MinecraftClient.getInstance().setScreen(this);
        else      MinecraftClient.getInstance().setScreen(null);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // Semi-transparent overlay
        ctx.fill(0, 0, width, height, 0x88000000);

        Category[] cats = Category.values();
        for (int ci = 0; ci < cats.length; ci++) {
            Category cat = cats[ci];
            List<Module> modules = moduleManager.getByCategory(cat);
            int x = catX[ci], y = catY[ci];
            int panelHeight = PANEL_H + modules.size() * MODULE_H + 2;

            // Panel background
            ctx.fill(x, y, x + PANEL_W, y + panelHeight, BG_COLOR);
            // Category header
            ctx.fill(x, y, x + PANEL_W, y + PANEL_H, HEADER_OFF);
            ctx.drawTextWithShadow(this.textRenderer, cat.getDisplayName(),
                x + PADDING, y + 3, TEXT_COLOR);

            // Modules
            for (int mi = 0; mi < modules.size(); mi++) {
                Module m = modules.get(mi);
                int my = y + PANEL_H + mi * MODULE_H;
                int bg = m.isEnabled() ? 0xFF16213E : 0xFF0D0D1A;
                ctx.fill(x, my, x + PANEL_W, my + MODULE_H, bg);
                // Accent bar on enabled
                if (m.isEnabled()) ctx.fill(x, my, x + 2, my + MODULE_H, ACCENT);
                ctx.drawTextWithShadow(this.textRenderer, m.getName(),
                    x + PADDING + 2, my + 2, m.isEnabled() ? TEXT_COLOR : 0xFFAAAAAA);
            }
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Category[] cats = Category.values();
        for (int ci = 0; ci < cats.length; ci++) {
            Category cat = cats[ci];
            List<Module> modules = moduleManager.getByCategory(cat);
            int x = catX[ci], y = catY[ci];

            // Header drag
            if (button == 0 && inRect(mouseX, mouseY, x, y, PANEL_W, PANEL_H)) {
                dragCatIdx = ci;
                dragOffX = (int) mouseX - x;
                dragOffY = (int) mouseY - y;
                return true;
            }

            // Module toggle
            for (int mi = 0; mi < modules.size(); mi++) {
                int my = y + PANEL_H + mi * MODULE_H;
                if (inRect(mouseX, mouseY, x, my, PANEL_W, MODULE_H)) {
                    if (button == 0) modules.get(mi).toggle();
                    return true;
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dx, double dy) {
        if (dragCatIdx >= 0) {
            catX[dragCatIdx] = (int) mouseX - dragOffX;
            catY[dragCatIdx] = (int) mouseY - dragOffY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dx, dy);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragCatIdx = -1;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // ESC
            toggle();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() { return false; }

    private boolean inRect(double mx, double my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }
}
