package me.xneox.guilds.enums;

import com.archyx.aureliumskills.stats.Stats;
import java.util.Arrays;
import java.util.List;

public enum Race {
  NONE(
      Stats.WISDOM,
      0,
      "&cBrak (Domyślnie)",
      "&7Nawet jeśli wybierzesz tą opcję, nie będziesz musiał",
      "&7czekać przed zmianą na inną rasę.",
      "&7Gdy nie masz rasy, nie posiadasz żadnych ulepszeń"),
  HUMAN(
      Stats.HEALTH,
      8,
      "&6Człowiek",
      "",
      "&7Ludzie posiadają zwiększoną statystykę",
      "&c❤ Zdrowia (+8)",
      "",
      "&7Dzięki temu, są w stanie dłużej przeżyć."),
  GOBLIN(
      Stats.LUCK,
      24,
      "&aGoblin",
      "",
      "&7Gobliny posiadają zwiększoną statystykę",
      "&2☘ Szczęścia (+24)",
      "",
      "&7Dzięki temu, zdobywają lepsze łupy ze skrzyń,",
      "&7oraz łowienia, i mają szansę na podwójny drop."),
  ELF(
      Stats.REGENERATION,
      16,
      "&dElf",
      "",
      "&7Elfy posiadają zwiększoną statystykę",
      "&6❥ Regeneracji (+16)",
      "",
      "&7Dzięki temu, szybciej leczą swoje rany."),
  DWARF(
      Stats.STRENGTH,
      12,
      "&2Krasnolud",
      "",
      "&7Krasnoludy posiadają zwiększoną statystykę",
      "&4➽ Siły (+12)",
      "",
      "&7Dzięki temu, łatwiej pokonują przeciwników."),
  ORC(
      Stats.TOUGHNESS,
      16,
      "&cOrk",
      "",
      "&7Orkowie posiadają zwiększoną statystykę",
      "&5✦ Twardości (+16)",
      "",
      "&7Dzięki temu, są w stanie wytrzymać więcej otrzymywanych obrażeń"),
  MORG(
      Stats.WISDOM,
      14,
      "&bMorg",
      "",
      "&7Morgowie posiadają zwiększoną statystykę",
      "&9✿ Inteligencji (+14)",
      "",
      "&7Dzięki temu, zdobywają więcej EXP, zmniejszony koszt kowadła,",
      "&7oraz więcej many.");

  private final String title;
  private final List<String> description;
  private final Stats stat;
  private final int multiplier;

  Race(Stats stat, int multiplier, String title, String... description) {
    this.title = title;
    this.description = Arrays.asList(description);
    this.stat = stat;
    this.multiplier = multiplier;
  }

  public String title() {
    return this.title;
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
