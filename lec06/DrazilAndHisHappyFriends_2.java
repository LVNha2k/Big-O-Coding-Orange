package lec06;

import java.io.*;

// Drazil and His Happy Friends Codeforces
// Diophantine 
public class DrazilAndHisHappyFriends_2 {
	MyReader rd = new MyReader();
	int n, m, b, g;
	boolean[] arr;

	void solve() throws IOException {
		int d = gcd(n = rd.nextInt(), m = rd.nextInt());
		arr = new boolean[d];

		b = rd.nextInt();
		while (b-- > 0)
			arr[rd.nextInt() % d] = true;

		g = rd.nextInt();
		while (g-- > 0)
			arr[rd.nextInt() % d] = true;

		for (boolean element : arr)
			if (!element) {
				System.out.print("No");
				return;
			}
		System.out.print("Yes");
	}

	int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		new DrazilAndHisHappyFriends_2().solve();
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