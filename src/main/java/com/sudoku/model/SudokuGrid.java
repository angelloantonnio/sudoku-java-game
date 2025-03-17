package main.java.com.sudoku.model;

public class SudokuGrid {
    private static final int SIZE = 9;
    private final int[][] board = new int[SIZE][SIZE];
    private final boolean[][] isEditable = new boolean[SIZE][SIZE];

    public int[][] getBoard() {
        return board;
    }

    public boolean isCellEditable(int row, int col) {
        return isWithinBounds(row, col) && isEditable[row][col];
    }

    public void setCell(int row, int col, int num) {
        if (isCellEditable(row, col) && isNumberValid(num)) {
            board[row][col] = num;
        }
    }

    public void setEditable(int row, int col, boolean editable) {
        if (isWithinBounds(row, col)) {
            isEditable[row][col] = editable;
        }
    }

    public boolean isNumberValid(int num) {
        return num >= 1 && num <= 9;
    }

    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    public void resetEditableCells() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (isEditable[r][c]) board[r][c] = 0;
            }
        }
    }
}