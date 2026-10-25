package dev.nova.client;

import com.mojang.blaze3d.platform.InputConstants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.resources.Identifier;

import org.lwjgl.glfw.GLFW;

public final class NovaUtilityClient implements ClientModInitializer {
    private static final String MOD_ID = "nova_utility";

    private static KeyMapping openGuiKey;
    private static KeyMapping toggleHudKey;

    @Override
    public void onInitializeClient() {
        NovaModules.registerDefaults();
        NovaCrystalESP.initialize();

        openGuiKey = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                "key.nova_utility.open_gui",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyMapping.Category.MISC
            )
        );

        toggleHudKey = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                "key.nova_utility.toggle_hud",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                KeyMapping.Category.MISC
            )
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new NovaClickGuiScreen());
                } else if (client.screen instanceof NovaClickGuiScreen) {
                    client.setScreen(null);
                }
            }

            while (toggleHudKey.consumeClick()) {
                NovaModules.setEnabled(
                    "HUD", !NovaModules.isEnabled("HUD")
                );
            }
        });

        ScreenEvents.AFTER_INIT.register(
            (client, screen, scaledWidth, scaledHeight) -> {
                if (screen instanceof TitleScreen) {
                    ScreenEvents.afterRender(screen).register(
                        (renderedScreen, graphics, mouseX, mouseY, tickDelta) -> {
                            Minecraft mc = Minecraft.getInstance();

                            int left = 10;
                            int bottom = renderedScreen.height - 44;

                            graphics.drawString(
                                mc.font, "NOVA UTILITY",
                                left, bottom, 0xFF8B5CF6, true
                            );

                            graphics.drawString(
                                mc.font, "Author: Rafay",
                                left, bottom + 12, 0xFFFFFFFF, true
                            );

                            graphics.drawString(
                                mc.font, "Dedicated to Gaza",
                                left, bottom + 24, 0xFF55CC77, true
                            );

                            graphics.drawString(
                                mc.font, "Free Palestine",
                                left, bottom + 36, 0xFFFFFFFF, true
                            );
                        }
                    );
                }
            }
        );

        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "nova_hud"),
            NovaUtilityClient::renderHud
        );
    }

    private static void renderHud(
        GuiGraphics graphics,
        DeltaTracker tickCounter
    ) {
        Minecraft mc = Minecraft.getInstance();

        if (!NovaModules.isEnabled("HUD")) return;

        int x = 8;
        int y = 8;
        int width = 170;
        int height = 24;

        if (mc.player != null) {
            if (NovaModules.isEnabled("FPS")) height += 13;
            if (NovaModules.isEnabled("Coordinates")) height += 13;
        }

        graphics.fill(
            x, y, x + width, y + height, 0xB0101420
        );

        graphics.fill(
            x, y, x + 3, y + height, 0xFF8B5CF6
        );

        graphics.drawString(
            mc.font, "NOVA UTILITY",
            x + 10, y + 7, 0xFFFFFFFF, true
        );

        int lineY = y + 20;

        if (mc.player != null && NovaModules.isEnabled("FPS")) {
            graphics.drawString(
                mc.font, "FPS  " + mc.getFps(),
                x + 10, lineY, 0xFFE5E7EB, true
            );
            lineY += 13;
        }

        if (mc.player != null && NovaModules.isEnabled("Coordinates")) {
            var player = mc.player;

            String coords = String.format(
                java.util.Locale.ROOT,
                "XYZ  %.1f  %.1f  %.1f",
                player.getX(),
                player.getY(),
                player.getZ()
            );

            graphics.drawString(
                mc.font, coords,
                x + 10, lineY, 0xFFE5E7EB, true
            );
        }
    }
}
