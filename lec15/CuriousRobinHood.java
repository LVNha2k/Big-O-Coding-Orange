package lec15;

import java.io.*;

// Curious Robin Hood LightOJ
public class CuriousRobinHood {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = (int) 1e5;
	int T, n, q;
	int[] arr = new int[MAX], segtree = new int[4 * MAX];

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			n = rd.nextInt();
			q = rd.nextInt();
			for (int i = 0; i < n; i++)
				arr[i] = rd.nextInt();

			buildTree(0, n - 1, 0);
			pw.printf("Case %d:\n", tc);

			for (int type, i, j, v; q-- > 0;) {
				type = rd.nextInt();

				if (type == 1) {
					i = rd.nextInt();
					pw.println(arr[i]);
					if (arr[i] != 0) {
						update(i, -arr[i], 0, n - 1, 0);
						arr[i] = 0;
					}
				} else if (type == 2) {
					i = rd.nextInt();
					v = rd.nextInt();
					update(i, v, 0, n - 1, 0);
					arr[i] += v;
				} else {
					i = rd.nextInt();
					j = rd.nextInt();
					pw.println(sumRange(i, j, 0, n - 1, 0));
				}
			}

		}
		pw.close();
	}

	void buildTree(int left, int right, int index) {
		if (left == right) {
			segtree[index] = arr[left];
			return;
		}
		int mid = (left + right) / 2;
		buildTree(left, mid, 2 * index + 1);
		buildTree(mid + 1, right, 2 * index + 2);

		segtree[index] = segtree[2 * index + 1] + segtree[2 * index + 2];
	}

	void update(int pos, int delta, int left, int right, int index) {
		if (left > pos || right < pos)
			return;
		if (left == right) {
			segtree[index] += delta;
			return;
		}
		int mid = (left + right) / 2;
		if (pos <= mid)
			update(pos, delta, left, mid, 2 * index + 1);
		else
			update(pos, delta, mid + 1, right, 2 * index + 2);

		segtree[index] = segtree[2 * index + 1] + segtree[2 * index + 2];
	}

	int sumRange(int qfrom, int qto, int left, int right, int index) {
		if (left > right)
			return 0;
		if (left > qto || right < qfrom)
			return 0;
		if (left >= qfrom && right <= qto)
			return segtree[index];

		int mid = (left + right) / 2;
		int s1 = sumRange(qfrom, qto, left, mid, 2 * index + 1);
		int s2 = sumRange(qfrom, qto, mid + 1, right, 2 * index + 2);

		return s1 + s2;
	}

	public static void main(String[] args) throws IOException {
		new CuriousRobinHood().solve();
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