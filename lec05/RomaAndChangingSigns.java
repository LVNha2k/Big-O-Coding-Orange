package lec05;

import java.io.*;

// Roma and Changing Signs Codeforces
public class RomaAndChangingSigns {
	MyReader rd = new MyReader();
	int n, k, ans;

	void solve() throws IOException {
		n = rd.nextInt();
		k = rd.nextInt();
		int income, min = Integer.MAX_VALUE;

		for (int i = 0; i < n; i++) {
			income = rd.nextInt();

			if (income < 0 && k-- > 0)
				income *= -1;

			ans += income;
			min = Math.min(min, income);
		}

		if (k % 2 == 1)
			ans -= (2 * min);

		System.out.print(ans);
	}

	public static void main(String[] args) throws IOException {
		new RomaAndChangingSigns().solve();
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