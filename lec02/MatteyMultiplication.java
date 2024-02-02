package lec02;

import java.io.*;
import java.util.*;

// Mattey Multiplication HackerEarth
public class MatteyMultiplication {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int T;
	long N, M;
	List<Integer> res = new ArrayList<>(64);

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			N = rd.nextLong();
			M = rd.nextLong();
			res.clear();

			for (int bitIndex = 0; M != 0; bitIndex++) {
				// If LSB == 1
				if ((M & 1L) == 1)
					res.add(bitIndex);
				M >>= 1;
			}

			pw.printf("(%d<<%d)", N, res.get(res.size() - 1));

			for (int i = res.size() - 2; i >= 0; i--)
				pw.printf(" + (%d<<%d)", N, res.get(i));

			pw.println();
		}
		pw.flush();
	}

	public static void main(String[] args) throws IOException {
		new MatteyMultiplication().solve();
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

		public long nextLong() throws IOException {
			long ret = 0;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10L + c - '0';
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