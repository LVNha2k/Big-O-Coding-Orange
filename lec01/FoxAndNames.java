package lec01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Fox And Names Codeforces
public class FoxAndNames {
	MyReader rd = new MyReader();
	int n, indegree[] = new int[26];
	String names[];
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(26).collect(Collectors.toList());
	List<Integer> topo = new ArrayList<>(26);

	void solve() throws IOException {
		names = new String[n = rd.nextInt()];
		for (int i = 0; i < n; i++)
			names[i] = rd.next();

		boolean shorterComesFirst = true;
		for (int i = 0; i < n - 1; i++) {
			String name1 = names[i], name2 = names[i + 1];
			boolean isPrefix = true;

			for (int j = 0, u, v; j < Math.min(name1.length(), name2.length()); j++) {
				u = name1.charAt(j) - 'a';
				v = name2.charAt(j) - 'a';
				if (u != v) {
					graph.get(u).add(v);
					indegree[v]++;
					isPrefix = false;
					break;
				}
			}

			if (isPrefix && name1.length() > name2.length()) {
				shorterComesFirst = false;
				break;
			}
		}

		if (shorterComesFirst && kahn())
			for (int u : topo)
				System.out.print((char) (u + 'a'));
		else
			System.out.print("Impossible");
	}

	boolean kahn() {
		Queue<Integer> zeroIndegree = new LinkedList<>();
		for (int u = 0; u < 26; u++)
			if (indegree[u] == 0)
				zeroIndegree.add(u);

		while (!zeroIndegree.isEmpty()) {
			int u = zeroIndegree.poll();
			topo.add(u);

			for (int v : graph.get(u))
				if (--indegree[v] == 0)
					zeroIndegree.add(v);
		}

		return topo.size() == 26;
	}

	public static void main(String[] args) throws IOException {
		new FoxAndNames().solve();
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

		public String next() throws IOException {
			StringBuilder sb = new StringBuilder();
			while ((c = read()) <= ' ')
				;
			do {
				sb.append((char) c);
			} while ((c = read()) > ' ');
			if (isWindows && c == 13)
				read();
			return sb.toString();
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