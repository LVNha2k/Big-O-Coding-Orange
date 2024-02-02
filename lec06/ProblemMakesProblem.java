package lec06;

import java.io.*;

// Problem Makes Problem LightOJ
public class ProblemMakesProblem {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = (int) 2e6, MOD = (int) 1e9 + 7;
	int T, n, k;
	long[] fact = new long[MAX];

	void solve() throws IOException {
		fact[0] = 1;
		for (int i = 1; i < MAX; i++)
			fact[i] = (i * fact[i - 1]) % MOD;

		T = rd.nextInt();
		long num, denom, ans;

		for (int tc = 1; tc <= T; tc++) {
			n = rd.nextInt();
			k = rd.nextInt();

			num = fact[n + k - 1];
			denom = (fact[n] * fact[k - 1]) % MOD;
			ans = (num * modInverse(denom, MOD)) % MOD;

			pw.printf("Case %d: %d\n", tc, ans);
		}
		pw.close();
	}

	long modInverse(long b, long m) {
		long res = fastPow(b, m - 2, m);
		if ((res * b) % m == 1)
			return res;
		return -1;
	}

	long fastPow(long a, long b, long m) {
		if (b == 0)
			return 1;
		long res = fastPow(a, b >> 1, m);
		res = (res * res) % m;
		if ((b & 1) == 1)
			res = (res * a) % m;
		return res;
	}

	public static void main(String[] args) throws IOException {
		new ProblemMakesProblem().solve();
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