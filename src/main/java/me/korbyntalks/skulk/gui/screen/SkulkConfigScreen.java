package me.korbyntalks.skulk.gui.screen;

import me.korbyntalks.modConfigUtils.modConfigUtils;
import me.korbyntalks.skulk.Skulk;
import me.korbyntalks.skulk.config.Config;
import me.korbyntalks.skulk.gui.widget.AdjustableButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public final class SkulkConfigScreen extends Screen {
    private final String title = "Skulk Config";
    private Screen parent;
    private int preset = 1;

    public SkulkConfigScreen(Screen parent) {
        this.parent = parent;
    }

    private String showONorOFFinsteadofNumbers(String value) {
        if(value.contains("1")) {
            return value.replace("1", "ON");
        }
        return value.replace("0", "OFF");
    }

    private void toggleIntConfig(int confSettingInt, String confSetting) {
        if(confSettingInt == 0) {
            try {
                modConfigUtils.WriteConfigValue(confSetting, 1, Skulk.MOD_ID);
                Config.initializeConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                modConfigUtils.WriteConfigValue(confSetting, 0, Skulk.MOD_ID);
                Config.initializeConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void init() {
        this.buttons.clear();
        this.buttons.add(new ButtonWidget(0, this.width / 2 - 100, this.height / 6 + 168, "Done"));

        this.buttons.add(new OptionButtonWidget(1, this.width / 2 - 155, this.height / 6 + 24, showONorOFFinsteadofNumbers(Config.improvedMouse)));
        this.buttons.add(new OptionButtonWidget(2, this.width / 2 + 5, this.height / 6 + 24, showONorOFFinsteadofNumbers(Config.everlastingMusic)));
        this.buttons.add(new OptionButtonWidget(3, this.width / 2 - 155, this.height / 6 + 48, showONorOFFinsteadofNumbers(Config.quitGameButtons)));
        this.buttons.add(new OptionButtonWidget(4, this.width / 2 + 5, this.height / 6 + 48, showONorOFFinsteadofNumbers(Config.betacraftProxy)));
        this.buttons.add(new OptionButtonWidget(5, this.width / 2 - 155, this.height / 6 + 72, "Sensitivity: " + Config.GrabSensitivity()));
        this.buttons.add(new AdjustableButtonWidget(5, this.width / 2 - 155, this.height / 6 + 94, 75, 20, "Increase +.05"));
        this.buttons.add(new AdjustableButtonWidget(5, this.width / 2 - 79, this.height / 6 + 94, 75, 20, "Decrease -.05"));
    }

    private void setSensitivityPreset(double finalValue, ButtonWidget button, String presetName) {
        try {
            modConfigUtils.WriteConfigValue(Config.sensitivity, finalValue, Skulk.MOD_ID);
            Config.initializeConfig();
            button.message = "Sensitivity: " + presetName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void buttonClicked(ButtonWidget button) {
        if(button.id == 0) {
            this.minecraft.openScreen(parent);
        }

        if(button.id == 1) {
            toggleIntConfig(Config.GrabImprovedMouse(), Config.improvedMouse);
            button.message = showONorOFFinsteadofNumbers(Config.improvedMouse);
        }
        if(button.id == 2) {
            toggleIntConfig(Config.GrabEverlastingMusic(), Config.everlastingMusic);
            button.message = showONorOFFinsteadofNumbers(Config.everlastingMusic);
        }
        if(button.id == 3) {
            toggleIntConfig(Config.GrabQuitGameButtons(), Config.quitGameButtons);
            button.message = showONorOFFinsteadofNumbers(Config.quitGameButtons);
        }
        if(button.id == 4) {
            toggleIntConfig(Config.GrabBetacraftProxy(), Config.betacraftProxy);
            button.message = showONorOFFinsteadofNumbers(Config.betacraftProxy);
        }

        if(button.id == 5) {
            double value;
            double finalValue;

            if(Objects.equals(button.message, "Increase +.05")) {
                try {
                    value = Config.GrabSensitivity() + 0.05;

                    DecimalFormat decimalFormat = new DecimalFormat("0.##");
                    finalValue = Double.parseDouble(decimalFormat.format(value));

                    if(finalValue > 2.00) {
                        finalValue = 2.00;
                    } else if(finalValue < 0.04) {
                        finalValue = 0.05;
                    }

                    modConfigUtils.WriteConfigValue(Config.sensitivity, finalValue, Skulk.MOD_ID);
                    Config.initializeConfig();
                    this.buttons.set(5, new OptionButtonWidget(5, this.width / 2 - 155, this.height / 6 + 72, "Sensitivity: " + Config.GrabSensitivity()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if(Objects.equals(button.message, "Decrease -.05")) {
                try {
                    value = Config.GrabSensitivity() - 0.05;

                    DecimalFormat decimalFormat = new DecimalFormat("0.##");
                    finalValue = Double.parseDouble(decimalFormat.format(value));

                    if(finalValue > 2.00) {
                        finalValue = 2.00;
                    } else if(finalValue < 0.04) {
                        finalValue = 0.05;
                    }

                    modConfigUtils.WriteConfigValue(Config.sensitivity, finalValue, Skulk.MOD_ID);
                    Config.initializeConfig();
                    this.buttons.set(5, new OptionButtonWidget(5, this.width / 2 - 155, this.height / 6 + 72, "Sensitivity: " + Config.GrabSensitivity()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else { // Presets
                switch (preset) {
                    case 1:
                        preset++;
                        setSensitivityPreset(2.00, button, "HYPERSPEED!!!");
                        break;

                    case 2:
                        preset++;
                        setSensitivityPreset(0.25, button, "Normal");
                        break;

                    case 3:
                        preset++;
                        setSensitivityPreset(0.15, button, "Slowed");
                        break;

                    case 4:
                        preset++;
                        setSensitivityPreset(0.01, button, "*yawn*");
                        break;

                    default:
                        preset = 2;
                        setSensitivityPreset(2.00, button, "HYPERSPEED!!!");
                }
            }
        }
    }

    public void render(int mouseX, int mouseY, float tickDelta) {
        this.drawBackgroundTexture();
        SkulkConfigScreen.drawCenteredString(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(mouseX, mouseY, tickDelta);
    }
}
