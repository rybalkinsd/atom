package utils;

import model.Cell;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * @author apomosov
 */
public class EatComparator implements Comparator<Cell> {
  @Override
  public int compare(@NotNull Cell o1, @NotNull Cell o2) {
    if (o1.getMass() / o2.getMass() > 1.2) {
      return 1;
    } else if (o2.getMass() / o1.getMass() > 1.2) {
      return -1;
    } else return 0;
  }
}
