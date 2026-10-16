package me.korbyntalks.modConfigUtils;

public final class Value {
    public Value() {

    }

    public String ConfValue(String valueName, double baseValue) {
        String finalValue = valueName + ": " + baseValue;

        return finalValue;
    }
    public String ConfValue(String valueName, float baseValue) {
        String finalValue = valueName + ": " + baseValue;

        return finalValue;
    }
    public String ConfValue(String valueName, int baseValue) {
        String finalValue = valueName + ": " + baseValue;

        return finalValue;
    }
    public String ConfValue(String valueName, boolean baseValue) {
        String finalValue = valueName + ": " + baseValue;

        return finalValue;
    }
}
