package lec06;

import java.io.*;

// Play with Floor and Ceil UVa
public class PlayWithFloorAndCeil {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	long T, x, k, p, q, gcd;

	void solve() throws IOException {
		long a, b; // Diophantine: a * p + b * q = x
		T = rd.nextInt();

		while (T-- > 0) {
			x = rd.nextInt();
			k = rd.nextInt();

			a = x / k;
			b = (long) Math.ceil(x / (double) k);
			extEuclid(a, b);

			p *= x / gcd;
			q *= x / gcd;

			pw.println(p + " " + q);
		}
		pw.close();
	}

	// Diophantine: a * p + b * q = x
	void extEuclid(long a, long b) {
		if (b == 0) {
			p = 1;
			q = 0;
			gcd = a;
			return;
		}

		extEuclid(b, a % b);
		long tmp = p;
		p = q;
		q = tmp - (a / b) * q;
	}

	public static void main(String[] args) throws IOException {
		new PlayWithFloorAndCeil().solve();
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