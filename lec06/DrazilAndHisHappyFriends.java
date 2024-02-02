package lec06;

import java.io.*;

// Drazil and His Happy Friends Codeforces
public class DrazilAndHisHappyFriends {
	MyReader rd = new MyReader();
	int n, m, b, g, boys[], girls[];

	void solve() throws IOException {
		boys = new int[n = rd.nextInt()];
		girls = new int[m = rd.nextInt()];

		b = rd.nextInt();
		for (int i = 0; i < b; i++)
			boys[rd.nextInt()] = 1;

		g = rd.nextInt();
		for (int i = 0; i < g; i++)
			girls[rd.nextInt()] = 1;

		int limit = 2 * lcm(n, m);

		for (int i = 0, indBoy, indGirl; i < limit; i++) {
			indBoy = i % n;
			indGirl = i % m;

			if (boys[indBoy] != girls[indGirl]) {
				b += boys[indBoy] ^ 1;
				g += girls[indGirl] ^ 1;
				boys[indBoy] = girls[indGirl] = 1;
			}
		}

		System.out.print((b == n && g == m) ? "Yes" : "No");
	}

	int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	int lcm(int a, int b) {
		return (a / gcd(a, b)) * b;
	}

	public static void main(String[] args) throws IOException {
		new DrazilAndHisHappyFriends().solve();
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