package main.java.com.sudoku.model;

import java.util.ArrayList;
import java.util.List;

public class SudokuValidator {
    private static final int SIZE = 9;
    private final int[][] solutionBoard;

    public SudokuValidator(int[][] solutionBoard) {
        this.solutionBoard = deepCopy(solutionBoard);
    }

    public boolean isValidSolution(int[][] playerBoard) {
        if (!isValidBoard(playerBoard)) return false;
        return getIncorrectCells(playerBoard).isEmpty();
    }

    public List<int[]> getIncorrectCells(int[][] playerBoard) {
        List<int[]> incorrectCells = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (playerBoard[r][c] != solutionBoard[r][c]) {
                    incorrectCells.add(new int[]{r, c});
                }
            }
        }
        return incorrectCells;
    }

    private boolean isValidBoard(int[][] board) {
        return board != null && board.length == SIZE && board[0].length == SIZE;
    }

    private int[][] deepCopy(int[][] original) {
        int[][] copy = new int[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            System.arraycopy(original[r], 0, copy[r], 0, SIZE);
        }
        return copy;
    }
}
