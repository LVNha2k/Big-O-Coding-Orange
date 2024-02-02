package lec15;

import java.io.*;
import java.util.*;

// Circular RMQ Codeforces
public class CircularRMQ {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final long INF = (int) 1e18;
	int n, m, a[];
	long[] segtree, lazy;

	void solve() throws IOException {
		a = new int[n = rd.nextInt()];
		for (int i = 0; i < n; i++)
			a[i] = rd.nextInt();

		segtree = new long[4 * n];
		lazy = new long[4 * n];

		buildTree(0, n - 1, 0);

		m = rd.nextInt();
		StringTokenizer st;

		for (int lf, rg, v; m-- > 0;) {
			st = new StringTokenizer(rd.readLine());
			lf = Integer.parseInt(st.nextToken());
			rg = Integer.parseInt(st.nextToken());

			if (st.hasMoreTokens()) {
				v = Integer.parseInt(st.nextToken());
				update(lf, rg, v);
			} else
				pw.println(minRange(lf, rg));
		}
		pw.close();

	}

	void buildTree(int left, int right, int index) {
		if (left == right) {
			segtree[index] = a[left];
			return;
		}
		int mid = (left + right) / 2;
		buildTree(left, mid, 2 * index + 1);
		buildTree(mid + 1, right, 2 * index + 2);
		segtree[index] = Math.min(segtree[2 * index + 1], segtree[2 * index + 2]);
	}

	void update(int qfrom, int qto, int delta) {
		if (qfrom <= qto) {
			updateLazy(qfrom, qto, delta, 0, n - 1, 0);
			return;
		}
		updateLazy(qfrom, n - 1, delta, 0, n - 1, 0);
		updateLazy(0, qto, delta, 0, n - 1, 0);
	}

	long minRange(int qfrom, int qto) {
		if (qfrom <= qto)
			return minRangeLazy(qfrom, qto, 0, n - 1, 0);

		return Math.min(minRangeLazy(qfrom, n - 1, 0, n - 1, 0), minRangeLazy(0, qto, 0, n - 1, 0));
	}

	void updateLazy(int qfrom, int qto, int delta, int left, int right, int index) {
		if (left > right)
			return;
		if (lazy[index] != 0) {
			segtree[index] += lazy[index];
			if (left != right) {
				lazy[2 * index + 1] += lazy[index];
				lazy[2 * index + 2] += lazy[index];
			}
			lazy[index] = 0;
		}
		if (left > qto || right < qfrom)
			return;
		if (left >= qfrom && right <= qto) {
			segtree[index] += delta;
			if (left != right) {
				lazy[2 * index + 1] += delta;
				lazy[2 * index + 2] += delta;
			}
			return;
		}
		int mid = (left + right) / 2;
		updateLazy(qfrom, qto, delta, left, mid, 2 * index + 1);
		updateLazy(qfrom, qto, delta, mid + 1, right, 2 * index + 2);

		segtree[index] = Math.min(segtree[2 * index + 1], segtree[2 * index + 2]);
	}

	long minRangeLazy(int qfrom, int qto, int left, int right, int index) {
		if (left > right)
			return INF;
		if (lazy[index] != 0) {
			segtree[index] += lazy[index];
			if (left != right) {
				lazy[2 * index + 1] += lazy[index];
				lazy[2 * index + 2] += lazy[index];
			}
			lazy[index] = 0;
		}
		if (left > qto || right < qfrom)
			return INF;
		if (left >= qfrom && right <= qto)
			return segtree[index];

		int mid = (left + right) / 2;
		return Math.min(minRangeLazy(qfrom, qto, left, mid, 2 * index + 1),
				minRangeLazy(qfrom, qto, mid + 1, right, 2 * index + 2));
	}

	public static void main(String[] args) throws IOException {
		new CircularRMQ().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 32;
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

		public String readLine() throws IOException {
			byte[] buf = new byte[lineLength];
			int cnt = 0;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = c;
			}
			if (isWindows && buf[cnt - 1] == 13)
				return new String(buf, 0, cnt - 1);
			return new String(buf, 0, cnt);
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