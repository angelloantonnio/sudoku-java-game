package main.java.com.sudoku.controller;

import main.java.com.sudoku.model.*;

import java.util.List;

public class SudokuController {
    private final SudokuGrid grid;
    private final SudokuValidator validator;

    public SudokuController(DifficultyLevel difficultyLevel) {
        grid = new SudokuGrid();
        SudokuGenerator generator = new SudokuGenerator(grid);
        generator.generateFullBoard(difficultyLevel);
        validator = new SudokuValidator(generator.getSolutionBoard());
    }

    public int[][] getBoard() {
        return grid.getBoard();
    }

    public boolean isCellEditable(int row, int col) {
        return grid.isCellEditable(row, col);
    }

    public boolean setCell(int row, int col, int num) {
        if (isCellEditable(row, col) && grid.isNumberValid(num)) {
            grid.setCell(row, col, num);
            return true;
        }
        return false;
    }

    public boolean isGameCompleted() {
        return validator.isValidSolution(grid.getBoard());
    }

    public List<int[]> getIncorrectCells() {
        return validator.getIncorrectCells(grid.getBoard());
    }

    public void resetGame() {
        grid.resetEditableCells();
    }

    public String[][] getHighlightedBoard() {
        int[][] board = grid.getBoard();
        List<int[]> incorrectCells = getIncorrectCells();
        String[][] highlightedBoard = new String[9][9];

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == 0) {
                    highlightedBoard[r][c] = ".";
                } else if (isCellEditable(r, c)) {
                    int finalR = r;
                    int finalC = c;
                    boolean isIncorrect = incorrectCells.stream().anyMatch(cell -> cell[0] == finalR && cell[1] == finalC);
                    highlightedBoard[r][c] = isIncorrect ? "\u001B[31m" + board[r][c] + "\u001B[0m"
                            : "\u001B[32m" + board[r][c] + "\u001B[0m";
                } else {
                    highlightedBoard[r][c] = String.valueOf(board[r][c]);
                }
            }
        }
        return highlightedBoard;
    }
}
