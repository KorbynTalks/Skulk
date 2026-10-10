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

    public static void CreateConfigValue(Value value, String modName) throws IOException {
        FileWriter writer = new FileWriter(modConfigPath(modName).toFile());

        writer.write(value.toString());
    }
}
