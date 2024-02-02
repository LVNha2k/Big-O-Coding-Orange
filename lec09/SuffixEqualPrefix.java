package lec09;

import java.io.*;

// Suffix Equal Prefix SPOJ
public class SuffixEqualPrefix {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = (int) (1e6) + 1, MOD = (int) (1e9) + 7;
	int T;
	String S;
	long[] f = new long[MAX], mul = new long[MAX];

	void solve() throws IOException {
		mul[0] = 1;
		for (int i = 1; i < MAX; i++)
			mul[i] = (mul[i - 1] * 26) % MOD;

		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			S = rd.next();

			polyHash(S);

			int n = S.length(), ans = 0, len = 1;

			for (long prefix, suffix; len < n; len++) {
				prefix = f[len];
				suffix = (f[n] - (f[n - len] * mul[len] % MOD) + MOD) % MOD;
				if (prefix == suffix)
					ans++;
			}

			pw.printf("Case %d: %d\n", tc, ans);
		}
		pw.close();
	}

	void polyHash(String keys) {
		long hashValue = 0;
		for (int i = 0; i < keys.length(); i++) {
			hashValue = (code(keys.charAt(i)) + (26 * hashValue % MOD)) % MOD;
			f[i + 1] = hashValue;
		}
	}

	int code(char c) {
		return c - 'a' + 1;
	}

	public static void main(String[] args) throws IOException {
		new SuffixEqualPrefix().solve();
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