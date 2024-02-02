package lec11;

import java.io.*;

// Aibohphobia SPOJ
public class Aibohphobia {
	MyReader rd = new MyReader();
	static final int MAX = 6100;
	int t, n;
	char[] S;
	int[][] maxPalinLen = new int[MAX][MAX];

	void solve() throws IOException {
		t = 1;
		while (t-- > 0) {
			S = rd.next().toCharArray();
			n = S.length;

			for (int i = 0; i < n; i++)
				maxPalinLen[i][i] = 1;

			for (int i = 0; i < n - 1; i++)
				if (S[i] == S[i + 1])
					maxPalinLen[i][i + 1] = 2;
				else
					maxPalinLen[i][i + 1] = 1;

			for (int length = 3; length <= n; length++)
				for (int start = 0, end; start <= n - length; start++) {
					end = start + length - 1;

					if (S[start] == S[end])
						maxPalinLen[start][end] = 2 + maxPalinLen[start + 1][end - 1];
					else
						maxPalinLen[start][end] = Math.max(maxPalinLen[start + 1][end], maxPalinLen[start][end - 1]);
				}

			System.out.println(n - maxPalinLen[0][n - 1]);
		}
	}

	public static void main(String[] args) throws IOException {
		new Aibohphobia().solve();
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