package lec10;

import java.io.*;
import java.util.*;

// Alphacode SPOJ
public class Alphacode {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 5001;
	String s;
	long[] dp = new long[MAX];

	void solve() throws IOException {
		while (!(s = rd.next()).equals("0")) {
			Arrays.fill(dp, 0);
			dp[0] = 1;

			int n = s.length();
			s = '@' + s;

			for (int i = 1; i <= n; i++) {
				if (s.charAt(i) != '0')
					dp[i] += dp[i - 1];

				if (i >= 2) {
					int num = (s.charAt(i - 1) - '0') * 10 + (s.charAt(i) - '0');
					if (num >= 10 && num <= 26)
						dp[i] += dp[i - 2];
				}
			}

			pw.println(dp[n]);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new Alphacode().solve();
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