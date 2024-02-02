package lec15;

// Sum of given range
public class SegmentTree_Sample {
	static int[] a, segtree, lazy;

	static double log2(int num) {
		return Math.log(num) / Math.log(2);
	}

	static void buildTree(int left, int right, int index) {
		if (left == right) {
			segtree[index] = a[left];
			return;
		}

		int mid = (left + right) / 2;
		buildTree(left, mid, 2 * index + 1);
		buildTree(mid + 1, right, 2 * index + 2);

		segtree[index] = segtree[2 * index + 1] + segtree[2 * index + 2];
	}

	static void updateQuery(int pos, int value, int left, int right, int index) {
		if (left > pos || right < pos)
			return;

		if (left == right) {
			a[pos] = value;
			segtree[index] = value;
			return;
		}

		int mid = (left + right) / 2;
		if (pos <= mid)
			updateQuery(pos, value, left, mid, 2 * index + 1);
		else
			updateQuery(pos, value, mid + 1, right, 2 * index + 2);

		segtree[index] = segtree[2 * index + 1] + segtree[2 * index + 2];
	}

	static void updateQueryLazy(int qfrom, int qto, int delta, int left, int right, int index) {
		if (left > right)
			return;

		if (lazy[index] != 0) {
			segtree[index] += lazy[index] * (right - left + 1);
			if (left != right) {
				lazy[2 * index + 1] += lazy[index];
				lazy[2 * index + 2] += lazy[index];
			}
			lazy[index] = 0;
		}

		if (left > qto || right < qfrom)
			return;

		if (left >= qfrom && right <= qto) {
			segtree[index] += delta * (right - left + 1);
			if (left != right) {
				lazy[2 * index + 1] += delta;
				lazy[2 * index + 2] += delta;
			}
			return;
		}

		int mid = (left + right) / 2;
		updateQueryLazy(qfrom, qto, delta, left, mid, 2 * index + 1);
		updateQueryLazy(qfrom, qto, delta, mid + 1, right, 2 * index + 2);

		segtree[index] = segtree[2 * index + 1] + segtree[2 * index + 2];
	}

	static int sumQueryLazy(int qfrom, int qto, int left, int right, int index) {
		if (left > right)
			return 0;

		if (lazy[index] != 0) {
			segtree[index] += lazy[index] * (right - left + 1);
			if (left != right) {
				lazy[2 * index + 1] += lazy[index];
				lazy[2 * index + 2] += lazy[index];
			}
			lazy[index] = 0;
		}

		if (left > qto || right < qfrom)
			return 0;

		if (left >= qfrom && right <= qto)
			return segtree[index];

		int mid = (left + right) / 2;
		return sumQueryLazy(qfrom, qto, left, mid, 2 * index + 1)
				+ sumQueryLazy(qfrom, qto, mid + 1, right, 2 * index + 2);
	}

	public static void main(String[] args) {
		a = new int[] { 5, -7, 9, 0, -2, 8, 3, 6, 4, 1 };
		int n = a.length;
		int h = (int) Math.ceil(log2(n));

		int sizeTree = 2 * h * h - 1; // ~= 4 * n
		segtree = new int[sizeTree];
		lazy = new int[sizeTree];

		buildTree(0, n - 1, 0);

		int qfrom = 3, qto = 8;
		int sum = sumQueryLazy(qfrom, qto, 0, n - 1, 0);
		System.out.printf("Sum of given range: %d\n", sum);

		updateQuery(5, 7, 0, n - 1, 0);

		qfrom = 0;
		qto = n - 1;
		sum = sumQueryLazy(qfrom, qto, 0, n - 1, 0);
		System.out.printf("Sum of given range: %d\n", sum);

		qfrom = 0;
		qto = 6;
		updateQueryLazy(qfrom, qto, -2, 0, n - 1, 0);

		qfrom = 0;
		qto = n - 1;
		sum = sumQueryLazy(qfrom, qto, 0, n - 1, 0);
		System.out.printf("Sum of given range: %d\n", sum);
	}

}