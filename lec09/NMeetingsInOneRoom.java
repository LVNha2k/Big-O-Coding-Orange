package lec09;

import java.io.*;
import java.util.*;

// N meetings in one room GeeksforGeeks
public class NMeetingsInOneRoom {
	MyReader rd = new MyReader();
	static final int MAX_N = 100;
	int T, N;
	Meeting[] arr = new Meeting[MAX_N];

	void solve() throws IOException {
		for (int i = 0; i < MAX_N; i++)
			arr[i] = new Meeting();

		T = rd.nextInt();
		while (T-- > 0) {
			N = rd.nextInt();

			for (int i = 0; i < N; i++) {
				arr[i].start = rd.nextInt();
				arr[i].ith = i + 1;
			}
			for (int i = 0; i < N; i++)
				arr[i].finish = rd.nextInt();

			Arrays.sort(arr, 0, N);

			int lastFinish = arr[0].finish;
			System.out.print(arr[0].ith);

			for (int i = 1; i < N; i++)
				if (arr[i].start > lastFinish) {
					lastFinish = arr[i].finish;
					System.out.printf(" %d", arr[i].ith);
				}

			System.out.println();
		}
	}

	public static void main(String[] args) throws IOException {
		new NMeetingsInOneRoom().solve();
	}

	class Meeting implements Comparable<Meeting> {
		int start, finish, ith;

		@Override
		public int compareTo(Meeting that) {
			return Integer.compare(this.finish, that.finish);
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