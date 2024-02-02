package lec10;

import java.io.*;
import java.util.*;

// Bytelandian gold coins SPOJ
public class BytelandianGoldCoins {
	MyScanner sc = new MyScanner();
	static final int MAX = (int) 1e6 + 1;
	int n;
	long dp[] = new long[MAX];

	void solve() throws IOException {
		while (sc.hasNext()) {
			n = sc.nextInt();
			System.out.println(get(n));
		}
	}

	long get(int n) {
		if (n <= 1)
			return n;

		if (n < MAX && dp[n] > 0)
			return dp[n];

		long res = Math.max(n, get(n / 2) + get(n / 3) + get(n / 4));
		if (n < MAX)
			dp[n] = res;
		return res;
	}

	public static void main(String[] args) throws IOException {
		new BytelandianGoldCoins().solve();
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