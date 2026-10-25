
package dev.nova.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EndCrystal;

public final class NovaCrystalESP {
    private static final double MAX_DISTANCE = 96.0;
    private static boolean initialized = false;

    private NovaCrystalESP() {}

    public static void initialize() {
        if (initialized) return;
        initialized = true;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!NovaModules.isEnabled("Crystal ESP")
                    || client.level == null
                    || client.player == null) {
                return;
            }

            var player = client.player;
            var style = GizmoStyle.stroke(0xFF8B5CF6, 2.0f);

            for (Entity entity : client.level.entitiesForRendering()) {
                if (!(entity instanceof EndCrystal)) continue;

                if (entity.distanceToSqr(player)
                        > MAX_DISTANCE * MAX_DISTANCE) {
                    continue;
                }

                Gizmos.cuboid(
                    entity.getBoundingBox().inflate(0.03),
                    style
                ).setAlwaysOnTop();
            }
        });
    }
}
