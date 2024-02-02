package lec15;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Course Schedule
public class CourseSchedule {
	MyReader rd = new MyReader();
	int N, P, visited[];
	List<List<Integer>> graph;

	void solve() throws IOException {
		N = rd.nextInt();
		P = rd.nextInt();
		graph = Stream.generate(ArrayList<Integer>::new).limit(N).collect(Collectors.toList());
		visited = new int[N];

		while (P-- > 0)
			graph.get(rd.nextInt()).add(rd.nextInt());

		for (int u = 0; u < N; u++)
			if (visited[u] == 0 && isCyclic(u)) {
				System.out.print("no");
				return;
			}

		System.out.print("yes");
	}

	boolean isCyclic(int u) {
		visited[u] = 1;

		for (int v : graph.get(u))
			if (visited[v] == 1)
				return true;
			else if (visited[v] == 0 && isCyclic(v))
				return true;

		visited[u] = 2;
		return false;
	}

	public static void main(String[] args) throws IOException {
		new CourseSchedule().solve();
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