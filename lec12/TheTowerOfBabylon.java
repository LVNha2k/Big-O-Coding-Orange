package lec12;

import java.io.*;
import java.util.*;

// The Tower of Babylon UVa
public class TheTowerOfBabylon {
	MyReader rd = new MyReader();
	PrintWriter pw = new PrintWriter(System.out);
	static final int MAX = 30;
	int n, dp[] = new int[6 * MAX];
	List<Block> blocks = new ArrayList<>(6 * MAX);

	void solve() throws IOException {
		int tc = 1;
		while ((n = rd.nextInt()) != 0) {
			blocks.clear();

			for (int i = 0, x, y, z; i < n; i++) {
				blocks.add(new Block(x = rd.nextInt(), y = rd.nextInt(), z = rd.nextInt()));
				blocks.add(new Block(x, z, y));
				blocks.add(new Block(y, x, z));
				blocks.add(new Block(y, z, x));
				blocks.add(new Block(z, x, y));
				blocks.add(new Block(z, y, x));
			}

			Collections.sort(blocks);
			int ans = 0;

			for (int i = 0; i < blocks.size(); i++) {
				dp[i] = blocks.get(i).z;

				for (int j = 0; j < i; j++)
					if (blocks.get(i).x < blocks.get(j).x && blocks.get(i).y < blocks.get(j).y)
						dp[i] = Math.max(dp[i], dp[j] + blocks.get(i).z);

				ans = Math.max(ans, dp[i]);
			}

			pw.printf("Case %d: maximum height = %d\n", tc++, ans);
		}
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		new TheTowerOfBabylon().solve();
	}

	class Block implements Comparable<Block> {
		int x, y, z;

		public Block(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public int compareTo(Block that) {
			return Integer.compare(that.x, this.x);
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