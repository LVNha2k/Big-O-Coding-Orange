package lec05;

import java.io.*;
import java.util.*;

// Making Jumps SPOJ
public class MakingJumps {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX_N = 10;
	int n;
	boolean[][] graph = new boolean[MAX_N][MAX_N];
	int[] dr = { -2, -2, -1, -1, 1, 1, 2, 2 };
	int[] dc = { -1, 1, -2, 2, -2, 2, -1, 1 };

	void solve() throws IOException {
		for (int tc = 1; (n = rd.nextInt()) != 0; tc++) {
			for (boolean[] arr : graph)
				Arrays.fill(arr, false);

			int total = 0, sCol = -1;

			for (int row = 0; row < n; row++) {
				int begin = rd.nextInt(), len = rd.nextInt();
				if (row == 0)
					sCol = begin;

				total += len;
				for (int i = 0; i < len; i++)
					graph[row][begin + i] = true;
			}

			int ans = total - dfs(0, sCol);

			pw.printf("Case %d, %d square%s can not be reached.\n", tc, ans, ans == 1 ? "" : "s");
		}
		pw.close();
	}

	int dfs(int row, int col) {
		int path = 0;
		graph[row][col] = false;

		for (int i = 0; i < 8; i++) {
			int tmpr = row + dr[i], tmpc = col + dc[i];

			if (isValid(tmpr, tmpc) && graph[tmpr][tmpc])
				path = Math.max(path, dfs(tmpr, tmpc));
		}

		graph[row][col] = true;
		return path + 1;
	}

	boolean isValid(int row, int col) {
		return row >= 0 && row < MAX_N && col >= 0 && col < MAX_N;
	}

	public static void main(String[] args) throws IOException {
		new MakingJumps().solve();
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