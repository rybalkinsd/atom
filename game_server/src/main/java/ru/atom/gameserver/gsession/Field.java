package ru.atom.gameserver.gsession;

import java.util.ArrayList;
import java.util.List;

public class Field {

    public static final int COLS = 17;
    public static final int ROWS = 13;

    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int BOX = 2;
    public static final int BONUS = 3;

    private final int[][] mapOfTypes = new int[COLS][ROWS];
    private final int[][] mapOfId = new int[COLS][ROWS];

    public Field() {
        for (int i = 0; i < COLS; ++i) {
            mapOfTypes[i][0] = WALL;
            mapOfTypes[i][ROWS - 1] = WALL;
        }
        for (int i = 1; i < ROWS; ++i) {
            mapOfTypes[0][i] = WALL;
            mapOfTypes[COLS - 1][i] = WALL;
        }
        for (int i = 1; i < ROWS - 1; ++i) {
            int startIndex = 1;
            int finishIndex = COLS - 1;
            if (i % 2 == 1) {
                if (i == 1 || i == ROWS - 2) {
                    startIndex = 3;
                    finishIndex = COLS - 3;
                }
                for (int j = startIndex; j < finishIndex; ++j) {
                    mapOfTypes[j][i] = BOX;
                }
            } else {
                if (i == 2 || i == ROWS - 3) {
                    startIndex = 2;
                    finishIndex = COLS - 2;
                }
                for (int j = startIndex; j < finishIndex; ++j) {
                    if (j % 2 == 1) {
                        mapOfTypes[j][i] = BOX;
                    } else {
                        mapOfTypes[j][i] = WALL;
                    }
                }
            }
        }
    }

    public int getCellType(Cell cell) {
        return mapOfTypes[cell.col][cell.row];
    }

    public void setId(Cell cell, int id) {
        mapOfId[cell.col][cell.row] = id;
    }

    public int getId(Cell cell) {
        return mapOfId[cell.col][cell.row];
    }

    public boolean canMove(Cell currCell, Cell nextCell) {
        if (currCell.equals(nextCell)) {
            return true;
        } else {
            return availableCell(mapOfTypes[nextCell.col][nextCell.row]);
        }
    }

    public List<Cell> getFireCells(Cell initialPoint, int power) {
        List<Cell> fireCells = new ArrayList<>();
        fireCells.add(initialPoint);
        iterateByColumns(fireCells, initialPoint, power);
        iterateByRows(fireCells, initialPoint, power);
        return fireCells;
    }

    public List<Cell> applyFireCells(List<Cell> fireCells) {
        List<Cell> boxCells = new ArrayList<>();
        for (Cell cell : fireCells) {
            if (mapOfTypes[cell.col][cell.row] == BOX) {
                boxCells.add(cell);
                mapOfTypes[cell.col][cell.row] = EMPTY;
            }
        }
        return boxCells;
    }

    public void setBonus(Cell cell) {
        mapOfTypes[cell.col][cell.row] = BONUS;
    }

    private void iterateByColumns(List<Cell> fireCells, Cell initialPoint, int power) {
        int row = initialPoint.row;
        for (int i = 1; i <= power; ++i) {
            int col = initialPoint.col - i;
            if (validFireCell(col, row)) {
                fireCells.add(new Cell(col, row));
            }
            if (stopFireCell(col, row)) {
                break;
            }
        }
        for (int i = 1; i <= power; ++i) {
            int col = initialPoint.col + i;
            if (validFireCell(col, row)) {
                fireCells.add(new Cell(col, row));
            }
            if (stopFireCell(col, row)) {
                break;
            }
        }
    }

    private void iterateByRows(List<Cell> fireCells, Cell initialPoint, int power) {
        int col = initialPoint.col;
        for (int i = 1; i <= power; ++i) {
            int row = initialPoint.row - i;
            if (validFireCell(col, row)) {
                fireCells.add(new Cell(col, row));
            }
            if (stopFireCell(col, row)) {
                break;
            }
        }
        for (int i = 1; i <= power; ++i) {
            int row = initialPoint.row + i;
            if (validFireCell(col, row)) {
                fireCells.add(new Cell(col, row));
            }
            if (stopFireCell(col, row)) {
                break;
            }
        }
    }

    private boolean availableCell(int val) {
        return val == EMPTY || val == BONUS;
    }

    private boolean validFireCell(int col, int row) {
        return mapOfTypes[col][row] != WALL;
    }

    private boolean stopFireCell(int col, int row) {
        return mapOfTypes[col][row] == WALL || mapOfTypes[col][row] == BOX;
    }

    public static class Cell {

        public final int col;
        public final int row;

        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (!Cell.class.equals(obj)) {
                return false;
            }
            Cell cell = (Cell)obj;
            return col == cell.col && row == cell.row;
        }
    }

}
