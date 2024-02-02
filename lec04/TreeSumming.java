package lec04;

import java.io.*;
import java.util.*;

// Tree Summing UVa
public class TreeSumming {
	MyScanner sc = new MyScanner();
	int sum;
	String exp;

	void solve() throws IOException {
		while (sc.hasNext()) {
			sum = sc.nextInt();

			exp = "";
			int parentheses = 0;
			do {
				String tmp = sc.next();
				exp += tmp;

				for (int i = 0; i < tmp.length(); i++)
					if (tmp.charAt(i) == '(')
						parentheses++;
					else if (tmp.charAt(i) == ')')
						parentheses--;

			} while (parentheses > 0);

			System.out.println(check(1, sum, 0) ? "yes" : "no");
		}
	}

	int getInteger(int ind) {
		char c = exp.charAt(ind);
		boolean neg = (c == '-');
		if (neg)
			c = exp.charAt(++ind);

		int ret = 0;
		do {
			ret = ret * 10 + c - '0';
		} while (Character.isDigit(c = exp.charAt(++ind)));

		return neg ? -ret : ret;
	}

	boolean check(int ind, int target, int current) {
		if (Character.isDigit(exp.charAt(ind)) || exp.charAt(ind) == '-')
			current += getInteger(ind);
		else
			return false;

		int left = leftChild(ind);
		int right = rightChild(ind);

		if (left == -1 && right == -1)
			return target == current;

		boolean leftPath = false, rightPath = false;

		if (left != -1)
			leftPath = check(left, target, current);

		if (right != -1)
			rightPath = check(right, target, current);

		return leftPath || rightPath;
	}

	int leftChild(int ind) {
		char c = exp.charAt(ind);
		while (Character.isDigit(c) || c == '-')
			c = exp.charAt(++ind);

		c = exp.charAt(ind + 1);
		if (Character.isDigit(c) || c == '-')
			return ind + 1;

		return -1;
	}

	int rightChild(int ind) {
		char c = exp.charAt(ind);
		while (Character.isDigit(c) || c == '-')
			c = exp.charAt(++ind);

		int parentheses = 0;
		do {
			c = exp.charAt(ind++);
			if (c == '(')
				parentheses++;
			else if (c == ')')
				parentheses--;

		} while (parentheses > 0);

		c = exp.charAt(ind + 1);
		if (Character.isDigit(c) || c == '-')
			return ind + 1;

		return -1;
	}

	public static void main(String[] args) throws IOException {
		new TreeSumming().solve();
	}

	static class MyScanner {
		final int BUFFER_SIZE = 1 << 16;
		final String FILE = "inp.txt";
		BufferedReader br;
		StringTokenizer st;

		public MyScanner() {
			this(false);
		}

		public MyScanner(boolean file) {
			if (file)
				try {
					br = new BufferedReader(new FileReader(FILE), BUFFER_SIZE);
				} catch (IOException e) {
					e.printStackTrace();
				}
			else
				br = new BufferedReader(new InputStreamReader(System.in), BUFFER_SIZE);
		}

		String nextLine() throws IOException {
			return br.readLine();
		}

		String next() throws IOException {
			while (st == null || !st.hasMoreElements()) {
				String line = br.readLine();
				if (line == null)
					return null;
				st = new StringTokenizer(line);
			}
			return st.nextToken();
		}

		boolean hasNext() throws IOException {
			while (st == null || !st.hasMoreElements()) {
				String line = br.readLine();
				if (line == null)
					return false;
				st = new StringTokenizer(line);
			}
			return st.hasMoreTokens();
		}

		int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		double nextDouble() throws IOException {
			return Double.parseDouble(next());
		}
	}
}