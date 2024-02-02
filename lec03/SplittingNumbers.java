package lec03;

import java.io.*;

// Splitting Numbers UVa
public class SplittingNumbers {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, a, b;
	boolean odd;

	void solve() throws IOException {
		while ((n = rd.nextInt()) != 0) {

			a = b = 0;
			odd = true;

			for (int i = 0; i <= 30; i++)
				if ((n & (1 << i)) > 0) {
					if (odd)
						a |= 1 << i;
					else
						b |= 1 << i;
					odd = !odd;
				}

			pw.println(a + " " + b);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new SplittingNumbers().solve();
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