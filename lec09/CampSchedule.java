package lec09;

import java.io.*;

// Camp Schedule Codeforces
public class CampSchedule {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	static final int MOD = (int) (1e9) + 7;
	String s, t;
	long[] hash, pow;

	void solve() throws IOException {
		s = rd.next();
		t = rd.next();
		int n = s.length(), m = t.length();

		int[] cnt = new int[2];

		for (int i = 0; i < n; i++)
			cnt[s.charAt(i) - '0']++;
		for (int i = 0; i < m; i++)
			cnt[t.charAt(i) - '0']--;

		if (cnt[0] < 0 || cnt[1] < 0) {
			pw.print(s);
			return;
		}

		hash = new long[m + 1];
		pow = new long[m + 1];
		pow[0] = 1;
		polyHash(t);

		int maxPrefixDuplicate = 0, len = m - 1;

		for (long prefix, suffix; len > 0; len--) {
			prefix = getHash(1, len);
			suffix = getHash(m - len + 1, m);

			if (prefix == suffix) {
				maxPrefixDuplicate = len;
				break;
			}
		}

		int[] need = new int[2];
		for (int i = maxPrefixDuplicate; i < m; i++)
			need[t.charAt(i) - '0']++;

		StringBuilder ans = new StringBuilder(t);
		String add = t.substring(maxPrefixDuplicate, m);

		while (cnt[0] >= need[0] && cnt[1] >= need[1]) {
			ans.append(add);
			cnt[0] -= need[0];
			cnt[1] -= need[1];
		}

		while (cnt[0]-- > 0)
			ans.append('0');
		while (cnt[1]-- > 0)
			ans.append('1');

		pw.print(ans.toString());
	}

	void polyHash(String keys) {
		int base = 29;
		for (int i = 0; i < keys.length(); i++) {
			hash[i + 1] = (keys.charAt(i) + (hash[i] * base % MOD)) % MOD;
			pow[i + 1] = (pow[i] * base) % MOD;
		}
	}

	long getHash(int L, int R) {
		return (hash[R] - (hash[L - 1] * pow[R - L + 1] % MOD) + MOD) % MOD;
	}

	public static void main(String[] args) throws IOException {
		new CampSchedule().solve();
		pw.close();
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