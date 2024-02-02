package lec04;

import java.io.*;
import java.util.*;

// Bit Maps UVa
public class BitMaps {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 201, MAX_LEN = 50;
	String type, str, res;
	int H, W, indStr, matB[][] = new int[MAX][MAX];

	void solve() throws IOException {
		String tmp = rd.next();
		while (true) {
			if (tmp.equals("#"))
				break;
			type = tmp;
			H = rd.nextInt();
			W = rd.nextInt();

			str = "";
			while (true) {
				tmp = rd.next();
				if (tmp.equals("B") || tmp.equals("D") || tmp.equals("#"))
					break;
				str += tmp;
			}

			indStr = 0;
			for (int[] arr : matB)
				Arrays.fill(arr, 0);

			// B to D
			if (type.equals("B")) {
				for (int i = 1; i <= H; i++)
					for (int j = 1; j <= W; j++)
						matB[i][j] = str.charAt(indStr++) - '0';

				pw.printf("%c%4d%4d\n", 'D', H, W);
				res = B2D(1, 1, H, W);

				for (int i = 0; i < res.length(); i++) {
					pw.print(res.charAt(i));
					if ((i + 1) % MAX_LEN == 0 || i == res.length() - 1)
						pw.println();
				}
				continue;
			}

			// D to B
			pw.printf("%c%4d%4d\n", 'B', H, W);
			D2B(1, 1, H, W);

			int cnt = 0;
			for (int i = 1; i <= H; i++)
				for (int j = 1; j <= W; j++) {
					pw.print(matB[i][j]);

					if (++cnt % MAX_LEN == 0 || (i == H && j == W))
						pw.println();
				}
		}
		pw.close();
	}

	int sum(int r, int c, int h, int w) {
		int ret = 0;
		for (int i = r; i < r + h; i++)
			for (int j = c; j < c + w; j++)
				ret += matB[i][j];
		return ret;
	}

	String B2D(int r, int c, int h, int w) {
		if (h == 0 || w == 0)
			return "";

		int sum = sum(r, c, h, w);

		if (sum == 0)
			return "0";
		if (sum == h * w)
			return "1";

		String s1 = B2D(r, c, (h + 1) / 2, (w + 1) / 2);
		String s2 = B2D(r, c + (w + 1) / 2, (h + 1) / 2, w / 2);
		String s3 = B2D(r + (h + 1) / 2, c, h / 2, (w + 1) / 2);
		String s4 = B2D(r + (h + 1) / 2, c + (w + 1) / 2, h / 2, w / 2);

		return "D" + s1 + s2 + s3 + s4;
	}

	void D2B(int r, int c, int h, int w) {
		if (h == 0 || w == 0 || indStr == str.length())
			return;

		char ch = str.charAt(indStr++);
		if (ch == '1')
			for (int i = r; i < r + h; i++)
				for (int j = c; j < c + w; j++)
					matB[i][j] = 1;

		else if (ch == 'D') {
			D2B(r, c, (h + 1) / 2, (w + 1) / 2);
			D2B(r, c + (w + 1) / 2, (h + 1) / 2, w / 2);
			D2B(r + (h + 1) / 2, c, h / 2, (w + 1) / 2);
			D2B(r + (h + 1) / 2, c + (w + 1) / 2, h / 2, w / 2);
		}
	}

	public static void main(String[] args) throws IOException {
		new BitMaps().solve();
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