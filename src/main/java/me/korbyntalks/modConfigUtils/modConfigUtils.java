package me.korbyntalks.modConfigUtils;

import me.korbyntalks.modConfigUtils.utils.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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
            // Searching for the Config file that doesn't exist. *Easter Egg Found!*
            try {
                Files.createFile(modConfigPath(modName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Creates a Config value. If it already exists inside the Config file, it will do nothing,
     * however you should probably check if it is there with
     * {@link modConfigUtils#ConfigValueExists(String, String)} to prevent Log Spam.
     * @param value The value to create.
     * @param modName Your Mod's name.
     * @throws IOException if the Config file could not be read for whatever reason.
     * Probably Windows putting permissions on files that don't need to be locked down! (:
     */
    public static void CreateConfigValue(String value, String modName) throws IOException {
        if(!Files.readAllLines(modConfigPath(modName)).toString().contains(value.replaceAll("[\\d.]", ""))) {
            try (FileWriter writer = new FileWriter(modConfigPath(modName).toFile(), true);
                 BufferedWriter out = new BufferedWriter(writer)) {
                out.append(value);
                out.newLine();
                out.flush();
            }
        } else {
            LOGGER.info("Config Value " + value.replaceAll("[\\d.:]", "") + "already exists.");
        }
    }

    /**
     * Reads a value from the Mod's Config file.
     * <p>
     *     This returns the full line from the Config file, so you will have to remove the "name" from the value manually.
     *     For example, You call {@code ReadConfigValue()} for your value "sensitivity",
     *     which in the file is set to 0.25. It will bring back this:
     * </p>
     * {@code Sensitivity: 0.25}
     * <p>
     *     Then you do something like <blockquote>Double.parseDouble(sensitivity.replaceAll("[^0-9.]", ""));</blockquote>
     *     to get rid of the name from the beginning so you can use the value as an actual Double.
     * </p>
     * @param value The value to read.
     * @param modName Your Mod's name.
     * @return The full line for the Config value you wanted to read, straight from the file.
     * @throws IOException if the Config file could not be read for whatever reason.
     * Probably Windows putting permissions on files that don't need to be locked down! (:
     */
    public static String ReadConfigValue(String value, String modName) throws IOException {
        Object finalValue;

        Optional<String> foundValue = Files.lines(modConfigPath(modName))
                .filter(line -> line.contains(value.replaceAll("[\\d.]", ""))).findFirst();
        if(foundValue.isPresent()) {
            finalValue = foundValue.get().replace(":", "");

            return finalValue.toString();
        }
        return "dummy value. Something went wrong.."; // What are you, a dummy? *Easter Egg Found!*
    }

    /**
     * Checks if a Config value exists inside the Config file.
     * @param value The value to check.
     * @param modName Your Mod's name.
     * @return {@code true} if it exists, {@code false} if it isn't.
     * @throws IOException if the Config file could not be read for whatever reason.
     * Probably Windows putting permissions on files that don't need to be locked down! (:
     */
    public static boolean ConfigValueExists(String value, String modName) throws IOException {
        return Files.readAllLines(modConfigPath(modName)).toString().contains(value.replaceAll("[\\d.]", ""));
    }
}
