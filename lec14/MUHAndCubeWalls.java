package lec14;

import java.io.*;

// MUH and Cube Walls Codeforces
public class MUHAndCubeWalls {
	MyReader rd = new MyReader();
	int n, w, a[], b[], prefix[];

	void solve() throws IOException {
		n = rd.nextInt();
		w = rd.nextInt();
		if (w == 1) {
			System.out.print(n);
			return;
		}
		a = new int[n];
		b = new int[w];

		// dA[i] = a[i + 1] - a[i]
		a[0] = rd.nextInt();
		for (int i = 1; i < n; i++) {
			a[i] = rd.nextInt();
			a[i - 1] = a[i] - a[i - 1];
		}

		// dB[i] = b[i + 1] - b[i]
		b[0] = rd.nextInt();
		for (int i = 1; i < w; i++) {
			b[i] = rd.nextInt();
			b[i - 1] = b[i] - b[i - 1];
		}

		--n;
		prefix = new int[--w];
		KMPpreprocess();
		System.out.print(KMPsearch());
	}

	void KMPpreprocess() {
		int j = 0;
		for (int i = 1; i < w; i++) {
			while (j > 0 && b[i] != b[j])
				j = prefix[j - 1];
			if (b[i] == b[j])
				j++;
			prefix[i] = j;
		}
	}

	int KMPsearch() {
		int res = 0, j = 0;
		for (int i = 0; i < n; i++) {
			while (j > 0 && a[i] != b[j])
				j = prefix[j - 1];
			if (a[i] == b[j])
				j++;
			if (j == w) {
				res++;
				j = prefix[j - 1];
			}
		}
		return res;
	}

	public static void main(String[] args) throws IOException {
		new MUHAndCubeWalls().solve();
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