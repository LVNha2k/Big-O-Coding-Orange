package lec09;

import java.io.*;
import java.util.*;

// The Monk and Prateek HackerEarth
public class TheMonkAndPrateek {
	MyReader rd = new MyReader();
	int N;
	Map<Integer, Integer> map = new HashMap<>();

	void solve() throws IOException {
		N = rd.nextInt();
		int maxHashValue = -1;

		for (int i = 0, hashValue; i < N; i++) {
			hashValue = f(rd.nextInt());
			map.put(hashValue, map.getOrDefault(hashValue, 0) + 1);
			maxHashValue = Math.max(maxHashValue, hashValue);
		}

		if (map.size() == N) {
			System.out.printf("%d %d", maxHashValue, 0);
			return;
		}

		int maxFreq = 0, minHashValue = Integer.MAX_VALUE, nCollisions = 0;

		for (Map.Entry<Integer, Integer> en : map.entrySet()) {
			maxFreq = Math.max(maxFreq, en.getValue());
			nCollisions += en.getValue() - 1;
		}

		for (Map.Entry<Integer, Integer> en : map.entrySet())
			if (en.getValue() == maxFreq)
				minHashValue = Math.min(minHashValue, en.getKey());

		System.out.printf("%d %d", minHashValue, nCollisions);
	}

	int f(int n) {
		return n ^ sumDigits(n);
	}

	int sumDigits(int n) {
		int sum = 0;
		for (; n > 0; n /= 10)
			sum += n % 10;
		return sum;
	}

	public static void main(String[] args) throws IOException {
		new TheMonkAndPrateek().solve();
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