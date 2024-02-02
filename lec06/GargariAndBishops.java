package lec06;

import java.io.*;

// Gargari and Bishops Codeforces
public class GargariAndBishops {
	MyReader rd = new MyReader();
	int n, a[][], x[] = new int[2], y[] = new int[2];
	long[] diag1, diag2, res = new long[2];

	void solve() throws IOException {
		n = rd.nextInt();
		a = new int[n + 1][n + 1];
		diag1 = new long[2 * n + 1];
		diag2 = new long[2 * n + 1];

		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				a[i][j] = rd.nextInt();
				diag1[i - j + n] += a[i][j];
				diag2[i + j] += a[i][j];
			}

		res[0] = res[1] = -1;

		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= n; j++) {
				int pos = (i + j) & 1;
				long sum = diag1[i - j + n] + diag2[i + j] - a[i][j];

				if (sum > res[pos]) {
					res[pos] = sum;
					x[pos] = i;
					y[pos] = j;
				}
			}

		System.out.println(res[0] + res[1]);
		System.out.printf("%d %d %d %d", x[0], y[0], x[1], y[1]);
	}

	public static void main(String[] args) throws IOException {
		new GargariAndBishops().solve();
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