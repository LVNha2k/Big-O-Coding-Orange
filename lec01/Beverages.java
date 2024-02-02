package lec01;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Beverages UVa
public class Beverages {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 101;
	int N, M, indegree[] = new int[MAX_N];
	String[] beverages = new String[MAX_N];
	List<List<Integer>> graph = Stream.generate(ArrayList<Integer>::new).limit(MAX_N).collect(Collectors.toList());
	Map<String, Integer> map = new HashMap<>(MAX_N, 1);
	List<Integer> result = new ArrayList<>(MAX_N);

	void clear() {
		map.clear();
		result.clear();
		Arrays.fill(indegree, 0);
		for (List<Integer> list : graph)
			list.clear();
	}

	void solve() throws IOException {
		N = rd.nextInt();
		for (int C = 1; !rd.isEOF(); C++) {
			clear();

			for (int i = 1; i <= N; i++)
				map.put(beverages[i] = rd.next(), i);

			M = rd.nextInt();
			for (int v; M-- > 0;) {
				graph.get(map.get(rd.next())).add(v = map.get(rd.next()));
				indegree[v]++;
			}

			kahn();

			StringBuilder sb = new StringBuilder("Case #" + C);
			sb.append(": Dilbert should drink beverages in this order: ");
			for (int u : result)
				sb.append(beverages[u] + ' ');

			sb.setCharAt(sb.length() - 1, '.');
			pw.print(sb.toString());

			N = rd.nextInt();
			if (!rd.isEOF())
				pw.print("\n\n");
		}
		pw.close();
	}

	void kahn() {
		PriorityQueue<Integer> zeroIndegree = new PriorityQueue<>();
		for (int u = 1; u <= N; u++)
			if (indegree[u] == 0)
				zeroIndegree.add(u);

		while (!zeroIndegree.isEmpty()) {
			int u = zeroIndegree.poll();
			result.add(u);
			for (int v : graph.get(u))
				if (--indegree[v] == 0)
					zeroIndegree.add(v);
		}
	}

	public static void main(String[] args) throws IOException {
		new Beverages().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 64;
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

		public String readLine() throws IOException {
			byte[] buf = new byte[lineLength];
			int cnt = 0;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = c;
			}
			if (isWindows && buf[cnt - 1] == 13)
				return new String(buf, 0, cnt - 1);
			return new String(buf, 0, cnt);
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