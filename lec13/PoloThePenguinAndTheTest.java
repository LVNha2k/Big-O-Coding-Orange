package lec13;

import java.io.*;

// Polo the Penguin and the Test CodeChef
public class PoloThePenguinAndTheTest {
	MyReader rd = new MyReader();
	static final int MAX = 101;
	int t, N, W;
	int[] C = new int[MAX], P = new int[MAX], T = new int[MAX];
	int[][] dp = new int[MAX][MAX];

	void solve() throws IOException {
		t = rd.nextInt();
		while (t-- > 0) {
			N = rd.nextInt();
			W = rd.nextInt();

			for (int i = 1; i <= N; i++) {
				C[i] = rd.nextInt();
				P[i] = rd.nextInt();
				T[i] = rd.nextInt();
			}

			System.out.println(knapsack());
		}
	}

	int knapsack() {
		for (int i = 1; i <= N; i++)
			for (int j = 0; j <= W; j++)
				if (T[i] > j)
					dp[i][j] = dp[i - 1][j];
				else {
					int p1 = dp[i - 1][j];
					int p2 = C[i] * P[i] + dp[i - 1][j - T[i]];
					dp[i][j] = Math.max(p1, p2);
				}

		return dp[N][W];
	}

	public static void main(String[] args) throws IOException {
		new PoloThePenguinAndTheTest().solve();
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