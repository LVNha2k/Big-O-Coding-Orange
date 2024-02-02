package lec13;

import java.io.*;
import java.util.*;

// Diving for Gold UVa
public class DivingForGold {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 31, MAX_T = 1001;
	int t, w, n;
	Treasure[] treasures = new Treasure[MAX_N];
	int[][] dp = new int[MAX_N][MAX_T];
	List<Treasure> takes = new ArrayList<>();

	void solve() throws IOException {
		boolean first = true;
		while (sc.hasNext()) {
			t = sc.nextInt();
			w = sc.nextInt();
			n = sc.nextInt();

			for (int i = 1; i <= n; i++)
				treasures[i] = new Treasure(sc.nextInt(), sc.nextInt());

			int gold = knapsack();
			takes.clear();

			for (int i = n; i > 0; i--)
				if (dp[i][t] != dp[i - 1][t]) {
					takes.add(treasures[i]);
					t -= treasures[i].depth * 3 * w;
				}

			if (!first)
				pw.print("\n");
			first = false;

			pw.println(gold);
			pw.println(takes.size());

			for (int i = takes.size() - 1; i >= 0; i--)
				pw.println(takes.get(i));
		}
		pw.close();
	}

	int knapsack() {
		for (int i = 1; i <= n; i++)
			for (int j = 0; j <= t; j++)
				if (j < treasures[i].depth * 3 * w)
					dp[i][j] = dp[i - 1][j];
				else {
					int g1 = dp[i - 1][j];
					int g2 = treasures[i].gold + dp[i - 1][j - treasures[i].depth * 3 * w];
					dp[i][j] = Math.max(g1, g2);
				}

		return dp[n][t];
	}

	public static void main(String[] args) throws IOException {
		new DivingForGold().solve();
	}

	class Treasure {
		int depth, gold;

		public Treasure(int depth, int gold) {
			this.depth = depth;
			this.gold = gold;
		}

		@Override
		public String toString() {
			return depth + " " + gold;
		}

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