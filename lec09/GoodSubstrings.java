package lec09;

import java.io.*;
import java.util.*;

// Good Substrings Codeforces
public class GoodSubstrings {
	MyReader rd = new MyReader();
	String s, isGood;
	int k;
	Set<Long> set = new HashSet<>();

	void solve() throws IOException {
		s = rd.next();
		isGood = rd.next();
		k = rd.nextInt();

		int base = 29;
		long polyHash;

		for (int i = 0; i < s.length(); i++) {
			polyHash = 0;
			
			for (int j = i, bad = 0; j < s.length(); j++) {
				bad += isGood.charAt(s.charAt(j) - 'a') == '0' ? 1 : 0;
				if (bad > k)
					break;
				
				polyHash = s.charAt(j) - 'a' + 1 + base * polyHash;
				set.add(polyHash);
			}
		}
		
		System.out.print(set.size());
	}

	public static void main(String[] args) throws IOException {
		new GoodSubstrings().solve();
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