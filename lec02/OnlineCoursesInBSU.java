package lec02;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Online Courses in BSU Codeforces
public class OnlineCoursesInBSU {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	int n, k, mainCourses[], visited[];
	// Edge u -> v: Passed course v before starting course u
	List<List<Integer>> graph;
	List<Integer> topo;
	boolean cycle;

	void solve() throws IOException {
		topo = new ArrayList<>(n = rd.nextInt());
		mainCourses = new int[k = rd.nextInt()];
		for (int i = 0; i < k; i++)
			mainCourses[i] = rd.nextInt();

		graph = Stream.generate(ArrayList<Integer>::new).limit(n + 1).collect(Collectors.toList());
		visited = new int[n + 1];

		for (int u = 1, t; u <= n; u++) {
			t = rd.nextInt();
			while (t-- > 0)
				graph.get(u).add(rd.nextInt()); // u -> v
		}

		topoSort();

		if (cycle) {
			pw.print(-1);
			return;
		}

		pw.println(topo.size());
		for (int c : topo)
			pw.print(c + " ");
	}

	void topoSort() {
		for (int i = 0; i < k && !cycle; i++)
			DFS(mainCourses[i]);
	}

	void DFS(int u) {
		if (visited[u] == 0) {
			visited[u] = 1;

			for (int v : graph.get(u))
				if (!cycle)
					DFS(v);

			visited[u] = 2;
			topo.add(u);

		} else if (visited[u] == 1)
			cycle = true;
	}

	public static void main(String[] args) throws IOException {
		new OnlineCoursesInBSU().solve();
		pw.close();
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