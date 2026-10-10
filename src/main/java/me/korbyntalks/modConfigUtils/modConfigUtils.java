package me.korbyntalks.modConfigUtils;

import me.korbyntalks.modConfigUtils.utils.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class modConfigUtils {

    private static final String loggerID = "Mod Config Utils";
    private static final Logger LOGGER = LoggerFactory.getLogger(loggerID);

    private final Path modConfigPath;

    public modConfigUtils(String modName) {
        modConfigPath = Paths.get(WorkingDirectory.Get() + "/" + modName + ".txt");

        CreateConfig();
    }

    private void CreateConfig() {
        if(Files.notExists(modConfigPath)) {
            LOGGER.info("a Config file for a Mod does not exist! Creating.");
            try {
                Files.createFile(modConfigPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void CreateConfigValue(Value value) {

    }
}
