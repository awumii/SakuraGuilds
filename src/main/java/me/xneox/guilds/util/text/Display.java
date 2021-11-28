package me.xneox.guilds.util.text;

import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

// TODO: use it somewhere? i don't remember what i created this for
public class Display {
  private final String name;
  private final char icon;
  private final TextColor color;

  public Display(String name, char icon, TextColor color) {
    this.name = name;
    this.icon = icon;
    this.color = color;
  }

  public static Display of(@NotNull String name, char icon, @NotNull String hexColor) {
    return new Display(name, icon, TextColor.fromHexString(hexColor));
  }

  @NotNull
  public String name() {
    return this.name;
  }

  public char icon() {
    return this.icon;
  }

  @NotNull
  public TextColor color() {
    return this.color;
  }

  @NotNull
  @Override
  public String toString() {
    return "&" + this.color + this.icon + " " + this.name;
  }
}
