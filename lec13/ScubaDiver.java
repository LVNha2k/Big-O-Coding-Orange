package lec13;

import java.io.*;
import java.util.*;

// Scuba diver SPOJ
public class ScubaDiver {
	MyReader rd = new MyReader();
	static final int MAX_N = 1001, INF = (int) 1e9;
	int c, t, a, n;
	int[] oxy = new int[MAX_N], nitro = new int[MAX_N], weight = new int[MAX_N];
	int[][][] dp = new int[MAX_N][22][80];

	void solve() throws IOException {
		c = rd.nextInt();
		while (c-- > 0) {
			t = rd.nextInt();
			a = rd.nextInt();
			n = rd.nextInt();

			for (int i = 1; i <= n; i++) {
				oxy[i] = rd.nextInt();
				nitro[i] = rd.nextInt();
				weight[i] = rd.nextInt();
			}
			for (int[][] mat : dp)
				for (int[] arr : mat)
					Arrays.fill(arr, -1);

			System.out.println(calc(n, t, a));
		}
	}

	int calc(int i, int oxyNeed, int nitroNeed) {
		if (dp[i][oxyNeed][nitroNeed] != -1)
			return dp[i][oxyNeed][nitroNeed];
		if (oxyNeed == 0 && nitroNeed == 0)
			return dp[i][oxyNeed][nitroNeed] = 0;
		if (i == 0)
			return dp[i][oxyNeed][nitroNeed] = INF;

		int dontTake = calc(i - 1, oxyNeed, nitroNeed);
		int take = weight[i] + calc(i - 1, Math.max(0, oxyNeed - oxy[i]), Math.max(0, nitroNeed - nitro[i]));
		dp[i][oxyNeed][nitroNeed] = Math.min(dontTake, take);

		return dp[i][oxyNeed][nitroNeed];
	}

	public static void main(String[] args) throws IOException {
		new ScubaDiver().solve();
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