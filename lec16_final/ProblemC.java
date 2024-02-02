package lec16_final;

import java.io.*;
import java.util.*;

// Little Deepu and Array HackerEarth
public class ProblemC {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	static final int INF = (int) 1e9 + 7;
	int N, M, X, segtree[], lazy[];
	Pair A[];

	void solve() throws IOException {
		N = rd.nextInt();
		A = new Pair[N];
		segtree = new int[4 * N];
		lazy = new int[4 * N];

		for (int i = 0; i < N; i++)
			A[i] = new Pair(i, rd.nextInt());

		Arrays.sort(A, (p1, p2) -> p1.val - p2.val);

		buildTree(0, N - 1, 0);
		M = rd.nextInt();

		while (M-- > 0) {
			X = rd.nextInt();
			int lo = 0, hi = N - 1, mid, pos = -1;

			while (lo <= hi) {
				mid = (lo + hi) / 2;
				if (minRangeLazy(mid, N - 1, 0, N - 1, 0) > X) {
					pos = mid;
					hi = mid - 1;
				} else
					lo = mid + 1;
			}

			if (pos != -1)
				updateLazy(pos, N - 1, -1, 0, N - 1, 0);
		}

		for (int i = 0; i < N; i++)
			A[i].val = minRangeLazy(i, i, 0, N - 1, 0);

		Arrays.sort(A, (p1, p2) -> p1.ith - p2.ith);

		for (Pair p : A)
			pw.print(p.val + " ");
	}

	void buildTree(int left, int right, int index) {
		if (left == right) {
			segtree[index] = A[left].val;
			return;
		}

		int mid = (left + right) / 2;
		buildTree(left, mid, 2 * index + 1);
		buildTree(mid + 1, right, 2 * index + 2);

		segtree[index] = Math.min(segtree[2 * index + 1], segtree[2 * index + 2]);
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

		if (qfrom > right || qto < left)
			return;

		if (qfrom <= left && qto >= right) {
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

	int minRangeLazy(int qfrom, int qto, int left, int right, int index) {
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

		if (qfrom > right || qto < left)
			return INF;

		if (qfrom <= left && qto >= right)
			return segtree[index];

		int mid = (left + right) / 2;
		int q1 = minRangeLazy(qfrom, qto, left, mid, 2 * index + 1);
		int q2 = minRangeLazy(qfrom, qto, mid + 1, right, 2 * index + 2);

		return Math.min(q1, q2);
	}

	public static void main(String[] args) throws IOException {
		new ProblemC().solve();
		pw.close();
	}

	class Pair {
		int ith, val;

		public Pair(int ith, int val) {
			this.ith = ith;
			this.val = val;
		}

	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 128;
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

		public char nextPrintableChar() throws IOException {
			do {
				c = read();
			} while (c < 32 || c > 126);
			return (char) c;
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

		public String readLineReverse() throws IOException {
			byte[] buf = new byte[lineLength];
			int ind = lineLength - 1;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[ind--] = c;
			}
			if (isWindows && buf[ind + 1] == 13)
				return new String(buf, ind + 2, lineLength - (ind + 2));
			return new String(buf, ind + 1, lineLength - (ind + 1));
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

		public long nextLong() throws IOException {
			long ret = 0;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10L + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (isWindows && c == 13)
				read();
			return neg ? -ret : ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
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