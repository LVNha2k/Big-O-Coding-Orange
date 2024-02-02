package lec13;

import java.io.*;

// Pick The Sticks UVa
public class PickTheSticks {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 1001, MAX_L = 2001;
	int T, N, L;
	long ans;
	int[] a = new int[MAX_N], v = new int[MAX_N];
	long[][][] dp = new long[MAX_N][2 * MAX_L][3];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = rd.nextInt();
			L = rd.nextInt();
			L *= 2;

			ans = 0;
			for (int i = 1; i <= N; i++) {
				a[i] = rd.nextInt();
				a[i] *= 2;
				v[i] = rd.nextInt();
				ans = Math.max(ans, v[i]);
			}

			for (int i = 1; i <= N; i++)
				for (int j = 1; j <= L; j++)
					for (int cnt = 0; cnt <= 2; cnt++) {
						dp[i][j][cnt] = dp[i - 1][j][cnt];

						if (j >= a[i])
							dp[i][j][cnt] = Math.max(dp[i][j][cnt], v[i] + dp[i - 1][j - a[i]][cnt]);

						if (j >= a[i] / 2 && cnt > 0)
							dp[i][j][cnt] = Math.max(dp[i][j][cnt], v[i] + dp[i - 1][j - a[i] / 2][cnt - 1]);

						ans = Math.max(ans, dp[i][j][cnt]);
					}

			pw.printf("Case #%d: %d\n", tc, ans);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new PickTheSticks().solve();
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