
package dev.nova.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public final class NovaClickGuiScreen extends Screen {
    private static final int PANEL_WIDTH = 220;
    private static final int ROW_HEIGHT = 25;

    private int panelX;
    private int panelY;
    private List<Map.Entry<String, Boolean>> modules;

    public NovaClickGuiScreen() {
        super(Component.literal("Nova Utility"));
    }

    @Override
    protected void init() {
        panelX = (width - PANEL_WIDTH) / 2;
        panelY = (height - 190) / 2;
        refreshModules();
    }

    private void refreshModules() {
        modules = new ArrayList<>(
            NovaModules.snapshot().entrySet()
        );
    }

    @Override
    public void render(
        GuiGraphics graphics,
        int mouseX,
        int mouseY,
        float partialTick
    ) {
        graphics.fill(
            0, 0, width, height, 0x99060910
        );

        int panelHeight = 48 + modules.size() * ROW_HEIGHT;

        graphics.fill(
            panelX, panelY,
            panelX + PANEL_WIDTH, panelY + panelHeight,
            0xF0141722
        );

        graphics.fill(
            panelX, panelY,
            panelX + PANEL_WIDTH, panelY + 3,
            0xFF8B5CF6
        );

        graphics.drawCenteredString(
            font, "NOVA UTILITY",
            panelX + PANEL_WIDTH / 2,
            panelY + 12,
            0xFFFFFFFF
        );

        graphics.drawCenteredString(
            font, "CLICK MODULES TO TOGGLE",
            panelX + PANEL_WIDTH / 2,
            panelY + 29,
            0xFFB9A5FF
        );

        for (int i = 0; i < modules.size(); i++) {
            var entry = modules.get(i);
            int rowY = panelY + 43 + i * ROW_HEIGHT;
            boolean hovered =
                mouseX >= panelX + 8
                && mouseX <= panelX + PANEL_WIDTH - 8
                && mouseY >= rowY
                && mouseY < rowY + ROW_HEIGHT;

            graphics.fill(
                panelX + 8, rowY,
                panelX + PANEL_WIDTH - 8,
                rowY + ROW_HEIGHT - 2,
                hovered ? 0xFF303044 : 0xFF202330
            );

            graphics.drawString(
                font, entry.getKey(),
                panelX + 16, rowY + 7,
                0xFFE5E7EB
            );

            boolean enabled = entry.getValue();

            graphics.drawString(
                font, enabled ? "ON" : "OFF",
                panelX + PANEL_WIDTH - 42,
                rowY + 7,
                enabled ? 0xFF8B5CF6 : 0xFF9CA3AF
            );
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(
        double mouseX,
        double mouseY,
        int button
    ) {
        if (button == 0) {
            for (int i = 0; i < modules.size(); i++) {
                int rowY = panelY + 43 + i * ROW_HEIGHT;

                if (
                    mouseX >= panelX + 8
                    && mouseX <= panelX + PANEL_WIDTH - 8
                    && mouseY >= rowY
                    && mouseY < rowY + ROW_HEIGHT
                ) {
                    String name = modules.get(i).getKey();
                    boolean enabled = NovaModules.isEnabled(name);

                    NovaModules.setEnabled(name, !enabled);
                    refreshModules();
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(
        int keyCode,
        int scanCode,
        int modifiers
    ) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE
            || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            Minecraft.getInstance().setScreen(null);
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
