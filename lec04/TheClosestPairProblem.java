package lec04;

import java.io.*;
import java.util.*;

// The Closest Pair Problem UVa
public class TheClosestPairProblem {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final double INF = 1e9;
	List<Point> points = new ArrayList<>(10000);
	int N;

	void solve() throws IOException {
		while ((N = rd.nextInt()) != 0) {
			points.clear();

			for (int i = 0; i < N; i++)
				points.add(new Point(rd.nextDouble(), rd.nextDouble()));

			Collections.sort(points, (p1, p2) -> Double.compare(p1.x, p2.x));

			double ans = closest(0, N);

			if (ans < 10000)
				pw.printf("%.4f\n", ans);
			else
				pw.println("INFINITY");
		}
		pw.close();
	}

	double dist(Point p1, Point p2) {
		double x = p1.x - p2.x;
		double y = p1.y - p2.y;
		return Math.sqrt(x * x + y * y);
	}

	// [left, right)
	double closest(int left, int right) {
		if (right - left <= 2)
			return (left == right - 1) ? INF : dist(points.get(left), points.get(right - 1));

		int mid = (left + right) / 2;

		double distLeft = closest(left, mid);
		double distRight = closest(mid + 1, right);
		double distMin = Math.min(distLeft, distRight);

		return stripClosest(left, right, mid, distMin);
	}

	double stripClosest(int left, int right, int mid, double distMin) {
		List<Point> splitted = new ArrayList<>();

		for (int i = left; i < right; i++)
			if (Math.abs(points.get(i).x - points.get(mid).x) <= distMin)
				splitted.add(points.get(i));

		Collections.sort(splitted, (p1, p2) -> Double.compare(p1.y, p2.y));

		double smallest = distMin;
		int size = splitted.size();

		for (int i = 0; i < size; i++)
			for (int j = i + 1; j < size && (splitted.get(j).y - splitted.get(i).y) < smallest; j++)
				smallest = Math.min(smallest, dist(splitted.get(i), splitted.get(j)));

		return smallest;
	}

	public static void main(String[] args) throws IOException {
		new TheClosestPairProblem().solve();
	}

	class Point {
		double x, y;

		public Point(double x, double y) {
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