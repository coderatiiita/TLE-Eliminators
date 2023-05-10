package RangeQueriesBegineer.DayTwo;

// https://codeforces.com/edu/course/2/lesson/4/2/practice/contest/273278/problem/A
import java.io.*;
import java.util.StringTokenizer;

public class D2T1 {

    // Fast I/O class for reading input
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) {
        FastReader in = new FastReader();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = in.nextInt();
        int m = in.nextInt();
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.nextLong();
        }
        SegmentTree st = new SegmentTree(arr);

        while (m-- > 0) {
            long idx = in.nextInt();
            long val = in.nextLong();
            long beforeOpMax = maxSegmentSum(n, st);
            out.printf("%d\n", beforeOpMax);
            st.update(idx, idx, val);
        }
        out.println(maxSegmentSum(n, st));

        // Flush and close the output PrintWriter
        out.flush();
        out.close();
    }

    public static long maxSegmentSum(int n, SegmentTree st) {
        long maxSum = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                maxSum = Math.max(maxSum, st.query(i, j));
            }
        }
        return maxSum;
    }

    static class SegmentTree {
        private long[] tree;
        private long[] arr;
        private long[] lazy;
        private int n;

        public SegmentTree(long[] arr) {
            this.arr = arr;
            this.n = arr.length;
            this.tree = new long[4 * n];
            this.lazy = new long[4 * n];
            build(1, 0, n - 1);
        }

        private void build(int v, int tl, int tr) {
            if (tl == tr) {
                tree[v] = arr[tl];
            } else {
                int tm = (tl + tr) / 2;
                build(2 * v, tl, tm);
                build(2 * v + 1, tm + 1, tr);
                tree[v] = tree[2 * v] + tree[2 * v + 1];
            }
        }

        public long query(int l, int r) {
            return query(1, 0, n - 1, l, r);
        }

        private long query(int v, int tl, int tr, int l, int r) {
            if (l > r) {
                return 0;
            }
            if (lazy[v] != 0) {
                tree[v] += (tr - tl + 1) * lazy[v];
                if (tl != tr) {
                    lazy[2 * v] += lazy[v];
                    lazy[2 * v + 1] += lazy[v];
                }
                lazy[v] = 0;
            }
            if (l == tl && r == tr) {
                return tree[v];
            }
            int tm = (tl + tr) / 2;
            return query(2 * v, tl, tm, l, Math.min(r, tm))
                    + query(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r);
        }

        public void update(long l, long r, long newVal) {
            update(1, 0, n - 1, l, r, newVal);
        }

        private void update(int v, int tl, int tr, long l, long r, long newVal) {
            if (lazy[v] != 0) {
                tree[v] += (tr - tl + 1) * lazy[v];
                if (tl != tr) {
                    lazy[2 * v] += lazy[v];
                    lazy[2 * v + 1] += lazy[v];
                }
                lazy[v] = 0;
            }
            if (l > r) {
                return;
            }
            if (l == tl && r == tr) {
                tree[v] = (tr - tl + 1) * newVal;
                if (tl != tr) {
                    lazy[2 * v] += newVal;
                    lazy[2 * v + 1] += newVal;
                }
            } else {
                int tm = (tl + tr) / 2;
                update(2 * v, tl, tm, l, Math.min(r, tm), newVal);
                update(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r, newVal);
                tree[v] = tree[2 * v] + tree[2 * v + 1];
            }
        }
    }
}
