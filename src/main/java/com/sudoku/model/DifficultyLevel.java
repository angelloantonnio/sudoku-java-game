package main.java.com.sudoku.model;

public enum DifficultyLevel {
    EASY(41, 46),
    MEDIUM(47, 54),
    HARD(55, 61),
    DIABOLIC(61, 68);

    private final int minRemove;
    private final int maxRemove;

    DifficultyLevel(int minRemove, int maxRemove) {
        this.minRemove = minRemove;
        this.maxRemove = maxRemove;
    }

    public int getMinRemove() {
        return minRemove;
    }

    public int getMaxRemove() {
        return maxRemove;
    }
}
