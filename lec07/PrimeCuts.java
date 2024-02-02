package lec07;

import java.io.*;
import java.util.*;

// Prime Cuts UVa
public class PrimeCuts {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	int N, C, size;
	boolean[] isPrime = new boolean[1001];
	List<Integer> list = new ArrayList<>();

	void solve() throws IOException {
		Arrays.fill(isPrime, true);
		isPrime[0] = false;

		for (int i = 2; i * i <= 1000; i++)
			if (isPrime[i])
				for (int j = i * i; j <= 1000; j += i)
					isPrime[j] = false;

		while (sc.hasNext()) {
			N = sc.nextInt();
			C = sc.nextInt();

			list.clear();
			for (int i = 1; i <= N; i++)
				if (isPrime[i])
					list.add(i);

			size = list.size();
			pw.printf("%d %d:", N, C);

			if (size < 2 * C - 1)
				for (int num : list)
					pw.print(" " + num);
			else
				for (int i = size / 2 - C + (size % 2); i <= size / 2 + C - 1; i++)
					pw.print(" " + list.get(i));

			pw.print("\n\n");
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new PrimeCuts().solve();
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