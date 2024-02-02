package lec15;

import java.io.*;
import java.util.*;

// Blueberries SPOJ
public class Blueberries {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 1001;
	int T, N, K, bush[] = new int[MAX];
	int[][] dp = new int[MAX][MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = rd.nextInt();
			K = rd.nextInt();
			for (int i = 1; i <= N; i++)
				bush[i] = rd.nextInt();

			for (int[] arr : dp)
				Arrays.fill(arr, -1);

			pw.printf("Scenario #%d: %d\n", tc, knapsack(1, 0));
		}
		pw.close();
	}

	int knapsack(int i, int w) {
		if (i > N)
			return 0;
		if (dp[i][w] != -1)
			return dp[i][w];

		int dontTake = knapsack(i + 1, w);
		int take = (w + bush[i] > K) ? 0 : bush[i] + knapsack(i + 2, w + bush[i]);

		return dp[i][w] = Math.max(dontTake, take);
	}

	public static void main(String[] args) throws IOException {
		new Blueberries().solve();
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