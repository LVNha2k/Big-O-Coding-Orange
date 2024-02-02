package lec01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Answer the boss! SPOJ
public class AnswerTheBoss {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 1000;
	int T, N, R, indegree[] = new int[MAX_N], rank[] = new int[MAX_N];
	boolean[] visited = new boolean[MAX_N];
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX_N).collect(Collectors.toList());
	List<Integer> topo = new ArrayList<>(MAX_N);
	List<Pair> ans = new ArrayList<>(MAX_N);

	void clear() {
		ans.clear();
		topo.clear();
		Arrays.fill(visited, false);
		Arrays.fill(indegree, 0);
		Arrays.fill(rank, 0);
		for (List<Integer> list : graph)
			list.clear();
	}

	void solve() throws IOException {
		T = rd.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			clear();
			N = rd.nextInt();
			R = rd.nextInt();

			for (int v; R-- > 0;) {
				v = rd.nextInt();
				graph.get(rd.nextInt()).add(v);
				indegree[v]++;
			}

			topoSort();

			for (int u : topo) {
				if (indegree[u] == 0)
					rank[u] = 1;
				for (int v : graph.get(u))
					rank[v] = Math.max(rank[v], rank[u] + 1);
			}

			for (int u = 0; u < N; u++)
				ans.add(new Pair(u, rank[u]));
			Collections.sort(ans);

			pw.printf("Scenario #%d:\n", tc);
			for (Pair p : ans)
				pw.println(p);
		}
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
		for (int u = 0; u < N; u++)
			if (!visited[u])
				DFS(u);
		Collections.reverse(topo);
	}

	public static void main(String[] args) throws IOException {
		new AnswerTheBoss().solve();
	}

	class Pair implements Comparable<Pair> {
		int emp, rank;

		public Pair(int emp, int rank) {
			this.emp = emp;
			this.rank = rank;
		}

		@Override
		public int compareTo(Pair that) {
			int c = Integer.compare(this.rank, that.rank);
			return c != 0 ? c : Integer.compare(this.emp, that.emp);
		}

		@Override
		public String toString() {
			return rank + " " + emp;
		}
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