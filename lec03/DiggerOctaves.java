package lec03;

import java.io.*;
import java.util.*;

// Digger Octaves UVa
public class DiggerOctaves {
	MyReader rd = new MyReader();
	static final int MAX_N = 8;
	int T, N, trace[] = new int[9];
	int[] dr = { 0, 0, 1, -1 }, dc = { 1, -1, 0, 0 };
	boolean[][] visited = new boolean[MAX_N][MAX_N];
	char[][] map = new char[MAX_N][];
	Set<Long> set = new HashSet<>();

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {
			set.clear();
			N = rd.nextInt();

			for (int i = 0; i < N; i++)
				map[i] = rd.next().toCharArray();

			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					visited[i][j] = (map[i][j] == '.') ? true : false;

			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					if (!visited[i][j]) {
						DFS(1, i, j);
						visited[i][j] = false;
					}

			System.out.println(set.size());
		}
	}

	void DFS(int step, int r, int c) {
		visited[r][c] = true;
		trace[step] = r * N + c;

		if (step == 8) {
			long sum = 0;
			for (int i = 1; i <= 8; i++)
				sum |= (1L << trace[i]);

			set.add(sum);
			return;
		}

		for (int i = 0; i < 4; i++) {
			int tmpr = r + dr[i], tmpc = c + dc[i];

			if (isValid(tmpr, tmpc) && !visited[tmpr][tmpc]) {
				DFS(step + 1, tmpr, tmpc);
				visited[tmpr][tmpc] = false;
			}
		}
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < N && col >= 0 && col < N;
	}

	public static void main(String[] args) throws IOException {
		new DiggerOctaves().solve();
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