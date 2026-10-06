package me.korbyntalks.skulk;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Skulk implements ModInitializer {
    public static final String MOD_ID = "skulk";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Skulk has been Initialized.");
    }
}
