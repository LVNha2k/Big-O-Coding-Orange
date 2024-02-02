package lec13;

import java.io.*;
import java.util.*;

// Dividing coins UVa
public class DividingCoins {
	MyReader rd = new MyReader();
	int n, m, sum;
	int[] coins = new int[100];
	boolean[] dp = new boolean[50001];

	void solve() throws IOException {
		n = rd.nextInt();
		while (n-- > 0) {
			m = rd.nextInt();
			sum = 0;

			for (int i = 0; i < m; i++) {
				coins[i] = rd.nextInt();
				sum += coins[i];
			}

			Arrays.fill(dp, false);
			dp[0] = true;

			for (int i = 0; i < m; i++)
				for (int j = sum; j >= coins[i]; j--)
					dp[j] = dp[j] || dp[j - coins[i]];

			int diff = -1;
			for (int amount = sum / 2; amount >= 0; amount--)
				if (dp[amount]) {
					diff = Math.abs(sum - amount - amount);
					break;
				}

			System.out.println(diff);
		}
	}

	public static void main(String[] args) throws IOException {
		new DividingCoins().solve();
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