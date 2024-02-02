package lec11;

import java.util.*;

public class LCS_Sample {
	static final int MAX_M = 101, MAX_N = 101;
	static int[][] dp = new int[MAX_M][MAX_N];

	static int LCS_TopDown(String s1, String s2, int m, int n) {
		if (m == 0 || n == 0)
			return dp[m][n] = 0;

		if (dp[m][n] != -1)
			return dp[m][n];

		if (s1.charAt(m - 1) == s2.charAt(n - 1)) {
			dp[m][n] = 1 + LCS_TopDown(s1, s2, m - 1, n - 1);
			return dp[m][n];
		}

		dp[m][n] = Math.max(LCS_TopDown(s1, s2, m - 1, n), LCS_TopDown(s1, s2, m, n - 1));
		return dp[m][n];
	}

	static int LCS_BottomUp(String s1, String s2, int m, int n) {
		for (int i = 0; i <= m; i++)
			dp[i][0] = 0;
		for (int j = 0; j <= n; j++)
			dp[0][j] = 0;

		for (int i = 1; i <= m; i++)
			for (int j = 1; j <= n; j++)
				if (s1.charAt(i - 1) == s2.charAt(j - 1))
					dp[i][j] = 1 + dp[i - 1][j - 1];
				else
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);

		return dp[m][n];
	}

	static void printLCS(String s1, String s2, int m, int n) {
		int lengthLCS = dp[m][n];
		char[] res = new char[lengthLCS];

		int i = m, j = n;

		while (i > 0 && j > 0)
			if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
				res[--lengthLCS] = s1.charAt(i - 1);
				i--;
				j--;
			}
			else if (dp[i - 1][j] > dp[i][j - 1])
				i--;
			else
				j--;

		System.out.println(res);
	}

	static void diff1(String s1, String s2, int m, int n) {
		List<String> res = new ArrayList<>();
		int i = m, j = n;

		while (i > 0 && j > 0)
			if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
				res.add(s1.charAt(i - 1) + "");
				i--;
				j--;
			}
			else if (dp[i][j - 1] >= dp[i - 1][j])
				res.add("+" + s2.charAt(--j));
			else
				res.add("-" + s1.charAt(--i));

		for (i = res.size() - 1; i >= 0; i--)
			System.out.print(res.get(i) + " ");
	}

	static void diff2(String s1, String s2, int m, int n) {
		if (m > 0 && n > 0 && s1.charAt(m - 1) == s2.charAt(n - 1)) {
			diff2(s1, s2, m - 1, n - 1);
			System.out.printf("%c ", s1.charAt(m - 1));
		}
		else if (n > 0 && (m == 0 || dp[m][n - 1] >= dp[m - 1][n])) {
			diff2(s1, s2, m, n - 1);
			System.out.printf("+%c ", s2.charAt(n - 1));
		}
		else if (m > 0 && (n == 0 || dp[m][n - 1] < dp[m - 1][n])) {
			diff2(s1, s2, m - 1, n);
			System.out.printf("-%c ", s1.charAt(m - 1));
		}
	}

	public static void main(String[] args) {
		String s1 = "ATCJDZEFGY";
		String s2 = "BADCJEFGYT";
		int m = s1.length(), n = s2.length();

		for (int[] row : dp)
			Arrays.fill(row, -1);

		System.out.println(s1);
		System.out.println(s2);
		System.out.printf("Length of LCS is: %d\n", LCS_TopDown(s1, s2, m, n));
		System.out.printf("Length of LCS is: %d\n", LCS_BottomUp(s1, s2, m, n));

		printLCS(s1, s2, m, n);
		System.out.println("———————————————————————");

		s1 = "abcdfghjqz";
		s2 = "abcdefgijkrxyz";
		m = s1.length();
		n = s2.length();

		LCS_BottomUp(s1, s2, m, n);
		System.out.println(s1);
		System.out.println(s2);

		diff1(s1, s2, m, n);
		System.out.println();
		diff2(s1, s2, m, n);
	}

}