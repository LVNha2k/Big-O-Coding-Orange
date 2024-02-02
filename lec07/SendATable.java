package lec07;

import java.io.*;

// Send a Table UVa
public class SendATable {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 50001;
	int N, ans, phiCache[] = new int[MAX_N];

	void solve() throws IOException {
		while ((N = rd.nextInt()) != 0) {

			ans = 1;
			for (int y = 2; y <= N; y++)
				ans += eulerPhi(y) * 2;

			pw.println(ans);
		}
		pw.close();
	}

	int eulerPhi(int n) {
		if (phiCache[n] > 0)
			return phiCache[n];

		int res = n, m = n;

		for (int i = 2; i * i <= n; i++) {
			if (n % i == 0) {
				while (n % i == 0)
					n /= i;
				res = res / i * (i - 1);
			}
		}

		if (n > 1)
			res = res / n * (n - 1);

		return phiCache[m] = res;
	}

	public static void main(String[] args) throws IOException {
		new SendATable().solve();
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