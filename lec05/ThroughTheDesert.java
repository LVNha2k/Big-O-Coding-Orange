package lec05;

import java.io.*;

// Through the Desert UVa
public class ThroughTheDesert {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int n, pivot, prevPivot, leakCount;
	double total, tank;

	void solve() throws IOException {
		for (String event;;) {
			pivot = rd.nextInt();

			total += (n / 100.0 + leakCount) * (pivot - prevPivot);
			tank = Math.max(tank, total);
			prevPivot = pivot;

			event = rd.next();
			if (event.startsWith("F")) { // Fuel consumption
				rd.next();
				n = rd.nextInt();
				if (n == 0)
					break;

			} else if (event.startsWith("L")) // Leak
				leakCount++;

			else if (event.startsWith("M")) // Mechanic
				leakCount = 0;

			else if (event.startsWith("Ga")) { // Gas station
				rd.next();
				total = 0;

			} else { // Goal
				pw.printf("%.3f\n", tank);
				total = tank = n = prevPivot = leakCount = 0;
			}
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new ThroughTheDesert().solve();
	}

	static class MyReader {
		final int BUFFER_SIZE = 1 << 16;
		BufferedInputStream bin;
		byte buffer[], c;
		int bufferPointer, bytesRead, lineLength = 64;
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