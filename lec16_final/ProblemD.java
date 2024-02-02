package lec16_final;

import java.io.*;
import java.util.*;

// Trainsorting UVa
public class ProblemD {
	MyReader rd = new MyReader();
	static PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 2001;
	int n, cars[] = new int[MAX];
	int[] dpA = new int[MAX], dpB = new int[MAX];

	void solve() throws IOException {
		int tc = rd.nextInt(), ans;
		while (tc-- > 0) {

			n = rd.nextInt();
			for (int i = 1; i <= n; i++)
				cars[i] = rd.nextInt();

			Arrays.fill(dpA, 1);
			Arrays.fill(dpB, 1);
			ans = 0;

			for (int i = n; i > 0; i--) {
				for (int j = i + 1; j <= n; j++) {

					if (cars[j] < cars[i])
						dpA[i] = Math.max(dpA[i], dpA[j] + 1);

					if (cars[j] > cars[i])
						dpB[i] = Math.max(dpB[i], dpB[j] + 1);
				}
				ans = Math.max(ans, dpA[i] + dpB[i] - 1);
			}

			pw.println(ans);
		}
	}

	public static void main(String[] args) throws IOException {
		new ProblemD().solve();
		pw.close();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 128;
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

		public char nextPrintableChar() throws IOException {
			do {
				c = read();
			} while (c < 32 || c > 126);
			return (char) c;
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

		public String readLineReverse() throws IOException {
			byte[] buf = new byte[lineLength];
			int ind = lineLength - 1;
			while ((c = read()) != -1) {
				if (c == '\n')
					break;
				buf[ind--] = c;
			}
			if (isWindows && buf[ind + 1] == 13)
				return new String(buf, ind + 2, lineLength - (ind + 2));
			return new String(buf, ind + 1, lineLength - (ind + 1));
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

		public long nextLong() throws IOException {
			long ret = 0;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10L + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (isWindows && c == 13)
				read();
			return neg ? -ret : ret;
		}

		public double nextDouble() throws IOException {
			double ret = 0, div = 1;
			c = read();
			while (c <= ' ')
				c = read();
			boolean neg = (c == '-');
			if (neg)
				c = read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = read()) >= '0' && c <= '9');
			if (c == '.')
				while ((c = read()) >= '0' && c <= '9')
					ret += (c - '0') / (div *= 10);
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