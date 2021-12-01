package me.xneox.guilds.config;

import java.util.List;
import org.bukkit.Material;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

@SuppressWarnings("ALL") // make intellij shut up about using final fields that would break the config loader.
@ConfigSerializable
public class HologramConfig {
  private Claim claim = new Claim();
  private Nexus nexus = new Nexus();

  @ConfigSerializable
  public static class Nexus {
    private boolean enabled = true;
    private int heightAboveGround = 3;
    private Material icon = Material.ENDER_EYE;
    private List<String> text = List.of(
        "&6&lNEXUS {GUILD}",
        "&7Ilość żyć: &c{HP}/{MAX-HP}",
        "&7Tarcza: &c{SHIELD}");

    public boolean enabled() {
      return this.enabled;
    }

    public int heightAboveGround() {
      return this.heightAboveGround;
    }

    public Material icon() {
      return this.icon;
    }

    public List<String> text() {
      return this.text;
    }
  }

  @ConfigSerializable
  public static class Claim {
    private boolean enabled = true;
    private int displayDurationSeconds = 20;
    private int heightAboveGround = 3;
    private Material icon = Material.DIAMOND_SHOVEL;
    private List<String> text = List.of(
        "&6&lGILDIA {GUILD} ZAJMUJE TEN TEREN",
        "&7Lokalizacja: &f{LOCATION}",
        "&7Przez: &f{CLAIMER}");

    public boolean enabled() {
      return this.enabled;
    }

    public int displayDurationSeconds() {
      return this.displayDurationSeconds;
    }

    public int heightAboveGround() {
      return this.heightAboveGround;
    }

    public Material icon() {
      return this.icon;
    }

    public List<String> text() {
      return this.text;
    }
  }

  public Claim claim() {
    return this.claim;
  }

  public Nexus nexus() {
    return this.nexus;
  }
}
