package lec13;

public class MultipleKnapsack_Sapmle {
	static class Item {
		int profit, weight;

		public Item(int p, int w) {
			profit = p;
			weight = w;
		}
	}

	static int[] dp;

	static int unboundedKnapsack(Item[] items, int W) {
		dp = new int[W + 1];

		for (int i = 0; i <= W; i++)
			for (Item item : items)
				if (item.weight <= i)
					dp[i] = Math.max(dp[i], item.profit + dp[i - item.weight]);

		return dp[W];
	}

	public static void main(String[] args) {
		Item[] items = new Item[5];
		items[0] = new Item(1, 1);
		items[1] = new Item(2, 1);
		items[2] = new Item(2, 2);
		items[3] = new Item(4, 6);
		items[4] = new Item(10, 4);

		int W = 10;
		int res = unboundedKnapsack(items, W);
		System.out.println("Maximum value: " + res);
		printItems(items, W);
	}

	static void printItems(Item[] items, int W) {
		// TODO
	}
}