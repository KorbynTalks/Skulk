package me.korbyntalks.modConfigUtils;

import me.korbyntalks.modConfigUtils.utils.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class modConfigUtils {

    private static final String loggerID = "Mod Config Utils";
    private static final Logger LOGGER = LoggerFactory.getLogger(loggerID);

    private static Path modConfigPath(String modName) {
        return Paths.get(WorkingDirectory.Get() + "/config/" + modName + ".txt");
    }

    public static void initializeModConfig(String modName) {
        CreateConfig(modName);
    }

    private static void CreateConfig(String modName) {
        if(Files.notExists(modConfigPath(modName))) {
            LOGGER.info("a Config file for a Mod does not exist! Creating.");
            try {
                Files.createFile(modConfigPath(modName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a Config Value. If it already exists, it will do nothing, however you should probably check if it is there with {@code CheckConfigValue()} to prevent Log Spam.
     * @param value
     * @param modName
     * @throws IOException if the Config file could not be read for whatever reason. Probably Windows putting permissions on files that don't need to be locked down! (:
     */
    public static void CreateConfigValue(String value, String modName) throws IOException {
        if(!Files.readAllLines(modConfigPath(modName)).toString().contains(value.replaceAll("[\\d.]", ""))) {
            try (FileWriter writer = new FileWriter(modConfigPath(modName).toFile())) {
                writer.write(value);
            }
        } else {
            LOGGER.info("Config Value " + value.replaceAll("[\\d.:]", "") + "already exists.");
        }
    }

    /**
     * Checks if a Config Value exists.
     * @param value
     * @param modName
     * @return {@code true} if it exists, {@code false} if it isn't.
     * @throws IOException if the Config file could not be read for whatever reason. Probably Windows putting permissions on files that don't need to be locked down! (:
     */
    public static boolean CheckConfigValue(String value, String modName) throws IOException {
        return Files.readAllLines(modConfigPath(modName)).toString().contains(value.replaceAll("[\\d.]", ""));
    }
}
