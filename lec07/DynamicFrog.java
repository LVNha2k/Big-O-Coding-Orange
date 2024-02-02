package lec07;

import java.io.*;

// Dynamic Frog UVa
public class DynamicFrog {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int T, N, D, dist[];
	boolean isBig[];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			N = rd.nextInt();
			D = rd.nextInt();

			dist = new int[N + 2];
			isBig = new boolean[N + 2];

			for (int i = 1; i <= N; i++) {
				String s = rd.next();
				isBig[i] = s.startsWith("B");
				dist[i] = Integer.parseInt(s.substring(2));
			}

			isBig[0] = isBig[N + 1] = true;
			dist[0] = 0;
			dist[N + 1] = D;

			int ans = 0, lastBig = 0;

			for (int i = 1; i <= N + 1; i++)
				if (isBig[i]) {
					ans = Math.max(ans, minimaxLeap(lastBig, i));
					lastBig = i;
				}

			pw.printf("Case %d: %d\n", tc, ans);
		}
		pw.close();
	}

	int minimaxLeap(int L, int R) {
		if (L + 1 == R)
			return dist[R] - dist[L];

		int leap = 0;

		for (int i = L; i <= R - 2; ++i)
			leap = Math.max(leap, dist[i + 2] - dist[i]);

		return leap;
	}

	public static void main(String[] args) throws IOException {
		new DynamicFrog().solve();
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