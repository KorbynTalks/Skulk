package me.korbyntalks.modConfigUtils;

import me.korbyntalks.modConfigUtils.utils.*;
import me.korbyntalks.skulk.Skulk;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {

    String configFile;

    public Config(String modName) {
        String modConfigPath = WorkingDirectory.GetPath() + "/" + modName + ".txt";

        CreateConfig(modConfigPath);
    }
    private void CreateConfig(String path) {
        Path confPath = Paths.get(path);

        if(Files.notExists(confPath)) {
            Skulk.LOGGER.info("Config file does not exist! Creating.");
            try {
                Files.createFile(confPath);

//                FileWriter writer = new FileWriter(confPath.toFile());
//
//                writer.write("Sensitivity: ");
//                writer.write("0.25");
//                writer.close();

                configFile = Files.readAllLines(confPath).toString(); configFile = configFile.replaceAll("[^\\d.]", "");

                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            configFile = Files.readAllLines(confPath).toString(); configFile = configFile.replaceAll("[^\\d.]", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
