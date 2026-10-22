package dev.nova.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NovaUtilityClient implements ClientModInitializer {
    public static final String MOD_ID = "nova_utility";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Nova Utility initialized. Module framework is ready for development.");
    }
}
