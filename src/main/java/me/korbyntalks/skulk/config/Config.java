package me.korbyntalks.skulk.config;

import me.korbyntalks.modConfigUtils.Value;
import me.korbyntalks.modConfigUtils.modConfigUtils;
import me.korbyntalks.skulk.Skulk;

import java.io.IOException;

public class Config {
    static String sensitivity = new Value().ConfValue("Sensitivity", 0.25);
    static String improvedMouse = new Value().ConfValue("Improved Mouse Control", 1);
    static String debugMode = new Value().ConfValue("debug", 0);

    static String modName() {
        return Skulk.MOD_ID;
    }

    public static void initializeConfig() throws IOException {
        modConfigUtils.initializeModConfig(modName());

        if(!modConfigUtils.ConfigValueExists(sensitivity, modName())) {
            modConfigUtils.CreateConfigValue(sensitivity, modName());
        } else {
            sensitivity = modConfigUtils.ReadConfigValue(sensitivity, modName());
        }

        if(!modConfigUtils.ConfigValueExists(improvedMouse, modName())) {
            modConfigUtils.CreateConfigValue(improvedMouse, modName());
        } else {
            improvedMouse = modConfigUtils.ReadConfigValue(improvedMouse, modName());
        }

        if(!modConfigUtils.ConfigValueExists(debugMode, modName())) {
            modConfigUtils.CreateConfigValue(debugMode, modName());
        } else {
            debugMode = modConfigUtils.ReadConfigValue(debugMode, modName());
        }
    }

    public static double GrabSensitivity() {
        double finalValue;

        try {
            finalValue = Double.parseDouble(sensitivity.replaceAll("[^0-9.]", ""));
        } catch (NumberFormatException e) {
            finalValue = 0.25;
        }

        if(finalValue > 2.00) {
            finalValue = 2.00;
        } else if(finalValue < 0.01) {
            finalValue = 0.01;
        }

        return finalValue;
    }

    public static int GrabImprovedMouse() {
        int finalValue;

        try {
            finalValue = Integer.parseInt(improvedMouse.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            finalValue = 1;
        }

        if(finalValue > 1) {
            finalValue = 1;
        } else if(finalValue < 0) {
            finalValue = 0;
        }

        return finalValue;
    }

    public static int GrabDebug() {
        int finalValue;

        try {
            finalValue = Integer.parseInt(debugMode.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            finalValue = 0;
        }

        if(finalValue > 1) {
            finalValue = 1;
        } else if(finalValue < 0) {
            finalValue = 0;
        }

        return finalValue;
    }
}
