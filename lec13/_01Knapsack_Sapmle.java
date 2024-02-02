package lec13;

public class _01Knapsack_Sapmle {
	static class Item {
		int profit, weight;

		public Item(int p, int w) {
			profit = p;
			weight = w;
		}
	}

	static int[][] dp;

	static int knapsack(Item[] items, int W) {
		dp = new int[items.length][W + 1];

		for (int i = 1; i < items.length; i++)
			for (int j = 0; j <= W; j++)

				if (items[i].weight > j)
					dp[i][j] = dp[i - 1][j];

				else {
					int p1 = items[i].profit + dp[i - 1][j - items[i].weight];
					int p2 = dp[i - 1][j];
					dp[i][j] = Math.max(p1, p2);
				}

		return dp[items.length - 1][W];
	}

	public static void main(String[] args) {
		Item[] items = new Item[6];
		items[0] = new Item(0, 0);
		items[1] = new Item(1, 1);
		items[2] = new Item(2, 1);
		items[3] = new Item(2, 2);
		items[4] = new Item(4, 6);
		items[5] = new Item(10, 4);

		int W = 10;
		int res = knapsack(items, W);
		System.out.println("Total value: " + res);
		printItems(items, W);
	}

	static void printItems(Item[] items, int W) {
		System.out.println("List items:");

		for (int i = items.length - 1; i > 0; i--)
			if (dp[i][W] != dp[i - 1][W]) {

				System.out.printf("[%d, %d]\n", items[i].profit, items[i].weight);
				W -= items[i].weight;
			}
	}
}