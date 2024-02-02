package lec03;

import java.io.*;

// The Hamming Distance Problem UVa
public class TheHammingDistanceProblem {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, H;
	char[] res;

	void solve() throws IOException {
		int tc = rd.nextInt();
		while (tc-- > 0) {

			res = new char[N = rd.nextInt()];
			H = rd.nextInt();

			backtrack(0, 0);

			if (tc > 0)
				pw.println();
		}
		pw.close();
	}

	void backtrack(int selected, int countBit1) {
		if (countBit1 > H || N - selected + countBit1 < H)
			return;

		if (selected == N) {
			pw.println(res);
			return;
		}

		res[selected] = '0';
		backtrack(selected + 1, countBit1);

		res[selected] = '1';
		backtrack(selected + 1, countBit1 + 1);
	}

	public static void main(String[] args) throws IOException {
		new TheHammingDistanceProblem().solve();
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