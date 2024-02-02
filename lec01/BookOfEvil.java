package lec01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Book of Evil Codeforces
public class BookOfEvil {
	MyReader rd = new MyReader();
	int n, m, d, affected[];
	List<List<Integer>> graph;

	void solve() throws IOException {
		n = rd.nextInt();
		affected = new int[m = rd.nextInt()];
		d = rd.nextInt();
		for (int i = 0; i < m; i++)
			affected[i] = rd.nextInt();

		graph = Stream.generate(ArrayList<Integer>::new).limit(n + 1).collect(Collectors.toList());
		for (int i = 0, u, v; i < n - 1; i++) {
			graph.get(u = rd.nextInt()).add(v = rd.nextInt());
			graph.get(v).add(u);
		}

		int dist[] = new int[n + 1];
		Arrays.fill(dist, -1);
		dist[affected[0]] = 0;
		DFS(affected[0], dist);

		// Diameter of a tree
		int s = affected[0], r = affected[0];

		for (int u : affected)
			if (dist[u] > dist[s])
				s = u;

		Arrays.fill(dist, -1);
		dist[s] = 0;
		DFS(s, dist);

		for (int v : affected)
			if (dist[v] > dist[r])
				r = v;

		int[] distS = new int[n + 1], distR = new int[n + 1];
		Arrays.fill(distS, -1);
		Arrays.fill(distR, -1);
		distS[s] = 0;
		distR[r] = 0;
		DFS(s, distS);
		DFS(r, distR);

		int ans = 0;
		for (int u = 1; u <= n; u++)
			if (distS[u] <= d && distR[u] <= d)
				ans++;

		System.out.print(ans);
	}

	void DFS(int u, int[] dist) {
		for (int v : graph.get(u))
			if (dist[v] == -1) {
				dist[v] = dist[u] + 1;
				DFS(v, dist);
			}
	}

	public static void main(String[] args) throws IOException {
		new BookOfEvil().solve();
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