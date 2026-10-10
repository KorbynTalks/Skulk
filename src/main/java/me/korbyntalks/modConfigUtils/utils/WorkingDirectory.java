package me.korbyntalks.modConfigUtils.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkingDirectory {
    /**
     * @return Current Working Directory for Minecraft.
     */
    public static Path Get() {
        return Paths.get(System.getProperty("user.dir"));
    }
}
