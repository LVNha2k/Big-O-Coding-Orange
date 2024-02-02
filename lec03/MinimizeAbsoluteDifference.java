package lec03;

import java.io.*;

// Minimize Absolute Difference Topcoder
// Min | (?/?) - (?/?) |
public class MinimizeAbsoluteDifference {
	MyReader rd = new MyReader();
	long[] x = new long[5];
	// fraction min[0] = numerator, min[1] = denominator
	long[] min = { Integer.MAX_VALUE, 1 };
	int[] sub = new int[4], ans = new int[4];
	boolean[] used = new boolean[5];

	void solve() throws IOException {
		for (int i = 0; i < x.length; i++)
			x[i] = rd.nextLong();

		backtrack(0);

		for (int ind : ans)
			System.out.print(ind + " ");
	}

	// permutation
	void backtrack(int selected) {
		if (selected == 4) {
			long curNum = Math.abs(x[sub[0]] * x[sub[3]] - x[sub[1]] * x[sub[2]]);
			long curDenom = x[sub[1]] * x[sub[3]];

			if (compare(curNum, curDenom) < 0) {
				min[0] = curNum;
				min[1] = curDenom;
				System.arraycopy(sub, 0, ans, 0, sub.length);
			}
			return;
		}

		for (int ind = 0; ind < x.length; ind++)
			if (!used[ind]) {
				sub[selected] = ind;

				used[ind] = true;
				backtrack(selected + 1);
				used[ind] = false;
			}
	}

	int compare(long num, long denom) {
		return Long.compare(num * min[1], min[0] * denom);
	}

	public static void main(String[] args) throws IOException {
		new MinimizeAbsoluteDifference().solve();
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