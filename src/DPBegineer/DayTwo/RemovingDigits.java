package DPBegineer.DayTwo;//package DP;

import java.io.*;
import java.util.Arrays;

public class RemovingDigits {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)))){

            int n = Integer.parseInt(br.readLine().trim());

            if (n <= 9) {
                pw.printf("1\n");
                pw.flush();
                return;
            }

            int[] dp = new int[n+1];
            Arrays.fill(dp, Integer.MAX_VALUE);
            for (int i=0; i<=9; i++) {
                dp[i] = 1;
            }

            for (int i=10; i<=n; i++) {
                int temp = i;
                while (temp > 0) {
                    int digit = temp % 10;
                    temp /= 10;
                    if (digit != 0) {
                        dp[i] = Math.min(dp[i], dp[i - digit] + 1);
                    }
                }
            }

            pw.printf("%d\n", dp[n]);
            pw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}