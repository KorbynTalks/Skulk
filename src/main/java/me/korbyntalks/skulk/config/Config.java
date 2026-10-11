package me.korbyntalks.skulk.config;

import me.korbyntalks.modConfigUtils.Value;
import me.korbyntalks.modConfigUtils.modConfigUtils;
import me.korbyntalks.skulk.Skulk;

import java.io.IOException;

public class Config {
    static String sensitivity = new Value().ConfValue("Sensitivity", 0.25);
    static String modName() {
        return Skulk.MOD_ID;
    }

    public static void initializeConfig() throws IOException {
        modConfigUtils.initializeModConfig(modName());

        if(!modConfigUtils.ConfigValueExists(sensitivity, modName())) {
            modConfigUtils.CreateConfigValue(sensitivity, modName());
        }

        sensitivity = modConfigUtils.ReadConfigValue(sensitivity, modName());

        Skulk.LOGGER.info("is " + sensitivity + " the modified one?");
    }
}
