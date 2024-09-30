package dev.leafs4n.customdurabilitybar;

import dev.leafs4n.customdurabilitybar.config.DurabilityBarConfig;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDurabilityBar implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("customdurabilitybar");
	public static final DurabilityBarConfig CONFIG = DurabilityBarConfig.createAndLoad();

	@Override
	public void onInitialize() {
	}
}