
package dev.nova.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
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
        modules = new ArrayList<>(NovaModules.snapshot().entrySet());
    }

    @Override
    public void render(
        GuiGraphics graphics, int mouseX, int mouseY, float partialTick
    ) {
        graphics.fill(0, 0, width, height, 0x99060910);

        int panelHeight = 48 + modules.size() * ROW_HEIGHT;

        graphics.fill(
            panelX, panelY, panelX + PANEL_WIDTH,
            panelY + panelHeight, 0xF0141722
        );
        graphics.fill(
            panelX, panelY, panelX + PANEL_WIDTH,
            panelY + 3, 0xFF8B5CF6
        );

        graphics.drawCenteredString(
            font, "NOVA UTILITY", panelX + PANEL_WIDTH / 2,
            panelY + 12, 0xFFFFFFFF
        );
        graphics.drawCenteredString(
            font, "CLICK MODULES TO TOGGLE",
            panelX + PANEL_WIDTH / 2, panelY + 29, 0xFFB9A5FF
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
                panelX + PANEL_WIDTH - 8, rowY + ROW_HEIGHT - 2,
                hovered ? 0xFF303044 : 0xFF202330
            );

            graphics.drawString(
                font, entry.getKey(), panelX + 16, rowY + 7,
                0xFFE5E7EB
            );

            boolean enabled = entry.getValue();
            graphics.drawString(
                font, enabled ? "ON" : "OFF",
                panelX + PANEL_WIDTH - 42, rowY + 7,
                enabled ? 0xFF8B5CF6 : 0xFF9CA3AF
            );
        }

        super.render(graphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (event.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            double mouseX = event.x();
            double mouseY = event.y();

            for (int i = 0; i < modules.size(); i++) {
                int rowY = panelY + 43 + i * ROW_HEIGHT;

                if (
                    mouseX >= panelX + 8
                    && mouseX <= panelX + PANEL_WIDTH - 8
                    && mouseY >= rowY
                    && mouseY < rowY + ROW_HEIGHT
                ) {
                    String name = modules.get(i).getKey();
                    NovaModules.setEnabled(
                        name, !NovaModules.isEnabled(name)
                    );
                    refreshModules();
                    return true;
                }
            }
        }

        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (
            event.key() == GLFW.GLFW_KEY_ESCAPE
            || event.key() == GLFW.GLFW_KEY_RIGHT_SHIFT
        ) {
            Minecraft.getInstance().setScreen(null);
            return true;
        }

        return super.keyPressed(event);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
