package DPBegineer;

import java.io.*;

public class CoinCombinationsII {
    public static void main(String[] args) {
        int MOD = 1_000_000_007;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)))){

            String[] tokens = br.readLine().split(" ");
            int n = Integer.parseInt(tokens[0]);
            int amt = Integer.parseInt(tokens[1]);

            int[] coins = new int[n];
            tokens = br.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                coins[i] = Integer.parseInt(tokens[i]);
            }

            int[] dp = new int[1_000_001];
            dp[0] = 1;
            for (int j = 0; j < n; j++) {
                for (int i = 0; i <= amt; i++) {
                    if (i - coins[j] >= 0) {
                        dp[i] += dp[i - coins[j]];
                        if (dp[i] >= MOD)
                            dp[i] -= MOD;
                    }
                }
            }

            pw.printf("%d", dp[amt]);
            pw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}