package lec13;

import java.io.*;

// DNA Sequences SPOJ
public class DNASequences {
	MyReader rd = new MyReader();
	static final int MAX = 1001;
	int K, n, m;
	String s, t;
	int[][] dp = new int[MAX][MAX], conse = new int[MAX][MAX];

	void solve() throws IOException {
		while ((K = rd.nextInt()) != 0) {
			n = (s = rd.next()).length();
			m = (t = rd.next()).length();

			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= m; j++) {
					if (s.charAt(i - 1) == t.charAt(j - 1))
						conse[i][j] = conse[i - 1][j - 1] + 1;
					else
						conse[i][j] = 0;

					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

					for (int e = K; conse[i][j] >= e; e++)
						dp[i][j] = Math.max(dp[i][j], dp[i - e][j - e] + e);
				}

			System.out.println(dp[n][m]);
		}
	}

	public static void main(String[] args) throws IOException {
		new DNASequences().solve();
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