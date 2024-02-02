package lec02;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Brexit Negotiations Kattis
public class BrexitNegotiations {
	MyReader rd = new MyReader();
	int n, e[], indegree[];
	List<List<Integer>> reverseGraph;

	void solve() throws IOException {
		n = rd.nextInt();
		reverseGraph = Stream.generate(ArrayList<Integer>::new).limit(n + 1).collect(Collectors.toList());
		indegree = new int[n + 1];
		e = new int[n + 1];

		for (int u = 1, d, v; u <= n; u++) {
			e[u] = rd.nextInt();
			d = rd.nextInt();
			while (d-- > 0) {
				reverseGraph.get(u).add(v = rd.nextInt());
				indegree[v]++;
			}
		}

		System.out.print(kahn());
	}

	int kahn() {
		int ans = 0, recapTime = n - 1;
		PriorityQueue<Pair> zeroIndegree = new PriorityQueue<>();
		for (int u = 1; u <= n; u++)
			if (indegree[u] == 0)
				zeroIndegree.add(new Pair(u, e[u]));

		while (!zeroIndegree.isEmpty()) {
			Pair p = zeroIndegree.poll();
			ans = Math.max(ans, p.time + recapTime);
			recapTime--;

			for (int v : reverseGraph.get(p.topic))
				if (--indegree[v] == 0)
					zeroIndegree.add(new Pair(v, e[v]));
		}

		return ans;
	}

	public static void main(String[] args) throws IOException {
		new BrexitNegotiations().solve();
	}

	class Pair implements Comparable<Pair> {
		int topic, time;

		public Pair(int topic, int time) {
			this.topic = topic;
			this.time = time;
		}

		@Override
		public int compareTo(Pair that) {
			return Integer.compare(this.time, that.time);
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