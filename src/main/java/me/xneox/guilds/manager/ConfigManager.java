package me.xneox.guilds.manager;

import java.io.File;
import me.xneox.guilds.SakuraGuildsPlugin;
import me.xneox.guilds.config.GuiConfiguration;
import me.xneox.guilds.config.MessagesConfiguration;
import me.xneox.guilds.config.MainConfiguration;
import me.xneox.guilds.hook.HookUtils;
import me.xneox.guilds.util.ConfigurationLoader;
import me.xneox.guilds.util.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

public class ConfigManager {
  private MainConfiguration main;
  private MessagesConfiguration messages;
  private GuiConfiguration gui;

  // todo reload command
  public void loadConfigurations() {
    // Create plugin directory
    new File(HookUtils.DIRECTORY).mkdir();

    try {
      this.main = new ConfigurationLoader<>(MainConfiguration.class, this.loader("config.conf")).load();
      this.messages = new ConfigurationLoader<>(MessagesConfiguration.class, this.loader("messages.conf")).load();
      this.gui = new ConfigurationLoader<>(GuiConfiguration.class, this.loader("gui.conf")).load();
    } catch (ConfigurateException exception) {
      LogUtils.catchException("Couldn't load the configuration file(s)", exception);
    }
  }

  @NotNull
  private HoconConfigurationLoader loader(@NotNull String fileName) {
    return HoconConfigurationLoader.builder()
        .file(new File(HookUtils.DIRECTORY, fileName))
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
}
