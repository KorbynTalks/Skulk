package me.korbyntalks.modConfigUtils;

public final class Value {
    public String ConfValue(String valueName, double baseValue) {
        return valueName + ": " + baseValue;
    }
    public String ConfValue(String valueName, float baseValue) {
        return valueName + ": " + baseValue;
    }
    public String ConfValue(String valueName, int baseValue) {
        return valueName + ": " + baseValue;
    }
}
