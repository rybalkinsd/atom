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

    if ((double)o1.getMass() / (double)o2.getMass() > 1.2) {
      return 1;
    } else
      return 0;
  }
}
