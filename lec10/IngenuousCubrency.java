package lec10;

import java.io.*;
import java.util.*;

// Ingenuous Cubrency UVa
public class IngenuousCubrency {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 10000;
	long[] dp = new long[MAX];

	void solve() throws IOException {
		dp[0] = 1;

		for (int i = 1, coin; i <= 21; i++) {
			coin = i * i * i;
			for (int j = coin; j < MAX; j++)
				dp[j] += dp[j - coin];
		}

		while (sc.hasNext())
			pw.println(dp[sc.nextInt()]);

		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new IngenuousCubrency().solve();
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