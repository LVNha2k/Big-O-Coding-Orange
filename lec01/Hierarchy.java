package lec01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Hierarchy SPOJ
public class Hierarchy {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, K, parent[];
	List<List<Integer>> graph;
	List<Integer> topo;
	boolean[] visited;

	void solve() throws IOException {
		topo = new ArrayList<>(N = rd.nextInt());
		K = rd.nextInt();
		graph = Stream.generate(ArrayList<Integer>::new).limit(N + 1).collect(Collectors.toList());
		visited = new boolean[N + 1];
		parent = new int[N + 1];

		for (int u = 1, W; u <= K; u++) {
			W = rd.nextInt();
			while (W-- > 0)
				graph.get(u).add(rd.nextInt());
		}

		topoSort();
		for (int u, pa = 0, i = N - 1; i >= 0; i--) {
			u = topo.get(i);
			parent[u] = pa;
			pa = u;
		}

		for (int u = 1; u <= N; u++)
			pw.println(parent[u]);
		pw.close();
	}

	void DFS(int u) {
		visited[u] = true;
		for (int v : graph.get(u))
			if (!visited[v])
				DFS(v);
		topo.add(u);
	}

	void topoSort() {
		for (int u = 1; u <= N; u++)
			if (!visited[u])
				DFS(u);
	}

	public static void main(String[] args) throws IOException {
		new Hierarchy().solve();
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