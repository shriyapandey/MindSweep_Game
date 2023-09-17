import java.util.Random;
import java.util.Scanner;

public class minesweeper {
    private static final int SIZE = 10; // Size of the grid (adjust as needed)
    private static final int MINES = 15; // Number of mines (adjust as needed)
    private static char[][] grid = new char[SIZE][SIZE];
    private static boolean[][] revealed = new boolean[SIZE][SIZE];

    public static void main(String[] args) {
        initializeGrid();
        placeMines();
        calculateHints();

        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            displayGrid();

            System.out.print("Enter row and column (e.g., 1 2): ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
                System.out.println("Invalid input. Try again.");
                continue;
            }

            if (grid[row][col] == '*') {
                gameOver = true;
                System.out.println("Game Over! You hit a mine!");
            } else {
                revealed[row][col] = true;
                if (checkWin()) {
                    gameOver = true;
                    System.out.println("Congratulations! You won!");
                }
            }
        }

        scanner.close();
    }

    private static void initializeGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = ' ';
                revealed[i][j] = false;
            }
        }
    }

    private static void placeMines() {
        Random rand = new Random();
        int minesPlaced = 0;

        while (minesPlaced < MINES) {
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);

            if (grid[row][col] != '*') {
                grid[row][col] = '*';
                minesPlaced++;
            }
        }
    }

    private static void calculateHints() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (grid[i][j] != '*') {
                    int count = 0;

                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            if (i + x >= 0 && i + x < SIZE && j + y >= 0 && j + y < SIZE && grid[i + x][j + y] == '*') {
                                count++;
                            }
                        }
                    }

                    grid[i][j] = (char) (count + '0');
                }
            }
        }
    }

    private static void displayGrid() {
        System.out.println("  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) {
                if (revealed[i][j]) {
                    System.out.print(grid[i][j] + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    private static boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!revealed[i][j] && grid[i][j] != '*') {
                    return false;
                }
            }
        }
        return true;
    }
}
