package dev.nova.client;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Small first-pass module registry. The HUD is the first real module;
 * later modules can register their settings and activation logic here.
 */
public final class NovaModules {
    private static final Map<String, ModuleState> MODULES = new LinkedHashMap<>();

    private NovaModules() {}

    public static void registerDefaults() {
        register("HUD", true);
        register("Coordinates", true);
        register("FPS", true);
        register("Crystal ESP", false);
        register("Storage ESP", false);
    }

    public static void register(String name, boolean enabled) {
        MODULES.putIfAbsent(name, new ModuleState(enabled));
    }

    public static boolean isEnabled(String name) {
        ModuleState state = MODULES.get(name);
        return state != null && state.enabled;
    }

    public static void setEnabled(String name, boolean enabled) {
        ModuleState state = MODULES.get(name);
        if (state != null) state.enabled = enabled;
    }

    public static Map<String, Boolean> snapshot() {
        Map<String, Boolean> result = new LinkedHashMap<>();
        MODULES.forEach((name, state) -> result.put(name, state.enabled));
        return Collections.unmodifiableMap(result);
    }

    private static final class ModuleState {
        private boolean enabled;
        private ModuleState(boolean enabled) { this.enabled = enabled; }
    }
}
