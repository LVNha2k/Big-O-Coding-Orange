package lec04;

import java.io.*;

// Painting Fence Codeforces
public class PaintingFence {
	MyReader rd = new MyReader();
	int n, a[];

	void solve() throws IOException {
		a = new int[n = rd.nextInt()];
		for (int i = 0; i < n; i++)
			a[i] = rd.nextInt();

		System.out.print(paint(0, n - 1, 0));
	}

	// [L, R]
	int paint(int L, int R, int paintedHeight) {
		if (L > R)
			return 0;

		int mini = L;
		for (int i = L + 1; i <= R; i++)
			if (a[i] < a[mini])
				mini = i;

		int horizontal = a[mini] - paintedHeight;

		int sum = horizontal + paint(L, mini - 1, a[mini]) + paint(mini + 1, R, a[mini]);

		int allVertical = R - L + 1;

		return Math.min(sum, allVertical);
	}

	public static void main(String[] args) throws IOException {
		new PaintingFence().solve();
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