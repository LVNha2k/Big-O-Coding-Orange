package lec01;

import java.io.*;
import java.util.*;

// King's Path Codeforces
public class KingsPath {
	MyReader rd = new MyReader();
	int x0, y0, x1, y1, n;
	int[] dr = { 1, 1, 1, 0, 0, -1, -1, -1 };
	int[] dc = { 1, 0, -1, 1, -1, 1, 0, -1 };
	Set<Pair> visited = new HashSet<>();
	Set<Pair> allow = new HashSet<>();

	void solve() throws IOException {
		x0 = rd.nextInt();
		y0 = rd.nextInt();
		x1 = rd.nextInt();
		y1 = rd.nextInt();
		n = rd.nextInt();
		for (int r, a, b; n-- > 0;) {
			r = rd.nextInt();
			a = rd.nextInt();
			b = rd.nextInt();
			for (int c = a; c <= b; c++)
				allow.add(new Pair(r, c));
		}

		System.out.print(BFS());
	}

	int BFS() {
		Pair s = new Pair(x0, y0), pu, pv;
		Queue<Pair> queue = new LinkedList<>();
		queue.add(s);
		visited.add(s);

		while (!queue.isEmpty()) {
			pu = queue.poll();
			int r = pu.x, c = pu.y;

			for (int i = 0; i < 8; i++) {
				pv = new Pair(r + dr[i], c + dc[i]);

				if (allow.contains(pv) && !visited.contains(pv)) {
					visited.add(pv);
					pv.dist = pu.dist + 1;
					queue.add(pv);

					if (pv.x == x1 && pv.y == y1)
						return pv.dist;
				}
			}
		}

		return -1;
	}

	public static void main(String[] args) throws IOException {
		new KingsPath().solve();
	}

	class Pair {
		int x, y, dist;

		public Pair(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int hashCode() {
			return 997 * x + y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this)
				return true;
			Pair that = (Pair) obj;
			return this.x == that.x && this.y == that.y;
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