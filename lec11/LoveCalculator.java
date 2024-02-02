package lec11;

import java.io.*;
import java.util.*;

// Love Calculator LightOJ
public class LoveCalculator {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 31;
	int T, n, m;
	char[] s1, s2;
	int[][] dp = new int[MAX][MAX]; // shortest cover string
	long[][] numWays = new long[MAX][MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			n = (s1 = rd.next().toCharArray()).length;
			m = (s2 = rd.next().toCharArray()).length;

			for (long[] arr : numWays)
				Arrays.fill(arr, 0);

			for (int i = 0; i <= n; i++)
				for (int j = 0; j <= m; j++)
					if (i == 0 || j == 0) {
						dp[i][j] = i + j;
						numWays[i][j] = 1;
					}
					else if (s1[i - 1] == s2[j - 1]) {
						dp[i][j] = 1 + dp[i - 1][j - 1];
						numWays[i][j] = numWays[i - 1][j - 1];
					}
					else {
						dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);

						if (dp[i][j] == dp[i - 1][j] + 1)
							numWays[i][j] += numWays[i - 1][j];

						if (dp[i][j] == dp[i][j - 1] + 1)
							numWays[i][j] += numWays[i][j - 1];
					}

			pw.printf("Case %d: %d %d\n", tc, dp[n][m], numWays[n][m]);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new LoveCalculator().solve();
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

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
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