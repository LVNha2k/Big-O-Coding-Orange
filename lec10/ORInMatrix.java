package lec10;

import java.io.*;

// OR in Matrix Codeforces
public class ORInMatrix {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	int m, n;
	int[][] A, B, C;

	void solve() throws IOException {
		B = new int[m = rd.nextInt()][n = rd.nextInt()];
		A = new int[m][n];
		C = new int[m][n];

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				B[i][j] = rd.nextInt();
				A[i][j] = 1;
			}

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (B[i][j] == 0) {
					for (int k = 0; k < m; k++)
						A[k][j] = 0;

					for (int k = 0; k < n; k++)
						A[i][k] = 0;
				}

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {

				for (int k = 0; k < m; k++)
					C[i][j] |= A[k][j];
				for (int k = 0; k < n; k++)
					C[i][j] |= A[i][k];
			}

		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				if (B[i][j] != C[i][j]) {
					pw.print("NO");
					return;
				}

		pw.println("YES");
		for (int[] arr : A) {
			for (int e : arr)
				pw.printf("%d ", e);
			pw.println();
		}
	}

	public static void main(String[] args) throws IOException {
		new ORInMatrix().solve();
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