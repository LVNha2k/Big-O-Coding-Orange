package lec10;

import java.io.*;

// Philosophers Stone SPOJ
public class PhilosophersStone {
	MyReader rd = new MyReader();
	static final int MAX = 100;
	int T, h, w;
	int[][] floor = new int[MAX][MAX], dp = new int[MAX][MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			h = rd.nextInt();
			w = rd.nextInt();

			for (int r = 0; r < h; r++)
				for (int c = 0; c < w; c++)
					floor[r][c] = rd.nextInt();

			for (int c = 0; c < w; c++)
				dp[0][c] = floor[0][c];

			int above, left, right;

			for (int r = 1; r < h; r++)
				for (int c = 0; c < w; c++) {

					above = dp[r - 1][c];
					left = (c > 0) ? dp[r - 1][c - 1] : -1;
					right = (c < w - 1) ? dp[r - 1][c + 1] : -1;

					dp[r][c] = floor[r][c] + Math.max(above, Math.max(left, right));
				}

			int ans = 0;
			for (int c = 0; c < w; c++)
				ans = Math.max(ans, dp[h - 1][c]);

			System.out.println(ans);
		}
	}

	public static void main(String[] args) throws IOException {
		new PhilosophersStone().solve();
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