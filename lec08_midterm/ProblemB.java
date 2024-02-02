package lec08_midterm;

import java.io.*;

//The Sultan's Successors UVa
public class ProblemB {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	int k, board[][] = new int[8][8], ans;
	boolean[] col = new boolean[8], diag1 = new boolean[15], diag2 = new boolean[15];

	void solve() throws IOException {
		k = rd.nextInt();
		while (k-- > 0) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++)
					board[i][j] = rd.nextInt();

			ans = 0;
			backtrack(0, 0);
			pw.printf("%5d\n", ans);
		}
	}

	void backtrack(int row, int score) {
		if (row == 8) {
			ans = Math.max(ans, score);
			return;
		}

		for (int i = 0; i < 8; i++)
			if (!col[i] && !diag1[row - i + 7] && !diag2[row + i]) {
				col[i] = diag1[row - i + 7] = diag2[row + i] = true;

				backtrack(row + 1, score + board[row][i]);

				col[i] = diag1[row - i + 7] = diag2[row + i] = false;
			}
	}

	public static void main(String[] args) throws IOException {
		new ProblemB().solve();
		pw.close();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 128;
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

		public char nextPrintableChar() throws IOException {
			do {
				c = read();
			} while (c < 32 || c > 126);
			return (char) c;
		}

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
		}

		public String readLine() throws IOException {
			byte[] buf = new byte[lineLength];
			int cnt = 0;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = c;
			}
			if (isWindows && buf[cnt - 1] == 13)
				return new String(buf, 0, cnt - 1);
			return new String(buf, 0, cnt);
		}

		public String readLineReverse() throws IOException {
			byte[] buf = new byte[lineLength];
			int ind = lineLength - 1;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[ind--] = c;
			}
			if (isWindows && buf[ind + 1] == 13)
				return new String(buf, ind + 2, lineLength - (ind + 2));
			return new String(buf, ind + 1, lineLength - (ind + 1));
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

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
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