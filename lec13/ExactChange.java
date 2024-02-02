package lec13;

import java.io.*;
import java.util.*;

// Exact Change UVa
public class ExactChange {
	MyReader rd = new MyReader();
	static final int MAX = 2 * 10000, INF = (int) 1e9;
	int n, price;
	int[] coins = new int[100], dp = new int[MAX];

	void solve() throws IOException {
		int tc = rd.nextInt();
		while (tc-- > 0) {
			price = rd.nextInt();
			n = rd.nextInt();

			for (int i = 0; i < n; i++)
				coins[i] = rd.nextInt();

			Arrays.fill(dp, INF);
			dp[0] = 0;

			for (int i = 0; i < n; i++)
				for (int j = price; j >= 0; j--)
					if (dp[j] != INF)
						dp[j + coins[i]] = Math.min(dp[j + coins[i]], dp[j] + 1);

			for (int i = price; i < MAX; i++)
				if (dp[i] != INF) {
					System.out.printf("%d %d\n", i, dp[i]);
					break;
				}
		}
	}

	public static void main(String[] args) throws IOException {
		new ExactChange().solve();
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