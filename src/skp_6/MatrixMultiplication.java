package skp_6;

public class MatrixMultiplication {

	private static final String WRONG_FORMAT = "The format of the matrices doesn't allow multiplication!";
	private static final String ERROR_MESSAGE = "Something went wrong...";
	static int[][] result;

	public static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
		// TODO: Multiply Matrices matrixA and matrixB and return result

		if (matrixA.length != matrixB[0].length) {
			throw new IllegalArgumentException(WRONG_FORMAT);
		}

		// Ergebnismatrix hat soviele Zeilen wie A und so viele Spalten wie B
		result = new int[matrixA.length][matrixB[0].length];
		int[] matrixBColumn1 = { matrixB[0][0], matrixB[1][0], matrixB[2][0] };
		int[] matrixBColumn2 = { matrixB[0][1], matrixB[1][1], matrixB[2][1] };
		int[] matrixBColumn3 = { matrixB[0][2], matrixB[1][2], matrixB[2][2] };

		for (int i = 0; i < matrixA.length; i++) {
			SynchronousChannel channel = actualCalculationProcess(matrixA[i],i, matrixBColumn1, matrixBColumn2,
					matrixBColumn3);
//			int j = 0;
//			try {
//				while (channel.receive() != null) {
//					try {
//						result[i][j] = channel.receive();
//					} catch (InterruptedException e) {
//						System.out.println(ERROR_MESSAGE);
//						e.printStackTrace();
//					}
//					j++;
//				}
//			} catch (InterruptedException e) {
//				System.out.println(ERROR_MESSAGE);
//				e.printStackTrace();
//			}
		}

		// this is what the multiplication with loops would look like:
		// for (int i = 0; i < result.length; i++) {
		// for (int j = 0; j < result[i].length; j++) {
		// System.out.print(result[i][j] + " ");
		// }
		// System.out.println("\n");
		// }

		return MatrixMultiplication.result;
	}

	private static SynchronousChannel actualCalculationProcess(int[] matrixARow, int numberOfRow, int[]... matrixBColumns) {
		SynchronousChannel channel = new SynchronousChannel();
		Thread[] threads = new Thread[matrixBColumns.length];

		for (int column = 0; column < matrixBColumns.length; column++) {
			final int column1 = column;

			/*
			 * Create a thread which multiplies one row of matrix A with the
			 * columns of matrix B.
			 */
			threads[column] = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
							channel.send(multiply(matrixARow, matrixBColumns[column1]));
							result[numberOfRow][column1] = channel.receive();
						
					} catch (InterruptedException e) {
						System.out.println(ERROR_MESSAGE);
						e.printStackTrace();
					}
				}

				/**
				 * Multiplies one row of a <code>matrix A</code> with one
				 * <code>column</code> of a matrix B, thus producing one entry
				 * of the resulting matrix (<code>result</code>).
				 * 
				 * @param matrixARow
				 * @param column
				 * @return result
				 */
				private Integer multiply(int[] matrixARow, int[] column) {
					int result = 0;
					for (int i = 0; i < matrixARow.length; i++) {
						result += matrixARow[i] * column[i];
					}
					return result;
				}
			});

			// run the Thread
			threads[column].run();
		}

		// close the calculation threads
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return channel;
	}

}
