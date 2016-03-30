package sample.flamegraph.tools;

import java.util.concurrent.ThreadLocalRandom;

public class LLCMissesTest {
    public static final int DEFAULT_MATRIX_SIZE = 1024 *2;
  
    protected long[][] matrix;
  
    public LLCMissesTest(int n) {
        matrix = new long[n][n];
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < n; i = i + 1) {
            for (int j = 0; j < n; j = j + 1) {
                matrix[i][j] = random.nextInt();
            }
        }
    }
  
    public double calculateSum(boolean traverseByRows) {
        double sum = (double) 0;
  
        int n = matrix.length;
        for (int i = 0; i < n; i = i + 1) {
            for (int j = 0; j < n; j = j + 1) {
                if (traverseByRows == true) {
                    sum = sum + matrix[i][j];
                } else {
                    sum = sum + matrix[j][i];
                }
            }
        }
  
        return sum;
    }
 
    public static void main(String[] args) {
        final int NUM_ITERATIONS = 50;
 
        LLCMissesTest lmt = new LLCMissesTest(DEFAULT_MATRIX_SIZE);
        boolean traverseByRows = args[0].equals("1");
        for (int i = 0; i < NUM_ITERATIONS; i = i + 1) {
                System.out.printf("i = %d, traverseByRows = %b: total = %f\n", i, traverseByRows, lmt.calculateSum(traverseByRows));
        }
    }
}