package lec12;

import java.io.*;
import java.util.*;

// Prince And Princess UVa
public class PrinceAndPrincess {
	MyReader rd = new MyReader();
	static final int MAX = 250;
	int t, n, p, q;
	int[] pos = new int[MAX * MAX + 1];
	List<Integer> a = new ArrayList<>();

	void solve() throws IOException {
		t = rd.nextInt();
		for (int tc = 1; tc <= t; tc++) {
			n = rd.nextInt();
			p = rd.nextInt();
			q = rd.nextInt();

			Arrays.fill(pos, -1);
			a.clear();

			for (int i = 0, x; i <= p; i++) {
				x = rd.nextInt();
				pos[x] = i;
			}
			for (int i = 0, y; i <= q; i++) {
				y = rd.nextInt();
				if (pos[y] >= 0)
					a.add(pos[y]);
			}

			System.out.printf("Case %d: %d\n", tc, LIS());
		}
	}

	int LIS() {
		List<Integer> sub = new ArrayList<>();
		sub.add(a.get(0));

		for (int i = 1; i < a.size(); i++)
			if (a.get(i) < sub.get(0))
				sub.set(0, a.get(i));

			else if (a.get(i) > sub.get(sub.size() - 1))
				sub.add(a.get(i));

			else {
				int pos = lowerBound(sub, a.get(i));
				sub.set(pos, a.get(i));
			}

		return sub.size();
	}

	// [ )
	int lowerBound(List<Integer> list, int value) {
		int left = 0, right = list.size(), pos = right, mid;

		while (left < right) {
			mid = (left + right) / 2;

			if (list.get(mid) >= value)
				pos = right = mid;
			else
				left = mid + 1;
		}
		return pos;
	}

	public static void main(String[] args) throws IOException {
		new PrinceAndPrincess().solve();
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