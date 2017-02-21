package protocol.commands;

import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;
import protocol.model.Cell;
import protocol.model.Food;

import java.util.List;

/**
 * @author apomosov
 */
public final class CommandReplicate extends Command {
  @NotNull
  public static final String NAME = "cells";
  @NotNull
  @Expose
  private final List<Food> food;
  @NotNull
  @Expose
  private final List<Cell> cells;

  public CommandReplicate(@NotNull List<Food> food, @NotNull List<Cell> cells) {
    super(NAME);
    this.food = food;
    this.cells = cells;
  }

  @NotNull
  public List<Cell> getCells() {
    return cells;
  }

  @NotNull
  public List<Food> getFood() {
    return food;
  }
}
