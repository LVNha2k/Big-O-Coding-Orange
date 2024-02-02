package lec11;

import java.io.*;
import java.util.*;

// Cross-country SPOJ
public class Cross_Country {
	MyReader rd = new MyReader();
	static final int MAX = 2001;
	int d;
	int[][] dp = new int[MAX][MAX];
	List<Integer> agness = new ArrayList<>();
	List<List<Integer>> tomRoutes = new ArrayList<>();

	void solve() throws IOException {
		d = rd.nextInt();
		while (d-- > 0) {
			agness.clear();
			agness.add(-1);

			int point;
			while ((point = rd.nextInt()) != 0)
				agness.add(point);

			tomRoutes.clear();
			while (true) {
				List<Integer> route = new ArrayList<>();
				route.add(-1);

				while ((point = rd.nextInt()) != 0)
					route.add(point);

				if (route.size() == 1)
					break;
				tomRoutes.add(route);
			}

			int ans = 0;
			for (List<Integer> tom : tomRoutes)
				ans = Math.max(ans, LCS(agness, tom));

			System.out.println(ans);
		}
	}

	int LCS(List<Integer> agness, List<Integer> tom) {
		int n = agness.size() - 1, m = tom.size() - 1;
		for (int i = 0; i <= n; i++)
			dp[i][0] = 0;
		for (int j = 0; j <= m; j++)
			dp[0][j] = 0;

		for (int i = 1; i <= n; i++)
			for (int j = 1; j <= m; j++)
				if (agness.get(i).equals(tom.get(j)))
					dp[i][j] = 1 + dp[i - 1][j - 1];
				else
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

		return dp[n][m];
	}

	public static void main(String[] args) throws IOException {
		new Cross_Country().solve();
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