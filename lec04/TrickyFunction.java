package lec04;

import java.io.*;
import java.util.*;

// Tricky Function Codeforces
public class TrickyFunction {
	MyReader rd = new MyReader();
	static final long INF = Integer.MAX_VALUE;
	int n, a[], prefixSum[];
	Point[] points;

	void solve() throws IOException {
		n = rd.nextInt();
		a = new int[n + 1];
		prefixSum = new int[n + 1];

		for (int i = 1; i <= n; i++) {
			a[i] = rd.nextInt();
			prefixSum[i] = prefixSum[i - 1] + a[i];
		}

		points = new Point[n + 1];
		for (int i = 1; i <= n; i++)
			points[i] = new Point(i, prefixSum[i]);

		Arrays.sort(points, 1, n + 1, (p1, p2) -> Integer.compare(p1.x, p2.x));

		System.out.print(closest(1, n));
	}

	long sqr(int num) {
		return 1L * num * num;
	}

	long squareDist(Point p1, Point p2) {
		int x = p1.x - p2.x;
		int y = p1.y - p2.y;
		return sqr(x) + sqr(y);
	}

	// [left, right]
	long closest(int left, int right) {
		if (right - left <= 1)
			return (left == right) ? INF : squareDist(points[left], points[right]);

		int mid = (left + right) / 2;

		long distLeft = closest(left, mid - 1);
		long distRight = closest(mid + 1, right);
		long distMin = Math.min(distLeft, distRight);

		return stripClosest(left, right, mid, distMin);
	}

	long stripClosest(int left, int right, int mid, long distMin) {
		List<Point> splitted = new ArrayList<>();

		for (int i = left; i <= right; i++)
			if (Math.abs(points[i].x - points[mid].x) <= distMin)
				splitted.add(points[i]);

		Collections.sort(splitted, (p1, p2) -> Integer.compare(p1.y, p2.y));

		long smallest = distMin;
		int size = splitted.size();

		for (int i = 0; i < size; i++)
			for (int j = i + 1; j < size && sqr(splitted.get(j).y - splitted.get(i).y) < smallest; j++)
				smallest = Math.min(smallest, squareDist(splitted.get(i), splitted.get(j)));

		return smallest;
	}

	public static void main(String[] args) throws IOException {
		new TrickyFunction().solve();
	}

	class Point {
		int x, y;

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
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