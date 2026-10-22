package dev.nova.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public final class NovaUtilityClient implements ClientModInitializer {
    private static final String MOD_ID = "nova_utility";

    @Override
    public void onInitializeClient() {
        NovaModules.registerDefaults();
        HudElementRegistry.attachElementBefore(
            VanillaHudElements.CHAT,
            Identifier.fromNamespaceAndPath(MOD_ID, "nova_hud"),
            NovaUtilityClient::renderHud
        );
    }

    private static void renderHud(GuiGraphicsExtractor graphics, DeltaTracker tickCounter) {
        Minecraft minecraft = Minecraft.getInstance();
        int x = 8;
        int y = 8;
        int width = 154;
        int height = minecraft.player == null ? 36 : 64;

        graphics.fill(x, y, x + width, y + height, 0xB0101420);
        graphics.fill(x, y, x + 3, y + height, 0xFF8B5CF6);
        graphics.text(minecraft.font, "NOVA UTILITY", x + 10, y + 7, 0xFFFFFFFF, true);
        graphics.text(minecraft.font, "HUD  •  " + (NovaModules.isEnabled("HUD") ? "ON" : "OFF"),
            x + 10, y + 20, 0xFFB9A5FF, true);
        graphics.text(minecraft.font, "FPS  " + minecraft.getFps(), x + 10, y + 33, 0xFFE5E7EB, true);

        if (minecraft.player != null) {
            var player = minecraft.player;
            String coords = String.format("XYZ  %.1f  %.1f  %.1f", player.getX(), player.getY(), player.getZ());
            graphics.text(minecraft.font, coords, x + 10, y + 46, 0xFFE5E7EB, true);
        }
    }
}
