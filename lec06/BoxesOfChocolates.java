package lec06;

import java.io.*;

// Boxes of Chocolates UVa
public class BoxesOfChocolates {
	MyReader rd = new MyReader();
	int T, N, B, K;

	void solve() throws IOException {
		T = rd.nextInt();
		for (int cnt, sum; T-- > 0;) {
			N = rd.nextInt();
			B = rd.nextInt();
			sum = 0;

			while (B-- > 0) {
				cnt = 1;
				for (K = rd.nextInt(); K-- > 0;)
					cnt = (cnt * rd.nextInt()) % N;
				sum = (sum + cnt) % N;
			}

			System.out.println(sum);
		}
	}

	public static void main(String[] args) throws IOException {
		new BoxesOfChocolates().solve();
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