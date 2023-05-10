package DPBegineer.DayOne;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.*;

public class DiceCombinations {

    public static void main(String[] args) {
        int MOD = 1_000_000_007;

        int[] dp = new int[1_000_001];
        Arrays.fill(dp, 0);
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        dp[4] = 8;
        dp[5] = 16;
        dp[6] = 32;

        Scanner sc = new Scanner(in);
        int n = sc.nextInt();

        for (int i=7; i<=n; i++) {
            for (int no=1; no<=6; no++) {
                dp[i] += dp[i - no];
                if (dp[i] >= MOD)
                    dp[i] -= MOD;
            }
        }

        out.println(dp[n]);
    }

}
