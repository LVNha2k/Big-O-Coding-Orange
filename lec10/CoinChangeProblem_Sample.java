package lec10;

public class CoinChangeProblem_Sample {
	int[] dp;

	int coinChange(int total, int[] coins, int n) {
		dp = new int[total + 1];
		dp[0] = 1;

		for (int i = 0; i < n; i++)
			for (int j = coins[i]; j <= total; j++)
				dp[j] = dp[j] + dp[j - coins[i]];

		return dp[total];
	}
	
	/*
	 * Truy vết
	 * Chọn đồng có giá trị k.
	 * dp[total] = dp[total - k] + 1
	 * 
	 * */

	public static void main(String[] args) {
		CoinChangeProblem_Sample ins = new CoinChangeProblem_Sample();
		int total = 10;
		int[] coins = { 1, 2, 5, 10 };
		int n = coins.length;

		System.out.print(ins.coinChange(total, coins, n));
	}
}