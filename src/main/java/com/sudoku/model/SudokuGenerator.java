package main.java.com.sudoku.model;

import java.util.*;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private final SudokuGrid grid;
    private final int[][] solutionBoard;

    public SudokuGenerator(SudokuGrid grid) {
        this.grid = grid;
        this.solutionBoard = new int[SIZE][SIZE];
    }

    public int[][] getSolutionBoard() {
        return solutionBoard;
    }

    public void generateFullBoard(DifficultyLevel difficultyLevel) {
        if (!solveBoard(0, 0)) {
            throw new IllegalStateException("Falha ao gerar uma solução válida para o Sudoku.");
        }
        copyBoard(grid.getBoard(), solutionBoard);
        maskNumbers(difficultyLevel);
    }

    private boolean solveBoard(int row, int col) {
        if (row == SIZE) return true;

        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;

        for (int num : getShuffledNumbers()) {
            if (isValidNumber(row, col, num)) {
                grid.getBoard()[row][col] = num;
                if (solveBoard(nextRow, nextCol)) return true;
                grid.getBoard()[row][col] = 0;
            }
        }
        return false;
    }

    private void maskNumbers(DifficultyLevel difficultyLevel) {
        int min = difficultyLevel.getMinRemove();
        int max = difficultyLevel.getMaxRemove();
        int numbersToRemove = Math.min(new Random().nextInt((max - min) + 1) + min, SIZE * SIZE);

        List<int[]> positions = generateRandomPositions();
        for (int i = 0; i < numbersToRemove; i++) {
            int[] pos = positions.get(i);
            grid.getBoard()[pos[0]][pos[1]] = 0;
            grid.setEditable(pos[0], pos[1], true);
        }
    }

    private List<int[]> generateRandomPositions() {
        List<int[]> positions = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                positions.add(new int[]{r, c});
            }
        }
        Collections.shuffle(positions);
        return positions;
    }

    private boolean isValidNumber(int row, int col, int num) {
        int[][] board = grid.getBoard();

        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) return false;
        }

        int subGridRow = (row / 3) * 3;
        int subGridCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[subGridRow + i][subGridCol + j] == num) return false;
            }
        }
        return true;
    }

    private List<Integer> getShuffledNumbers() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
        Collections.shuffle(numbers);
        return numbers;
    }

    private void copyBoard(int[][] source, int[][] destination) {
        for (int r = 0; r < SIZE; r++) {
            System.arraycopy(source[r], 0, destination[r], 0, SIZE);
        }
    }
}

