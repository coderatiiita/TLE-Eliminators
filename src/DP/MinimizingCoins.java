package DP;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class MinimizingCoins {
    public static void main(String[] args) {
        Scanner sc =  new Scanner(System.in);
        int n = sc.nextInt();
        int amt = sc.nextInt();
        int[] coins = new int[n];

        for (int i=0; i<n; i++) {
            coins[i] = sc.nextInt();
        }

        Arrays.sort(coins);

        int[] dp = new int[amt+1];
        Arrays.fill(dp, amt+1);

        dp[0] = 0;

        for (int i=0; i<amt+1; i++) {
            for (int c : coins) {
                if (i-c >= 0) {
                    dp[i] = Math.min(dp[i], 1 + dp[i-c]);
                }
            }
        }

        if (dp[amt] == amt+1) {
            System.out.println("-1");
        }

        System.out.println(dp[amt]);
    }
}
