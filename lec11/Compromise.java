package lec11;

import java.io.*;
import java.util.*;

// Compromise UVa
public class Compromise {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 101;
	String[] text1 = new String[MAX], text2 = new String[MAX];
	int n, m;
	int[][] dp = new int[MAX][MAX];
	String[] ans = new String[MAX];

	void solve() throws IOException {
		while (sc.hasNext()) {
			n = m = 0;

			String word;
			while (!(word = sc.next()).equals("#"))
				text1[++n] = word;

			while (!(word = sc.next()).equals("#"))
				text2[++m] = word;

			int len = LCS();
			trace(len);

			for (int i = 0; i < len; i++)
				pw.printf("%s ", ans[i]);
			pw.println();
		}
		pw.close();
	}

	int LCS() {
		for (int i = 0; i <= n; i++)
			dp[i][0] = 0;
		for (int j = 0; j <= m; j++)
			dp[0][j] = 0;

		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++)
				if (text1[i].equals(text2[j]))
					dp[i][j] = 1 + dp[i - 1][j - 1];
				else
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

		return dp[n][m];
	}

	void trace(int lengthLCS) {
		int i = n, j = m;
		while (i > 0 && j > 0)
			if (text1[i].equals(text2[j])) {
				ans[--lengthLCS] = text1[i];
				i--;
				j--;
			} else if (dp[i - 1][j] > dp[i][j - 1])
				i--;
			else
				j--;
	}

	public static void main(String[] args) throws IOException {
		new Compromise().solve();
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