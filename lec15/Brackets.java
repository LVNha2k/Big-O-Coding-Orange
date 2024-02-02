package lec15;

import java.io.*;

// Brackets SPOJ
public class Brackets {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 30001;

	class Node {
		int open, close, match;
		public Node() {
		}
	}

	int n, m;
	String word;
	Node[] segtree = new Node[4 * MAX];

	void solve() throws IOException {
		for (int i = 1; i < segtree.length; i++)
			segtree[i] = new Node();

		for (int tc = 1; tc <= 10; tc++) {
			n = rd.nextInt();
			word = '#' + rd.next();
			m = rd.nextInt();

			buildTree(1, n, 1);

			pw.printf("Test %d:\n", tc);

			for (int k; m-- > 0;) {
				k = rd.nextInt();

				if (k != 0)
					update(k, 1, n, 1);
				else
					pw.println((n % 2 == 0 && segtree[1].match == n / 2) ? "YES" : "NO");
			}

		}
		pw.close();
	}

	void buildTree(int left, int right, int index) {
		if (left == right) {
			if (word.charAt(left) == ')') {
				segtree[index].open = 0;
				segtree[index].close = 1;
			} else {
				segtree[index].open = 1;
				segtree[index].close = 0;
			}
			segtree[index].match = 0;
			return;
		}

		int mid = (left + right) / 2;
		buildTree(left, mid, 2 * index);
		buildTree(mid + 1, right, 2 * index + 1);

		calc(segtree[2 * index], segtree[2 * index + 1], segtree[index]);
	}

	void calc(Node left, Node right, Node index) {
		index.match = left.match + right.match;
		index.match += Math.min(left.open - left.match, right.close - right.match);
		index.open = left.open + right.open;
		index.close = left.close + right.close;
	}

	void update(int pos, int left, int right, int index) {
		if (left > pos || right < pos)
			return;
		if (left == right) {
			segtree[index].open ^= 1;
			segtree[index].close ^= 1;
			return;
		}

		int mid = (left + right) / 2;
		update(pos, left, mid, 2 * index);
		update(pos, mid + 1, right, 2 * index + 1);

		calc(segtree[2 * index], segtree[2 * index + 1], segtree[index]);
	}

	public static void main(String[] args) throws IOException {
		new Brackets().solve();
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