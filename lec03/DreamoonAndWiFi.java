package lec03;

import java.io.*;

// Dreamoon and WiFi Codeforces
public class DreamoonAndWiFi {
	MyReader rd = new MyReader();
	String s1, s2;
	int finish, nCorrect, nPermutation;

	void solve() throws IOException {
		s1 = rd.next();
		s2 = rd.next();

		int nUnrecognized = 0, endPos = 0;

		for (int i = 0; i < s1.length(); i++) {
			finish += (s1.charAt(i) == '+') ? 1 : -1;

			char ch = s2.charAt(i);
			if (ch == '+')
				endPos++;
			else if (ch == '-')
				endPos--;
			else
				nUnrecognized++;
		}

		if (nUnrecognized == 0) {
			System.out.print(endPos == finish ? 1d : 0d);
			return;
		}

		backtrack(nUnrecognized, endPos);
		System.out.print(nCorrect * 1.0 / nPermutation);
	}

	void backtrack(int nUnrecognized, int endPos) {
		if (nUnrecognized == 0) {
			nPermutation++;
			if (endPos == finish)
				nCorrect++;
			return;
		}

		backtrack(nUnrecognized - 1, endPos + 1);
		backtrack(nUnrecognized - 1, endPos - 1);
	}

	public static void main(String[] args) throws IOException {
		new DreamoonAndWiFi().solve();
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