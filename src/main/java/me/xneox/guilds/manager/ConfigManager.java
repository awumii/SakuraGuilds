package me.xneox.guilds.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.config.GuiConfiguration;
import me.xneox.guilds.config.HologramConfig;
import me.xneox.guilds.config.MainConfiguration;
import me.xneox.guilds.config.MessagesConfiguration;
import me.xneox.guilds.integration.Integrations;
import me.xneox.guilds.util.ConfigurationLoader;
import me.xneox.guilds.util.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

public class ConfigManager {
  private MainConfiguration main;
  private MessagesConfiguration messages;
  private GuiConfiguration gui;
  private HologramConfig holograms;

  // todo reload command
  public void loadConfigurations() {
    try {
      // Create plugin directory
      Files.createDirectory(Path.of(Integrations.DIRECTORY));

      this.main = new ConfigurationLoader<>(MainConfiguration.class, this.createLoader("config.conf")).load();
      this.messages = new ConfigurationLoader<>(MessagesConfiguration.class, this.createLoader("messages.conf")).load();
      this.gui = new ConfigurationLoader<>(GuiConfiguration.class, this.createLoader("gui.conf")).load();
      this.holograms = new ConfigurationLoader<>(HologramConfig.class, this.createLoader("holograms.conf")).load();
    } catch (IOException exception) {
      LogUtils.catchException("Couldn't load the configuration file(s)", exception);
    }
  }

  @NotNull
  private HoconConfigurationLoader createLoader(@NotNull String fileName) {
    return HoconConfigurationLoader.builder()
        .file(new File(Integrations.DIRECTORY, fileName))
        .build();
  }

  // I HATE THESE METHODS BUT FOR THE SAKE OF CODE READABILITY EVERYWHERE ELSE I WILL LEAVE IT THERE.

  public static MainConfiguration config() {
    return SakuraGuildsPlugin.get().configManager().main;
  }

  public static MessagesConfiguration messages() {
    return SakuraGuildsPlugin.get().configManager().messages;
  }

  public static GuiConfiguration gui() {
    return SakuraGuildsPlugin.get().configManager().gui;
  }

  public static HologramConfig holograms() {
    return SakuraGuildsPlugin.get().configManager().holograms;
  }
}
