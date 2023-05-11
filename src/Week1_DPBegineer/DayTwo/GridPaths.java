package Week1_DPBegineer.DayTwo;

import java.io.*;

public class GridPaths {
    public static void main(String[] args) {
        int MOD = 1_000_000_007;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)))){

            int n = Integer.parseInt(br.readLine());

            char[][] grid = new char[n][n];

            for (int i=0; i<n; i++) {
                String row = br.readLine();
                for(int j=0; j<n; j++) {
                    grid[i][j] = row.charAt(j);
                }
            }

            if (grid[n-1][n-1] == '*') {
                pw.printf("%d", 0);
                pw.flush();
                return;
            } else if (n == 1) {
                pw.printf("%d", 1);
                pw.flush();
                return;
            }

            int[][] dp = new int[n][n];

            dp[n-1][n-2] = 1;
            dp[n-2][n-1] = 1;

            for (int i=n-1; i>=0; i--) {
                for (int j=n-1; j>=0; j--) {
                    if (grid[i][j] == '*')
                        continue;
                    if (i+1 < n && grid[i+1][j] == '.')
                        dp[i][j] += dp[i+1][j];
                    if (dp[i][j] >= MOD)
                        dp[i][j] -= MOD;
                    if (j+1 < n && grid[i][j+1] == '.')
                        dp[i][j] += dp[i][j+1];
                    if (dp[i][j] >= MOD)
                        dp[i][j] -= MOD;
                }
            }

            pw.printf("%d", dp[0][0]);
            pw.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}