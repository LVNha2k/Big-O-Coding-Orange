package lec02;

import java.io.*;

// Aish And XOR HackerEarth
public class AishAndXOR {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, q, arr[], prefixCountNum1[];

	void solve() throws IOException {
		N = rd.nextInt();
		arr = new int[N + 1];
		prefixCountNum1 = new int[N + 1];

		for (int i = 1; i <= N; i++)
			arr[i] = rd.nextInt();

		for (int i = 1; i <= N; i++)
			prefixCountNum1[i] = prefixCountNum1[i - 1] + arr[i];

		q = rd.nextInt();
		for (int L, R, countNum1, countNum0; q-- > 0;) {
			L = rd.nextInt();
			R = rd.nextInt();

			countNum1 = prefixCountNum1[R] - prefixCountNum1[L - 1];
			countNum0 = (R - L + 1) - countNum1;

			pw.println(countNum1 % 2 + " " + countNum0);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new AishAndXOR().solve();
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