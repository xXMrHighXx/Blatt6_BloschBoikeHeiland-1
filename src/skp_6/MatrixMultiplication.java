package skp_6;

/**
 * 
 * @author Lukas Heiland
 *
 */

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
		
		SynchronousChannel channel1 = new SynchronousChannel();
		SynchronousChannel channel2 = new SynchronousChannel();
		
		SourceThread source = new MatrixMultiplication.SourceThread(channel2, matrixB[0][0]);
		IntermediateThread intermediate = new MatrixMultiplication.IntermediateThread(channel1, channel2, matrixA[0][0]);
		ResultConsumerThread result = new MatrixMultiplication.ResultConsumerThread(channel1);
		source.run();
		intermediate.run();
		result.run();
		
		try {
			source.join();
			intermediate.join();
			result.join();
		} catch (InterruptedException e) {
			System.out.println(ERROR_MESSAGE);
			e.printStackTrace();
		}
		

		return MatrixMultiplication.result;
	}
	
	//main method for testing
	public static void main(String[] args) {
		int[][] A = {{1}}, B = {{2}};
		
		System.out.println(MatrixMultiplication.multiplyMatrices(A, B));
	}
	
	/**
	 * private class which first receives then sends a value.
	 * 
	 * @author Lukas Heiland
	 *
	 */
	private static class IntermediateThread extends Thread {
		public SynchronousChannel ownChannel;
		private SynchronousChannel channelToReceiveFrom;
		private Integer value;
		
		
		
		public IntermediateThread(SynchronousChannel ownChannel, SynchronousChannel channelToReceiveFrom, Integer value) {
			super();
			this.ownChannel = ownChannel;
			this.channelToReceiveFrom = channelToReceiveFrom;
			this.value = value;
		}


		@Override
		public void run() {
			super.run();
			System.out.println(this.getName() + " of type " + this.getClass().getName() + " started");
			
			try {
				Integer received = channelToReceiveFrom.receive();
				ownChannel.send(received + value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("Execution of  Thread " + this.getName() + " finished");
			}
		}
	}
	
	
	
	/**
	 * Thread in which a channel only sends and then waits for a receive()-call.
	 * 
	 * @author Lukas Heiland
	 */
	private static class SourceThread extends Thread {
		public SynchronousChannel ownChannel;
		private Integer value;
		
		public SourceThread(SynchronousChannel ownChannel, Integer value) {
			super();
			this.ownChannel = ownChannel;
			this.value = value;
		}

		@Override
		public void run() {
			super.run();
			System.out.println(this.getName() + " of type " + this.getClass().getName() + " started");
			
			try {
				ownChannel.send(value);
			} catch (InterruptedException e) {
				System.out.println(ERROR_MESSAGE);
				e.printStackTrace();
			} finally {
				System.out.println("Execution of  Thread " + this.getName() + " finished");
			}
		}
	}
	
	
	
	/**
	 * Thread in which the channel only receives a value from a <code>ThreadA</code>
	 * 
	 * @author Lukas Heiland
	 */
	private static class ResultConsumerThread extends Thread {
		public SynchronousChannel channelToReceiveFrom;
		
		public ResultConsumerThread(SynchronousChannel channelToReceiveFrom){
			super();
			this.channelToReceiveFrom = channelToReceiveFrom;
		}
		
		@Override
		public void run() {
			super.run();
			System.out.println(this.getName() + " of type " + this.getClass().getName() + " started");
			
			try {
				Integer result = channelToReceiveFrom.receive();
				MatrixMultiplication.result[0][0] = result;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

	




	/* this is what the multiplication with loops would look like:
		* for (int i = 0; i < result.length; i++) {
		* 	for (int j = 0; j < result[i].length; j++) {
		* 		System.out.print(result[i][j] + " ");
		* 	}
		* 	System.out.println("\n");
		* }
	*/
