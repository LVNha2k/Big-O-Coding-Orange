package lec11;

import java.io.*;
import java.util.*;

// Advanced Fruits SPOJ
public class AdvancedFruits {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 101;
	String a, b;
	int n, m, lcs[][] = new int[MAX][MAX];

	void solve() throws IOException {
		while (sc.hasNext()) {
			n = (a = sc.next()).length();
			m = (b = sc.next()).length();

			a = '@' + a;
			b = '@' + b;

			for (int i = 0; i <= n; i++)
				lcs[i][0] = 0;
			for (int j = 0; j <= m; j++)
				lcs[0][j] = 0;

			for (int i = 1; i <= n; i++)
				for (int j = 1; j <= m; j++)
					if (a.charAt(i) == b.charAt(j))
						lcs[i][j] = 1 + lcs[i - 1][j - 1];
					else
						lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);

			StringBuilder sb = new StringBuilder();
			int i = n, j = m;

			while (i > 0 || j > 0)
				if (i == 0)
					sb.append(b.charAt(j--));
				else if (j == 0)
					sb.append(a.charAt(i--));
				else {
					if (a.charAt(i) == b.charAt(j)) {
						sb.append(a.charAt(i));
						i--;
						j--;
					} else if (lcs[i][j] == lcs[i - 1][j])
						sb.append(a.charAt(i--));
					else
						sb.append(b.charAt(j--));
				}

			pw.println(sb.reverse().toString());
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new AdvancedFruits().solve();
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