package me.xneox.guilds.util.text;

import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

// TODO: use it somewhere? i don't remember what i created this for
public record Display(String name, char icon, TextColor color) {
  public static Display of(@NotNull String name, char icon, @NotNull String hexColor) {
    return new Display(name, icon, TextColor.fromHexString(hexColor));
  }

  @NotNull
  @Override
  public String toString() {
    return "&" + this.color + this.icon + " " + this.name;
  }
}
