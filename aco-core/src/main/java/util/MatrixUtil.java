package util;

/**
 * Created by ab792 on 2017/1/3.
 */
public class MatrixUtil {
    private MatrixUtil() {
    }

    public static void printMatrix(double[][] matrix) {
        if (matrix != null) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[i].length; j++) {
                    System.out.print(matrix[i][j] + "\t" + "\t" + "\t" + "\t");
                }
                System.out.print("\n");
            }
        }
    }
}
