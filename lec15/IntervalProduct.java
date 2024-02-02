package lec15;

import java.io.*;
import java.util.*;

// Interval Product UVa
public class IntervalProduct {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = (int) 1e5 + 1;
	int N, K;
	int[] X = new int[MAX], segtree = new int[4 * MAX];

	void solve() throws IOException {
		while (sc.hasNext()) {
			N = sc.nextInt();
			K = sc.nextInt();
			for (int i = 1, xi; i <= N; i++) {
				xi = sc.nextInt();
				X[i] = xi == 0 ? 0 : (xi > 0) ? 1 : -1;
			}

			buildTree(1, N, 1);

			for (int I, J, V, val; K-- > 0;)
				if (sc.next().equals("C")) {
					I = sc.nextInt();
					V = sc.nextInt();
					val = (V == 0) ? 0 : (V > 0) ? 1 : -1;
					update(I, val, 1, N, 1);
				} else {
					I = sc.nextInt();
					J = sc.nextInt();
					val = productRange(I, J, 1, N, 1);
					pw.print(val == 0 ? '0' : (val > 0) ? '+' : '-');
				}

			pw.println();
		}
		pw.close();
	}

	void buildTree(int left, int right, int index) {
		if (left == right) {
			segtree[index] = X[left];
			return;
		}
		int mid = (left + right) / 2;
		buildTree(left, mid, 2 * index);
		buildTree(mid + 1, right, 2 * index + 1);

		segtree[index] = segtree[2 * index] * segtree[2 * index + 1];
	}

	void update(int pos, int value, int left, int right, int index) {
		if (left > pos || right < pos)
			return;
		if (left == right) {
			segtree[index] = value;
			return;
		}
		int mid = (left + right) / 2;
		if (pos <= mid)
			update(pos, value, left, mid, 2 * index);
		else
			update(pos, value, mid + 1, right, 2 * index + 1);

		segtree[index] = segtree[2 * index] * segtree[2 * index + 1];
	}

	int productRange(int qfrom, int qto, int left, int right, int index) {
		if (left > right)
			return 1;
		if (left > qto || right < qfrom)
			return 1;
		if (left >= qfrom && right <= qto)
			return segtree[index];

		int mid = (left + right) / 2;
		int p1 = productRange(qfrom, qto, left, mid, 2 * index);
		int p2 = productRange(qfrom, qto, mid + 1, right, 2 * index + 1);

		return p1 * p2;
	}

	public static void main(String[] args) throws IOException {
		new IntervalProduct().solve();
	}

	static class MyScanner {
		final int BUFFER_SIZE = 1 << 16;
		final String FILE = "inp.txt";
		BufferedReader br;
		StringTokenizer st;

		public MyScanner() {
			this(false);
		}

		public MyScanner(boolean file) {
			if (file)
				try {
					br = new BufferedReader(new FileReader(FILE), BUFFER_SIZE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				br = new BufferedReader(new InputStreamReader(System.in), BUFFER_SIZE);
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreElements()) {
				String line = br.readLine();
				if (line == null)
					return null;
				st = new StringTokenizer(line);
			}
			return st.nextToken();
		}

		boolean hasNext() throws IOException {
			while (st == null || !st.hasMoreElements()) {
				String line = br.readLine();
				if (line == null)
					return false;
				st = new StringTokenizer(line);
			}
			return st.hasMoreTokens();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
	}
}