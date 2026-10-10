package me.korbyntalks.skulk.config;

import me.korbyntalks.modConfigUtils.*;
import me.korbyntalks.skulk.Skulk;

import java.io.IOException;

public class Config {
    static Value sensitivity = new Value("Sensitivity", 0.25);
    static String modName() {
        return Skulk.MOD_ID;
    }

    public static void initializeConfig() throws IOException {
        modConfigUtils.initializeModConfig(modName());

        modConfigUtils.CreateConfigValue(sensitivity, modName());
    }
}
