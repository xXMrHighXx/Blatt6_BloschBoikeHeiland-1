package skp_6;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int[][] matrixA = { { 1, 2, 3 }, 
                            { 4, 5, 6 }, 
                            { 7, 8, 9 } };

        int[][] matrixB = { { 1, 0, 2 }, 
                            { 0, 1, 2 }, 
                            { 1, 0, 0 } };

        int[][] matrixAxB = MatrixMultiplication.multiplyMatrices(matrixA, matrixB);

        printMatrices(matrixA, matrixB, matrixAxB);
    }

    public static void printMatrices(int[][] matrixA, int[][] matrixB, int[][] matrixAxB) {
        int dim = matrixA[0].length;
        String[][] dump = new String[dim][dim * 3 + 2];
        for (int row = 0; row < dim; row++) {
            dump[row][dim] = "";
            dump[row][2*dim+1] = "";
            for (int col = 0; col < dim; col++) {
                dump[row][col] = String.valueOf(matrixA[row][col]);
                dump[row][col + dim + 1] = String.valueOf(matrixB[row][col]);
                dump[row][col + 2*dim + 2] = String.valueOf(matrixAxB[row][col]);
            }
        }
        dump[dim / 2][dim] = " + ";
        dump[dim / 2][2*dim+1] = " = ";

        int maxColumns = 0;
        for (int row = 0; row < dump.length; row++) {
            maxColumns = Math.max(dump[row].length, maxColumns);
        }
        int[] lengths = new int[maxColumns];
        for (int row = 0; row < dump.length; row++) {
            for (int col = 0; col < dump[row].length; col++) {
                lengths[col] = Math.max(dump[row][col].length(), lengths[col]);
            }
        }
        String[] formats = new String[lengths.length];
        for (int row = 0; row < lengths.length; row++) {
            formats[row] = "%1$" + lengths[row] + "s" + (row + 1 == lengths.length ? "\n" : " ");
        }
        for (int row = 0; row < dump.length; row++) {
            for (int col = 0; col < dump[row].length; col++) {
                System.out.printf(formats[col], dump[row][col]);
            }
        }
    }

}
