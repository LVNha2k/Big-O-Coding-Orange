package lec04;

import java.io.*;
import java.util.*;

// Fill the Containers UVa
public class FillTheContainers {
	MyScanner sc = new MyScanner();
	PrintWriter pw = new PrintWriter(System.out);
	int n, m, total;
	List<Integer> vessels = new ArrayList<>(1000);

	void solve() throws IOException {
		while (sc.hasNext()) {
			n = sc.nextInt();
			m = sc.nextInt();

			vessels.clear();
			total = 0;

			for (int i = 0, c; i < n; i++) {
				vessels.add(c = sc.nextInt());
				total += c;
			}

			pw.println(binarySearch());
		}
		pw.close();
	}

	// [ ]
	int binarySearch() {
		int low = 0, high = total, mid, res = -1;

		while (low <= high) {
			mid = low + (high - low) / 2;

			if (canFill(mid)) {
				res = mid;
				high = mid - 1;
			} else
				low = mid + 1;
		}
		return res;
	}

	boolean canFill(int capacity) {
		int container = 0, numOfContainer = 0;

		for (int milk : vessels) {
			if (milk > capacity)
				return false;

			if (container + milk > capacity)
				container = 0;

			if (container == 0)
				if (++numOfContainer > m)
					return false;

			container += milk;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		new FillTheContainers().solve();
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