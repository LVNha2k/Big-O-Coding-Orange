package lec12;

import java.io.*;

// Check Transcription Codeforces
public class CheckTranscription {
	MyReader rd = new MyReader();
	static final int MOD = (int) (1e9) + 7;
	String s, t;
	int n, m, one, zero, ans;
	long[] hash, pow;

	void solve() throws IOException {
		n = (s = rd.next()).length();
		m = (t = rd.next()).length();

		for (int i = 0; i < n; i++)
			if (s.charAt(i) == '0')
				zero++;
			else
				one++;

		hash = new long[m + 1];
		pow = new long[m + 1];
		pow[0] = 1;
		int base = 41;

		for (int i = 0; i < m; i++) {
			hash[i + 1] = (t.charAt(i) - 'a' + 1 + (hash[i] * base % MOD)) % MOD;
			pow[i + 1] = (pow[i] * base) % MOD;
		}

		int len0, len1, sumLen1, start;
		long hash0, hash1;
		boolean valid;

		for (len0 = 1; len0 < m / zero; len0++) {
			sumLen1 = m - len0 * zero;
			if (sumLen1 % one != 0)
				continue;
			len1 = sumLen1 / one;

			hash0 = hash1 = -1;
			valid = true;
			start = 1;

			for (int i = 0; i < n; i++)
				if (s.charAt(i) == '0') {
					if (hash0 == -1)
						hash0 = getHash(start, start + len0 - 1);

					else if (hash0 != getHash(start, start + len0 - 1)) {
						valid = false;
						break;
					}
					start += len0;
				}
				else {
					if (hash1 == -1)
						hash1 = getHash(start, start + len1 - 1);

					else if (hash1 != getHash(start, start + len1 - 1)) {
						valid = false;
						break;
					}
					start += len1;
				}

			if (len0 == len1 && hash0 == hash1)
				valid = false;

			if (valid)
				ans++;
		}

		System.out.print(ans);
	}

	long getHash(int L, int R) {
		return (hash[R] - (hash[L - 1] * pow[R - L + 1] % MOD) + MOD) % MOD;
	}

	public static void main(String[] args) throws IOException {
		new CheckTranscription().solve();
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