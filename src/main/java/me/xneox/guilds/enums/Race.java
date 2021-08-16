package me.xneox.guilds.enums;

import com.archyx.aureliumskills.stats.Stats;
import java.util.Arrays;
import java.util.List;
import me.xneox.guilds.util.text.Display;

public enum Race {
  NONE(
      Stats.WISDOM,
      0,
      Display.of("Brak", '?', "#ffffff"),
      "",
      "&7Nawet jeśli wybierzesz tą opcję, nie będziesz musiał",
      "&7czekać przed zmianą na inną rasę.",
      "&7Gdy nie masz rasy, nie posiadasz żadnych ulepszeń"),
  HUMAN(
      Stats.HEALTH,
      8,
      Display.of("Człowiek", '❤', "#ee6c4d"),
      "",
      "&7Ludzie posiadają zwiększoną statystykę",
      "&c❤ Zdrowia (+8) [16 HP, 1 serce]",
      "",
      "&7Dzięki temu, są w stanie dłużej przeżyć."),
  GOBLIN(
      Stats.LUCK,
      24,
      Display.of("Goblin", '☘', "#b5e48c"),
      "",
      "&7Gobliny posiadają zwiększoną statystykę",
      "&2☘ Szczęścia (+24)",
      "",
      "&7Dzięki temu, zdobywają lepsze łupy ze skrzyń,",
      "&7oraz łowienia, i mają szansę na podwójny drop."),
  ELF(
      Stats.REGENERATION,
      22,
      Display.of("Elf", '❥', "#e56b6f"),
      "",
      "&7Elfy posiadają zwiększoną statystykę",
      "&6❥ Regeneracji (+22)",
      "",
      "&7Dzięki temu, szybciej leczą swoje rany."),
  DWARF(
      Stats.STRENGTH,
      12,
      Display.of("Krasnolud", '➽', "#80b918"),
      "",
      "&7Krasnoludy posiadają zwiększoną statystykę",
      "&4➽ Siły (+12) [~6% DMG]",
      "",
      "&7Dzięki temu, łatwiej pokonują przeciwników."),
  ORC(
      Stats.TOUGHNESS,
      24,
      Display.of("Ork", '✦', "#6f1d1b"),
      "",
      "&7Orkowie posiadają zwiększoną statystykę",
      "&5✦ Twardości (+24) [~7% DEF]",
      "",
      "&7Dzięki temu, są w stanie wytrzymać więcej otrzymywanych obrażeń"),
  HOBBIT(
      Stats.WISDOM,
      16,
      Display.of("Hobbit", '✿', "#43aa8b"),
      "",
      "&7Hobbici posiadają zwiększoną statystykę",
      "&9✿ Inteligencji (+16)",
      "",
      "&7Dzięki temu, zdobywają więcej EXP, zmniejszony koszt kowadła,",
      "&7oraz więcej many.");

  private final Display display;
  private final List<String> description;
  private final Stats stat;
  private final int multiplier;

  Race(Stats stat, int multiplier, Display display, String... description) {
    this.display = display;
    this.description = Arrays.asList(description);
    this.stat = stat;
    this.multiplier = multiplier;
  }

  public Display display() {
    return this.display;
  }

  public Stats stat() {
    return this.stat;
  }

  public int multiplier() {
    return this.multiplier;
  }

  public List<String> description() {
    return this.description;
  }
}
