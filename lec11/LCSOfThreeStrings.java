package lec11;

import java.io.*;

// LCS of three strings GeeksforGeeks
public class LCSOfThreeStrings {
	MyReader rd = new MyReader();
	static final int MAX = 101;
	int T, na, nb, nc;
	char[] A, B, C;
	int[][][] dp = new int[MAX][MAX][MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			na = rd.nextInt();
			nb = rd.nextInt();
			nc = rd.nextInt();

			A = rd.next().toCharArray();
			B = rd.next().toCharArray();
			C = rd.next().toCharArray();

			for (int i = 0; i <= na; i++)
				for (int j = 0; j <= nb; j++)
					for (int k = 0; k <= nc; k++)
						if (i == 0 || j == 0 || k == 0)
							dp[i][j][k] = 0;
						else if (A[i - 1] == B[j - 1] && B[j - 1] == C[k - 1])
							dp[i][j][k] = 1 + dp[i - 1][j - 1][k - 1];
						else
							dp[i][j][k] = Math.max(dp[i - 1][j][k], Math.max(dp[i][j - 1][k], dp[i][j][k - 1]));

			System.out.println(dp[na][nb][nc]);
		}
	}

	public static void main(String[] args) throws IOException {
		new LCSOfThreeStrings().solve();
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