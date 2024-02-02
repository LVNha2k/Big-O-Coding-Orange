package lec11;

import java.io.*;

// Palindromic characteristics Codeforces
public class PalindromicCharacteristics {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	char[] s;
	int k, n, dp[][], count[];

	void solve() throws IOException {
		n = (s = rd.next().toCharArray()).length;
		dp = new int[n][n];
		count = new int[n + 1];

		int subLen, L, R;

		for (subLen = 1; subLen <= n; subLen++)
			for (L = 0; L <= n - subLen; L++) {
				R = L + subLen - 1;

				if (subLen == 1)
					dp[L][L] = 1;

				else if (subLen == 2) {
					if (s[L] == s[R])
						dp[L][R] = 2;
				}
				else {
					if (s[L] == s[R] && dp[L + 1][R - 1] > 0)
						dp[L][R] = 1 + dp[L][L + subLen / 2 - 1];
				}

				count[dp[L][R]]++;
			}

		for (k = n - 1; k >= 1; k--)
			count[k] += count[k + 1];

		for (k = 1; k <= n; k++)
			pw.printf("%d ", count[k]);

		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new PalindromicCharacteristics().solve();
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