package lec01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Topological Sorting SPOJ
public class TopologicalSorting {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, m, indegree[];
	List<List<Integer>> graph;
	List<Integer> result;

	void solve() throws IOException {
		result = new ArrayList<>(n = rd.nextInt());
		m = rd.nextInt();
		graph = Stream.generate(ArrayList<Integer>::new).limit(n + 1).collect(Collectors.toList());
		indegree = new int[n + 1];

		for (int v; m-- > 0;) {
			graph.get(rd.nextInt()).add(v = rd.nextInt());
			indegree[v]++;
		}

		if (kahn())
			for (int u : result)
				pw.print(u + " ");
		else
			pw.print("Sandro fails.");

		pw.close();
	}

	boolean kahn() {
		PriorityQueue<Integer> zeroIndegree = new PriorityQueue<>();
		for (int u = 1; u <= n; u++)
			if (indegree[u] == 0)
				zeroIndegree.add(u);

		while (!zeroIndegree.isEmpty()) {
			int u = zeroIndegree.poll();
			result.add(u);

			for (int v : graph.get(u))
				if (--indegree[v] == 0)
					zeroIndegree.add(v);
		}

		return result.size() == n;
	}

	public static void main(String[] args) throws IOException {
		new TopologicalSorting().solve();
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