package lec04;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Distance in Tree Codeforces
public class DistanceInTree {
	MyReader rd = new MyReader();
	int n, k;
	long ans;
	List<List<Integer>> graph;

	void solve() throws IOException {
		n = rd.nextInt();
		k = rd.nextInt();
		graph = Stream.generate(ArrayList<Integer>::new).limit(n + 1).collect(Collectors.toList());

		for (int i = 0, u, v; i < n - 1; i++) {
			graph.get(u = rd.nextInt()).add(v = rd.nextInt());
			graph.get(v).add(u);
		}

		dfs(1, -1);
		System.out.print(ans);
	}

	int[] dfs(int u, int parent) {
		int[] count_u = new int[k + 1];
		count_u[0] = 1;

		for (int v : graph.get(u))
			if (v != parent) {
				int[] count_v = dfs(v, u);

				for (int i = 0; i < k; i++)
					ans += 1L * count_u[i] * count_v[k - 1 - i];

				for (int i = 1; i <= k; i++)
					count_u[i] += count_v[i - 1];
			}

		return count_u;
	}

	public static void main(String[] args) throws IOException {
		new DistanceInTree().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead;
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