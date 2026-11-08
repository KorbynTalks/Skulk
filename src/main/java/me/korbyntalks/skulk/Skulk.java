package me.korbyntalks.skulk;

import me.korbyntalks.skulk.config.Config;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Skulk implements ModInitializer {
    public static final String MOD_ID = "skulk";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Lubricating Indev with Skulk..");

        try {
            Config.initializeConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Config.GrabBetacraftProxy() == 1) {
            if(Config.GrabDebug() == 1) {
                Skulk.LOGGER.info("Using Betacraft Proxy.");
            }

            System.setProperty("http.proxyHost", "betacraft.ee");
            System.setProperty("http.proxyPort", "11702");
        }
    }
}
