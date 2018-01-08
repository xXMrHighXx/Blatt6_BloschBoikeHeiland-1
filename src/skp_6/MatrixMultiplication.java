package skp_6;

public class MatrixMultiplication {

	public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
		// TODO: Multiply Matrices matrixA and matrixB and return result
		
		if (matrixA.length != matrixB[0].length) {
			throw new IllegalArgumentException("The format of the matrices doesn't allow multiplication!");
		}
		
		// Ergebnismatrix hat soviele Zeilen wie A und so viele Spalten wie B
		int[][] result = new int[matrixA.length][matrixB[0].length];

		for (int i = 0; i < matrixA.length; i++) {
			for (int j = 0; j < matrixB[i].length; j++) {
				for (int k = 0; k < matrixA[i].length; k++) {
					result[i][j] += matrixA[i][k] * matrixB[k][j];
				}
			}
		}
		
//		for (int i = 0; i < result.length; i++) {
//			for (int j = 0; j < result[i].length; j++) {
//				System.out.print(result[i][j] + " ");
//			}
//			System.out.println("\n");
//		}
		
		return result;
	}

	public static void main(String[] args) {		
		int[][] matrixA = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[][] matrixB = { { 1, 0, 2 }, { 0, 1, 2 }, { 1, 0, 0 } };
		
		MatrixMultiplication.multiplyMatrices(matrixA, matrixB);
	}
}
