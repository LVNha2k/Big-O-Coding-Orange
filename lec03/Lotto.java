package lec03;

import java.io.*;

// Lotto UVa
public class Lotto {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int k, S[], game[] = new int[6], total, count;

	void solve() throws IOException {
		k = rd.nextInt();
		while (k != 0) {
			S = new int[k];

			for (int i = 0; i < k; i++)
				S[i] = rd.nextInt();

			total = nCk(k, 6);
			count = 1;
			backtrack(0, -1);

			k = rd.nextInt();
			if (k != 0)
				pw.print("\n\n");
		}
		pw.close();
	}

	// selected == index in game[]
	void backtrack(int selected, int pre) {
		if (selected == 6) {
			print();
			return;
		}

		for (int i = pre + 1; i <= k - (6 - selected); i++) {
			game[selected] = S[i];
			backtrack(selected + 1, i);
		}
	}

	void print() {
		StringBuilder sb = new StringBuilder();
		for (int num : game)
			sb.append(num + " ");

		sb.deleteCharAt(sb.length() - 1);
		if (count++ != total)
			sb.append('\n');

		pw.print(sb.toString());
	}

	int nCk(int n, int k) {
		if (k == 0 || k == n)
			return 1;
		if (k == 1)
			return n;
		return nCk(n - 1, k - 1) + nCk(n - 1, k);
	}

	public static void main(String[] args) throws IOException {
		new Lotto().solve();
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