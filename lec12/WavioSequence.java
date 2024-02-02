package lec12;

import java.io.*;
import java.util.*;

// Wavio Sequence UVa
public class WavioSequence {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 10000;
	int N;
	int[] a = new int[MAX], aReverse = new int[MAX];
	int[] increasingLen = new int[MAX], decreasingLen = new int[MAX];

	void solve() throws IOException {
		while (sc.hasNext()) {
			N = sc.nextInt();

			for (int i = 0; i < N; i++) {
				a[i] = sc.nextInt();
				aReverse[N - i - 1] = a[i];
			}

			LIS(a, increasingLen);
			LIS(aReverse, decreasingLen);

			int ans = 0;
			for (int i = 0, minLen; i < N; i++) {
				minLen = Math.min(increasingLen[i], decreasingLen[N - i - 1]);
				ans = Math.max(ans, minLen * 2 - 1);
			}

			pw.println(ans);
		}
		pw.close();
	}

	void LIS(int[] arr, int[] lis) {
		lis[0] = 1;
		List<Integer> sub = new ArrayList<>();
		sub.add(arr[0]);

		for (int i = 1, pos; i < N; i++) {
			pos = lowerBound(sub, arr[i]);
			if (pos == sub.size())
				sub.add(arr[i]);
			else
				sub.set(pos, arr[i]);
			lis[i] = pos + 1;
		}
	}

	// [ )
	int lowerBound(List<Integer> list, int value) {
		int left = 0, right = list.size(), pos = right, mid;

		while (left < right) {
			mid = (left + right) / 2;
			if (list.get(mid) >= value)
				pos = right = mid;
			else
				left = mid + 1;
		}
		return pos;
	}

	public static void main(String[] args) throws IOException {
		new WavioSequence().solve();
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