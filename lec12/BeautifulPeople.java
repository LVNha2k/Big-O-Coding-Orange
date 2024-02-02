package lec12;

import java.io.*;
import java.util.*;

// Beautiful People Codeforces
public class BeautifulPeople {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	int N, path[], dp[];
	Member[] a;

	void solve() throws IOException {
		N = rd.nextInt();
		a = new Member[N + 1];
		for (int i = 1; i <= N; i++)
			a[i] = new Member(rd.nextInt(), rd.nextInt(), i);

		Arrays.sort(a, 1, a.length);

		dp = new int[N + 1];
		path = new int[N + 1];
		Arrays.fill(path, -1);

		dp[1] = 1;
		int len = 1;

		for (int i = 2; i <= N; i++)
			if (a[i].B > a[dp[len]].B) {
				len++;
				dp[len] = i;
				path[i] = dp[len - 1];
			} else {
				int pos = lowerBound(a, dp, len, a[i].B);
				dp[pos] = i;
				path[i] = dp[pos - 1];
			}

		List<Integer> res = new ArrayList<>(len);
		int pre = dp[len];

		while (pre > 0) {
			res.add(a[pre].ith);
			pre = path[pre];
		}

		pw.println(len);

		for (int i = len - 1; i >= 0; i--)
			pw.print(res.get(i) + " ");

		pw.close();
	}

	// [ ]
	int lowerBound(Member[] a, int[] sub, int n, int value) {
		int left = 1, right = n, pos = n, mid, index;

		while (left <= right) {
			mid = (left + right) / 2;
			index = sub[mid];

			if (a[index].B >= value) {
				pos = mid;
				right = mid - 1;
			} else
				left = mid + 1;
		}
		return pos;
	}

	public static void main(String[] args) throws IOException {
		new BeautifulPeople().solve();
	}

	class Member implements Comparable<Member> {
		int S, B, ith;

		public Member(int s, int b, int ith) {
			S = s;
			B = b;
			this.ith = ith;
		}

		@Override
		public int compareTo(Member that) {
			int c = Integer.compare(this.S, that.S);
			return c != 0 ? c : Integer.compare(that.B, this.B);
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