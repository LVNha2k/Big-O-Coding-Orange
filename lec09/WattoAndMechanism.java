package lec09;

import java.io.*;
import java.util.*;

// Watto and Mechanism Codeforces
public class WattoAndMechanism {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MOD = (int) (1e9) + 7, MAX = (int) 6e5;
	char[] letters = { 'a', 'b', 'c' };
	int n, m, BASE1 = 29, BASE2 = 257;
	long[] pow1 = new long[MAX], pow2 = new long[MAX];
	Set<Long> set1 = new HashSet<>();
	Set<Long> set2 = new HashSet<>();

	void solve() throws IOException {
		pow1[0] = pow2[0] = 1;
		for (int i = 1; i < MAX; i++) {
			pow1[i] = (pow1[i - 1] * BASE1) % MOD;
			pow2[i] = (pow2[i - 1] * BASE2) % MOD;
		}

		n = rd.nextInt();
		m = rd.nextInt();

		for (String s; n-- > 0;) {
			set1.add(polyHash(s = rd.next(), BASE1));
			set2.add(polyHash(s, BASE2));
		}

		while (m-- > 0)
			pw.println(check(rd.next()) ? "YES" : "NO");

		pw.close();
	}

	boolean check(String s) {
		long oldHash1 = polyHash(s, BASE1), newHash1;
		long oldHash2 = polyHash(s, BASE2), newHash2;
		int len = s.length();

		for (int i = 0; i < len; i++)
			for (char ch : letters)
				if (ch != s.charAt(i)) {
					newHash1 = oldHash1 + ((ch - s.charAt(i)) * pow1[len - i - 1] % MOD) + MOD;
					newHash1 %= MOD;

					newHash2 = oldHash2 + ((ch - s.charAt(i)) * pow2[len - i - 1] % MOD) + MOD;
					newHash2 %= MOD;

					if (set1.contains(newHash1) && set2.contains(newHash2))
						return true;
				}

		return false;
	}

	long polyHash(String keys, int BASE) {
		long hashValue = 0;
		for (int i = 0; i < keys.length(); i++)
			hashValue = (keys.charAt(i) - 'a' + 1 + (hashValue * BASE % MOD)) % MOD;
		return hashValue;
	}

	public static void main(String[] args) throws IOException {
		new WattoAndMechanism().solve();
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