package main.java.com.sudoku;

import main.java.com.sudoku.controller.SudokuController;
import main.java.com.sudoku.model.DifficultyLevel;

import java.util.Scanner;

public class Main {
    private static SudokuController controller;
    private static boolean gameInProgress = false;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMainMenu();
                switch (scanner.next()) {
                    case "1" -> startNewGame(scanner);
                    case "2" -> {
                        if (gameInProgress) {
                            System.out.println("\nBem-vindo de volta!");
                            printBoard(controller.getBoard());
                            playGame(scanner);
                        } else System.out.println("Nenhum jogo em andamento!");
                    }
                    case "0" -> {
                        System.out.println("Saindo... Até mais!");
                        return;
                    }
                    default -> System.out.println("Opção inválida!");
                }
            }
        }
    }

    private static void startNewGame(Scanner scanner) {
        if (gameInProgress && !confirmAction(scanner, "Há um jogo em andamento. Deseja reiniciar? (S/N): ")) return;

        System.out.println("Escolha a dificuldade: EASY, MEDIUM, HARD, DIABOLIC");
        try {
            controller = new SudokuController(DifficultyLevel.valueOf(scanner.next().toUpperCase()));
            gameInProgress = true;
            System.out.println("\nNovo jogo iniciado!");
            printBoard(controller.getBoard());
            playGame(scanner);
        } catch (IllegalArgumentException e) {
            System.out.println("Dificuldade inválida!");
        }
    }

    private static void playGame(Scanner scanner) {
        while (true) {
            printGameMenu();
            switch (scanner.next()) {
                case "1" -> insertNumber(scanner);
                case "2" -> clearNumber(scanner);
                case "3" -> printHighlightedBoard();
                case "4" -> {
                    if (confirmAction(scanner, "Deseja reiniciar o jogo? (S/N): ")) {
                        controller.resetGame();
                        System.out.println("Jogo reiniciado!");
                        printBoard(controller.getBoard());
                    }
                }
                case "5" -> {
                    if (finishGame()) return;
                }
                case "0" -> {
                    System.out.println("Voltando ao menu principal...");
                    return;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void insertNumber(Scanner scanner) {
        int row = getInput("Linha (0-8): ", scanner, 0, 8);
        int col = getInput("Coluna (0-8): ", scanner, 0, 8);
        int num = getInput("Número (1-9): ", scanner, 1, 9);

        if (controller.setCell(row, col, num)) {
            System.out.println("Número inserido com sucesso!");
        } else {
            System.out.println("Célula bloqueada ou número inválido!");
        }
        printBoard(controller.getBoard());
    }

    private static void clearNumber(Scanner scanner) {
        int row = getInput("Linha (0-8): ", scanner, 0, 8);
        int col = getInput("Coluna (0-8): ", scanner, 0, 8);

        if (controller.isCellEditable(row, col)) {
            controller.setCell(row, col, 0);
            System.out.println("Número removido!");
        } else {
            System.out.println("Célula bloqueada!");
        }
        printBoard(controller.getBoard());
    }

    private static void printHighlightedBoard() {
        String[][] board = controller.getHighlightedBoard();
        System.out.println("    0 1 2   3 4 5   6 7 8 ");
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0) System.out.println("  +-------+-------+-------+");
            System.out.print(r + " | ");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) System.out.print("| ");
                System.out.print(board[r][c] + " ");
            }
            System.out.println("|");
        }
        System.out.println("  +-------+-------+-------+");
    }

    private static boolean finishGame() {
        if (controller.isGameCompleted()) {
            System.out.println("Parabéns! Sudoku completo!");
            gameInProgress = false;
            return true;
        }
        System.out.println("O tabuleiro não está concluído. Corrija antes de finalizar!");
        return false;
    }

    private static int getInput(String message, Scanner scanner, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int input = scanner.nextInt();
                if (input >= min && input <= max) return input;
                System.out.println("Entrada fora do intervalo permitido!");
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número válido.");
                scanner.nextLine();
            }
        }
    }

    private static boolean confirmAction(Scanner scanner, String message) {
        System.out.print(message);
        return scanner.next().equalsIgnoreCase("S");
    }

    private static void printBoard(int[][] board) {
        System.out.println("    0 1 2   3 4 5   6 7 8 ");
        System.out.println("  +-------+-------+-------+");
        for (int r = 0; r < 9; r++) {
            if (r % 3 == 0 && r != 0) System.out.println("  +-------+-------+-------+");
            System.out.print(r + " | ");
            for (int c = 0; c < 9; c++) {
                if (c % 3 == 0 && c != 0) System.out.print("| ");
                System.out.print((board[r][c] == 0 ? "." : board[r][c]) + " ");
            }
            System.out.println("|");
        }
        System.out.println("  +-------+-------+-------+");
    }

    private static void printMainMenu() {
        System.out.println("\n=== MENU SUDOKU ===");
        System.out.println("1 - Novo Jogo");
        if (gameInProgress) System.out.println("2 - Continuar Jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void printGameMenu() {
        System.out.println("\n=== MENU JOGO ===");
        System.out.println("1 - Inserir Número");
        System.out.println("2 - Limpar Número");
        System.out.println("3 - Verificar Erros");
        System.out.println("4 - Reiniciar Jogo");
        System.out.println("5 - Finalizar Jogo");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
    }
}