package me.korbyntalks.skulk.config;

import me.korbyntalks.modConfigUtils.*;

public class Config {
    Value sensitivity = new Value("Sensitivity", 0.25);

    private Config() {
        modConfigUtils.CreateConfigValue(sensitivity);
    }
}
