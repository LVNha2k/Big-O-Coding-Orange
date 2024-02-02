package lec15;

import java.io.*;

// Xenia and Bit Operations Codeforces
public class XeniaAndBitOperations {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, m;
	int[] segtree;
	// indexing 1-based
	// a[i] = segtree[i + 2^n -1]
	// leaf node segtree[j] = a[j - 2^n +1] while j in [2^n -> 2^(n+1)-1]

	void solve() throws IOException {
		n = rd.nextInt();
		m = rd.nextInt();
		segtree = new int[1 << (n + 1)];
		int parent, leftChild, rightChild, type;

		for (int j = 1 << n; j < (1 << (n + 1)); j++) {
			segtree[j] = rd.nextInt();

			for (parent = j / 2, type = 1; parent > 0; parent /= 2, type ^= 1) {
				leftChild = parent << 1;
				rightChild = (parent << 1) + 1;

				segtree[parent] = (type == 1) ? segtree[leftChild] | segtree[rightChild]
						: segtree[leftChild] ^ segtree[rightChild];
			}
		}

		for (int index, p, b; m-- > 0;) {
			p = rd.nextInt();
			b = rd.nextInt();
			index = p + (1 << n) - 1;
			segtree[index] = b;

			for (parent = index / 2, type = 1; parent > 0; parent /= 2, type ^= 1) {
				leftChild = parent << 1;
				rightChild = (parent << 1) + 1;

				segtree[parent] = (type == 1) ? segtree[leftChild] | segtree[rightChild]
						: segtree[leftChild] ^ segtree[rightChild];
			}

			pw.println(segtree[1]);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new XeniaAndBitOperations().solve();
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