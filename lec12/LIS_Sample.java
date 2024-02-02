package lec12;

import java.util.*;

public class LIS_Sample {
	static int last, dp[], path[];

	static int LIS(int[] a) {
		int length = 0, n = a.length;
		dp = new int[n];
		Arrays.fill(dp, 1);

//		path = new int[n];
//		Arrays.fill(path, -1);

		for (int i = 1; i < n; i++)
			for (int j = 0; j < i; j++)
				if (a[i] > a[j] && dp[i] < dp[j] + 1) {
					dp[i] = dp[j] + 1;
//					path[i] = j;
				}

		for (int i = 0; i < n; i++)
			if (length < dp[i]) {
				length = dp[i];
				last = i;
			}

		return length;
	}

	static void printLIS(int[] a) {
		List<Integer> res = new ArrayList<>();
		int pos = last;
		res.add(a[pos]);

		for (int i = pos - 1; i >= 0; i--)
			if (a[i] < a[pos] && dp[i] + 1 == dp[pos]) {
				pos = i;
				res.add(a[i]);
			}

		for (int i = res.size() - 1; i >= 0; i--)
			System.out.print(res.get(i) + " ");
	}

	static int LIS_BS(int[] a) {
		int len = 1, n = a.length;
		dp = new int[n];
		dp[0] = 0;

		path = new int[n];
		Arrays.fill(path, -1);

		for (int i = 1; i < n; i++)
			if (a[i] <= a[dp[0]])
				dp[0] = i;

			else if (a[i] > a[dp[len - 1]]) {
				path[i] = dp[len - 1];
				dp[len++] = i;
			} else {
				int pos = lowerBound(a, dp, len, a[i]);
				dp[pos] = i;
				path[i] = dp[pos - 1];
			}

		last = dp[len - 1];
		return len;
	}

	// [ )
	static int lowerBound(int[] a, int[] sub, int n, int value) {
		int left = 0, right = n, pos = right, mid, index;

		while (left < right) {
			mid = left + (right - left) / 2;
			index = sub[mid];

			if (a[index] >= value)
				pos = right = mid;
			else
				left = mid + 1;
		}
		return pos;
	}

	static void printLIS_BS(int[] a) {
		List<Integer> res = new ArrayList<>();
		int pre = last;

		while (pre >= 0) {
			res.add(a[pre]);
			pre = path[pre];
		}
		
		for (int i = res.size() - 1; i >= 0; i--)
			System.out.print(res.get(i) + " ");
	}

	public static void main(String[] args) {
		int[] a = { 2, 5, 12, 3, 10, 6, 8, 14, 4, 11, 7, 15 };
		int length = LIS(a);

		System.out.printf("Length of LIS is: %d\n", length);
		printLIS(a);
		System.out.println();

		length = LIS_BS(a);
		System.out.printf("Length of LIS is: %d\n", length);
		printLIS_BS(a);
		System.out.println();

	}

}