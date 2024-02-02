package lec10;

import java.io.*;
import java.util.*;

// Minimum Indexed Character GeeksforGeeks
public class MinimumIndexedCharacter {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int T, minPos[] = new int[26];
	String str, patt;

	void solve() throws IOException {
		T = rd.nextInt();
		while (T-- > 0) {

			str = rd.next();
			patt = rd.next();
			Arrays.fill(minPos, -1);

			for (int i = 0, index; i < str.length(); i++) {
				index = str.charAt(i) - 'a';
				if (minPos[index] == -1)
					minPos[index] = i;
			}

			String ans = "No character present";
			int ansIndex = Integer.MAX_VALUE;

			for (int i = 0, minPosi; i < patt.length(); i++) {
				minPosi = minPos[patt.charAt(i) - 'a'];

				if (minPosi != -1 && minPosi < ansIndex) {
					ansIndex = minPosi;
					ans = patt.charAt(i) + "";
				}
			}

			pw.println(ans);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new MinimumIndexedCharacter().solve();
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