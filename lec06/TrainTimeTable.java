package lec06;

import java.io.*;
import java.util.*;

// Train Time Table SPOJ
@SuppressWarnings("unchecked")
public class TrainTimeTable {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, T, NA, NB, ans[] = new int[2];
	PriorityQueue<Integer>[] heaps = new PriorityQueue[2];
	List<Event> events = new ArrayList<Event>(200);

	void solve() throws IOException {
		heaps[0] = new PriorityQueue<>();
		heaps[1] = new PriorityQueue<>();
		N = rd.nextInt();

		for (int tc = 1, type; tc <= N; tc++) {
			T = rd.nextInt();
			NA = rd.nextInt();
			NB = rd.nextInt();

			events.clear();
			for (PriorityQueue<Integer> h : heaps)
				h.clear();
			Arrays.fill(ans, 0);

			while (NA-- > 0)
				events.add(new Event(rd.nextInt(), rd.nextInt(), rd.nextInt(), rd.nextInt(), 0));
			while (NB-- > 0)
				events.add(new Event(rd.nextInt(), rd.nextInt(), rd.nextInt(), rd.nextInt(), 1));

			Collections.sort(events);

			for (Event e : events) {
				type = e.type;
				if (heaps[type].isEmpty() || heaps[type].peek() > e.start)
					ans[type]++;
				else
					heaps[type].poll();
				heaps[type ^ 1].add(e.end + T);
			}

			pw.printf("Case #%d: %d %d\n", tc, ans[0], ans[1]);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new TrainTimeTable().solve();
	}

	class Event implements Comparable<Event> {
		int start, end, type;

		public Event(int hhs, int mms, int hhe, int mme, int type) {
			start = hhs * 60 + mms;
			end = hhe * 60 + mme;
			this.type = type;
		}

		@Override
		public int compareTo(Event that) {
			return Integer.compare(this.start, that.start);
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