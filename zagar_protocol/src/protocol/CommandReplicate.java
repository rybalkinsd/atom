package protocol;

import org.jetbrains.annotations.NotNull;
import protocol.model.Cell;
import protocol.model.pFood;
import protocol.model.pVirus;

/**
 * @author apomosov
 */
public final class CommandReplicate extends Command {
  @NotNull
  public static final String NAME = "cells";
  @NotNull
  private final pFood[] foodToAdd;
  @NotNull
  private final pFood[] foodToRemove;
  @NotNull
  private final Cell[] cells;
  @NotNull
  private final pVirus[] virus;

  public CommandReplicate(@NotNull pFood[] foodToAdd, @NotNull pFood[] foodToRemove,
                          @NotNull Cell[] cells, @NotNull pVirus[] viruss) {
    super(NAME);
    this.foodToAdd = foodToAdd;
    this.cells = cells;
    this.virus = viruss;
    this.foodToRemove = foodToRemove;
  }

  @NotNull
  public protocol.model.Cell[] getCells() {
    return cells;
  }

  @NotNull
  public pFood[] getFoodToAdd() {
    return foodToAdd;
  }
  @NotNull
  public pFood[] getFoodToRemove(){return foodToRemove;}
  @NotNull
  public pVirus[] getVirus() { return virus;}
}
