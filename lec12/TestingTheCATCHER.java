package lec12;

import java.io.*;
import java.util.*;

// Testing the CATCHER UVa
public class TestingTheCATCHER {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	List<Integer> a = new ArrayList<>();
	int dp[];

	void solve() throws IOException {
		int tc = 1, height;
		while ((height = rd.nextInt()) != -1) {
			a.clear();

			do {
				a.add(height);
			} while ((height = rd.nextInt()) != -1);

			dp = new int[a.size()];
			Arrays.fill(dp, 1);

			int ans = 0;

			for (int i = 1; i < a.size(); i++) {
				for (int j = 0; j < i; j++)
					if (a.get(j) >= a.get(i))
						dp[i] = Math.max(dp[i], dp[j] + 1);

				ans = Math.max(ans, dp[i]);
			}

			if (tc != 1)
				pw.print("\n\n");

			pw.printf("Test #%d:\n", tc++);
			pw.printf("  maximum possible interceptions: %d", ans);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new TestingTheCATCHER().solve();
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