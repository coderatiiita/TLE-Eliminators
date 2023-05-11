package Week1_DPBegineer.DayOne;

import java.util.Arrays;
import java.util.Scanner;

public class CoinCombinationsI {

    public static void main(String[] args) {
        int MOD = 1_000_000_007;
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int amt = sc.nextInt();

        int[] coins = new int[n];
        for (int i=0; i<n; i++) {
            coins[i] = sc.nextInt();
        }

        Arrays.sort(coins);

        int[] dp = new int[1_000_001];
        dp[0] = 1;

        for (int i=1; i<=amt; i++) {
            for (int j=0; j<n; j++) {
                if (i-coins[j] >= 0) {
                    dp[i] = dp[i] + dp[i-coins[j]];
                    if (dp[i] >= MOD)
                        dp[i] -= MOD;
                } else break;
            }
        }

        System.out.println(dp[amt]);
    }

}
