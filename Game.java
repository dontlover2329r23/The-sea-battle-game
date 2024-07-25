package Sea_battle;

import java.util.Random;
import java.util.Scanner;

public class Game {
    private static final int GRID_SIZE = 7;
    private static final int SHIP_SIZE = 3;
    private static final int SHIP_COUNT = 3;

    public static void main(String[] args) {
        new Game().start();
    }

    void start() {
        int[][] grid = initializeGrid();
        placeShips(grid);
        playGame(grid);
    }

    int[][] initializeGrid() {
        int[][] grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = 0;
            }
        }
        return grid;
    }

    void placeShips(int[][] grid) {
        Random random = new Random();
        int shipsPlaced = 0;

        while (shipsPlaced < SHIP_COUNT) {
            boolean vertical = random.nextBoolean();
            int x = random.nextInt(GRID_SIZE);
            int y = random.nextInt(GRID_SIZE);

            if (canPlaceShip(grid, x, y, vertical)) {
                placeShip(grid, x, y, vertical);
                shipsPlaced++;
            }
        }
    }

    boolean canPlaceShip(int[][] grid, int x, int y, boolean vertical) {
        for (int i = 0; i < SHIP_SIZE; i++) {
            int xi = vertical ? x : x + i;
            int yi = vertical ? y + i : y;

            if (xi >= GRID_SIZE || yi >= GRID_SIZE || grid[xi][yi] != 0 || !isValidCell(grid, xi, yi)) {
                return false;
            }
        }
        return true;
    }

    boolean isValidCell(int[][] grid, int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int xi = x + i;
                int yi = y + j;
                if (xi >= 0 && yi >= 0 && xi < GRID_SIZE && yi < GRID_SIZE && grid[xi][yi] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    void placeShip(int[][] grid, int x, int y, boolean vertical) {
        for (int i = 0; i < SHIP_SIZE; i++) {
            int xi = vertical ? x : x + i;
            int yi = vertical ? y + i : y;
            grid[xi][yi] = 1;
        }
    }

    void playGame(int[][] grid) {
        Scanner scanner = new Scanner(System.in);
        int score = 0;
        int points = 49;
        int hits = 0;

        System.out.println("Let's start the game");
        //printGrid(grid);    For testing game

        while (hits < SHIP_COUNT * SHIP_SIZE) {
            int x = -1, y = -1;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Please enter 2 values between 0 and 6 (x y): ");
                if (scanner.hasNextInt()) {
                    x = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        y = scanner.nextInt();
                        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
                            validInput = true;
                        } else {
                            System.out.println("Values must be between 0 and 6. Try again.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter integers.");
                        scanner.next();  // Clear invalid input
                    }
                } else {
                    System.out.println("Invalid input. Please enter integers.");
                    scanner.next();  // Clear invalid input
                }
            }

            if (grid[x][y] == 1) {
                score += points;
                grid[x][y] = -1;
                hits++;
                if (hits % 3 == 0) {
                    System.out.println("Kill " + hits / 3 + " Ship!!!");
                } else {
                    System.out.println("Hit!");
                }
            } else if (grid[x][y] == 0) {
                grid[x][y] = -1;
                System.out.println("Miss!");
            } else {
                System.out.println("You've shot at this place before");
            }
            points--;
        }
        System.out.println("Game over! Your score: " + score);
    }

    void printGrid(int[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
