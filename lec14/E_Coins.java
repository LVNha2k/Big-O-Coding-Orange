package lec14;

import java.io.*;

// e-Coins UVa
public class E_Coins {
	MyReader rd = new MyReader();
	static final int MAX_M = 40, MAX_S = 301, INF = (int) 1e9;
	int n, m, S;
	int[] conven = new int[MAX_M], techno = new int[MAX_M];
	int[][] dp = new int[MAX_S][MAX_S];

	void solve() throws IOException {
		n = rd.nextInt();
		while (n-- > 0) {
			m = rd.nextInt();
			S = rd.nextInt();

			for (int i = 0; i < m; i++) {
				conven[i] = rd.nextInt();
				techno[i] = rd.nextInt();
			}

			for (int i = 0; i <= S; i++)
				for (int j = 0; j <= S; j++) {
					if (i == 0 && j == 0)
						continue;
					dp[i][j] = INF;

					for (int k = 0; k < m; k++)
						if (i >= conven[k] && j >= techno[k])
							dp[i][j] = Math.min(dp[i][j], dp[i - conven[k]][j - techno[k]] + 1);
				}

			int ans = INF;
			for (int conven = 0, techno; conven <= S; conven++) {
				techno = (int) Math.sqrt(S * S - conven * conven);
				
				if (techno * techno == S * S - conven * conven)
					ans = Math.min(ans, dp[conven][techno]);
			}

			System.out.println(ans == INF ? "not possible" : ans);
		}
	}

	public static void main(String[] args) throws IOException {
		new E_Coins().solve();
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