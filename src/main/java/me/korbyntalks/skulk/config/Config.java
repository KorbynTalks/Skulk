package me.korbyntalks.skulk.config;

import me.korbyntalks.skulk.Skulk;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Config {

    static Path workingDirectory = Paths.get(System.getProperty("user.dir"));
    static Path confPath = Paths.get(workingDirectory + "/config/Skulk.txt");
    static String configFile;

    public static double sensitivity() {
        double sens = Double.parseDouble(configFile);

        return sens;
    }

    public static void CheckConfig() {
        if(Files.notExists(confPath)) {
            Skulk.LOGGER.info("Config file does not exist! Creating.");
            try {
                Files.createFile(confPath);
                FileWriter writer = new FileWriter(confPath.toFile());

                writer.write("0.25");
                writer.close();

                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            configFile = Files.readAllLines(confPath).toString(); configFile = configFile.replaceAll("[\\[\\]]","");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
