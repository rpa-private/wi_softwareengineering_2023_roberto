package ch.fhnw.richards.Week_01.solution;

import java.util.Arrays;

/**
 * SBB Intercity Graph as an adjacency matrix. To declare the matrix, we must know
 * the size in advance. In the given example, there are 18 cities. This solution keeps
 * it simple, and just say whether we can travel directly from one city to another.
 * <p>
 * If you wanted more complex information, you could declare a matrix of objects.
 * Each object would then contain detailed information about the connection (edge)
 * between the nodes.
 */
public class SBBmatrix {
    private static String[] cityNames = {"Geneva", "Lausanne", "Yverdon", "Biel/Bienne",
            "Basel", "Olten", "Bern", "Spiez", "Interlaken", "Brig", "Luzern", "Schaffhausen",
            "Zürich", "Arth-Goldau", "Lugano", "Chur", "St. Gallen", "Romanshorn"};

    private static int[][] matrix = new int[18][18]; // Initial values are 0

    public static void main(String[] args) {
        initData(); // Initialize the matrix
//        printMatrix(matrix);
//        System.out.println();

        int[][] pathsLengthTwo = matrixMultiply(matrix, matrix);
//        printMatrix(pathsLengthTwo);
//        System.out.println();

        // Which cities can we reach from Zürich, with a path of *exactly* length 2?
        // Note: Zürich is city number 12
        System.out.println("Routes from Zürich with *exactly* one stop");
        for (int i = 0; i < pathsLengthTwo.length; i++) {
            if (pathsLengthTwo[12][i] > 0) System.out.println(cityNames[i]);
        }
        System.out.println();

        int[][] pathsLengthOneOrTwo = matrixAdd(matrix, pathsLengthTwo);

        // Which cities can we reach from Zürich, with a path of *at most* length 2?
        // Note: Zürich is city number 12
        System.out.println("Routes from Zürich with *at* *most* one stop");
        for (int i = 0; i < pathsLengthOneOrTwo.length; i++) {
            if (pathsLengthOneOrTwo[12][i] > 0) System.out.println(cityNames[i]);
        }
        System.out.println();
    }

    /**
     * Set matrix values to 1, where we have a direct connection between cities.
     * Since all connections go in both directions, they are all entered twice.
     */
    private static void initData() {
        matrix[0][1] = 1; matrix[1][0] = 1; // Geneva-Lausanne
        matrix[0][2] = 1; matrix[2][0] = 1; // Geneva-Yverdon
        matrix[1][2] = 1; matrix[2][1] = 1; // Lausanne-Yverdon
        matrix[2][3] = 1; matrix[3][2] = 1; // Yverdon-Biel/Bienne
        matrix[3][4] = 1; matrix[4][3] = 1; // Biel/Bienne-Basel
        matrix[3][5] = 1; matrix[5][3] = 1; // Biel/Bienne-Olten
        matrix[4][5] = 1; matrix[5][4] = 1; // Basel-Olten
        matrix[1][6] = 1; matrix[6][1] = 1; // Lausanne-Bern
        matrix[5][6] = 1; matrix[6][5] = 1; // Olten-Bern
        matrix[6][7] = 1; matrix[7][6] = 1; // Bern-Spiez
        matrix[7][8] = 1; matrix[8][7] = 1; // Spiez-Interlaken
        matrix[7][9] = 1; matrix[9][7] = 1; // Spiez-Brig
        matrix[5][10] = 1; matrix[10][5] = 1; // Olten-Luzern
        matrix[5][12] = 1; matrix[12][5] = 1; // Olten-Zürich
        matrix[4][12] = 1; matrix[12][4] = 1; // Basel-Zürich
        matrix[10][13] = 1; matrix[13][10] = 1; // Luzern-Arth-Goldau
        matrix[12][11] = 1; matrix[11][12] = 1; // Zürich-Schaffhausen
        matrix[12][13] = 1; matrix[13][12] = 1; // Zürich-Arth-Goldau
        matrix[12][17] = 1; matrix[17][12] = 1; // Zürich-Romanshorn
        matrix[12][15] = 1; matrix[15][12] = 1; // Zürich-Chur
        matrix[12][16] = 1; matrix[16][12] = 1; // Zürich-St. Gallen
        matrix[13][14] = 1; matrix[14][13] = 1; // Arth-Goldau-Lugano
    }

    /**
     * Add two matrices together: a + b
     */
    private static int[][] matrixAdd(int[][] a, int[][] b) {
        int[][] result = new int[a.length][a[0].length];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[0].length; col++) {
                result[row][col] = a[row][col] + b[row][col];
            }
        }
        return result;
    }

    /**
     * Multiply two matrices together: a x b
     */
    private static int[][] matrixMultiply(int[][] a, int[][] b) {
        int[][] result = new int[a.length][b[0].length];
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[0].length; col++) {
                result[row][col] = 0;
                for (int i = 0; i < b.length; i++) {
                    result[row][col] += a[row][i] * b[i][col];
                }
            }
        }
        return result;
    }

    private static void printMatrix(int[][] matrix) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                System.out.print(matrix[row][col] + " ");
            }
            System.out.println();
        }
    }
}
