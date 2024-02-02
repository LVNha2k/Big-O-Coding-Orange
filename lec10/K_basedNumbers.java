package lec10;

import java.io.*;

// K-based Numbers Timus Online Judge
public class K_basedNumbers {
	MyReader rd = new MyReader();
	int N, K;

	void solve() throws IOException {
		N = rd.nextInt();
		K = rd.nextInt();
		int[] endWith0 = new int[N + 1];
		int[] endNot0 = new int[N + 1];

		endNot0[1] = K - 1;
		for (int i = 2; i <= N; i++) {
			endWith0[i] = endNot0[i - 1];
			endNot0[i] = (endWith0[i - 1] + endNot0[i - 1]) * (K - 1);
		}
		
		System.out.print(endWith0[N] + endNot0[N]);
	}

	public static void main(String[] args) throws IOException {
		new K_basedNumbers().solve();
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