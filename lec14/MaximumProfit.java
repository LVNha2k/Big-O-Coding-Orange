package lec14;

import java.io.*;

// Maximum Profit GeeksforGeeks
public class MaximumProfit {
	MyReader rd = new MyReader();
	static final int MAX_N = 501, MAX_K = 201, INF = (int) 1e9;
	int T, N, K, A[] = new int[MAX_N];
	int[][] dp = new int[MAX_K][MAX_N];

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			K = rd.nextInt();
			N = rd.nextInt();

			for (int i = 1; i <= N; i++)
				A[i] = rd.nextInt();

			// buy at t, sell at j
			for (int i = 1; i <= K; i++)
				for (int j = 1; j <= N; j++) {
					int maxProfit = -INF;

					for (int t = 1; t < j; t++)
						maxProfit = Math.max(maxProfit, A[j] - A[t] + dp[i - 1][t]);

					dp[i][j] = Math.max(maxProfit, dp[i][j - 1]);
				}

			System.out.println(dp[K][N]);
		}
	}

	public static void main(String[] args) throws IOException {
		new MaximumProfit().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead;
		boolean isWindows;
		final String FILE = "inp.txt";

		public MyReader() {
			this(false);
		}

		public MyReader(boolean file) {
			try {
				bin = new BufferedInputStream(file ? new FileInputStream(FILE) : System.in, BUFFER_SIZE);
			} catch (IOException e) {
				e.printStackTrace();
			}
			buffer = new byte[BUFFER_SIZE];
			bufferPointer = bytesRead = 0;
			isWindows = System.getProperty("os.name").startsWith("Win");
		}

		public int nextInt() throws IOException {
			int ret = 0;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (isWindows && c == 13)
				read();
			return neg ? -ret : ret;
		}

		public boolean isEOF() {
			return buffer[0] == -1;
		}

		private byte read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}

		private void fillBuffer() throws IOException {
			bytesRead = bin.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}

		public void close() throws IOException {
			if (bin == null)
				return;
			bin.close();
		}
	}
}