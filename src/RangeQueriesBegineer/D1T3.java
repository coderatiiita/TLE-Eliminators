package RangeQueriesBegineer;

import java.io.*;
import java.util.StringTokenizer;

public class D1T3 {
  public static void main(String[] args) {
    FastReader in = new FastReader();
    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    // Read input and solve the problem
    // Example: Read an integer and print it
    int n = in.nextInt();
    int m = in.nextInt();
    long[] arr = new long[n];
    for (int i = 0; i < n; i++) {
      arr[i] = in.nextLong();
    }
    SegmentTree st = new SegmentTree(arr);

    while (m-- > 0) {
      int op = in.nextInt();
      if (op == 1) {
        int pos = in.nextInt();
        long newVal = in.nextLong();
        st.update(pos, newVal);
      } else {
        int l = in.nextInt();
        int r = in.nextInt();
        Pair ans = st.query(l, r - 1);
        out.printf("%d %d\n", ans.min, ans.count);
      }
    }

    // Flush and close the output PrintWriter
    out.flush();
    out.close();
  }

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

  static class SegmentTree {
    private Pair[] tree;
    private long[] arr;
    private int n;

    public SegmentTree(long[] arr) {
      this.arr = arr;
      this.n = arr.length;
      this.tree = new Pair[4 * n];
      build(1, 0, n - 1);
    }

    private void build(int v, int tl, int tr) {
      if (tl == tr) {
        tree[v] = new Pair(arr[tl], 1);
      } else {
        int tm = (tl + tr) / 2;
        build(2 * v, tl, tm);
        build(2 * v + 1, tm + 1, tr);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
      }
    }

    public Pair query(int l, int r) {
      return query(1, 0, n - 1, l, r);
    }

    private Pair query(int v, int tl, int tr, int l, int r) {
      if (l > r) return new Pair(Integer.MAX_VALUE, 0);
      if (l == tl && r == tr) return tree[v];
      int tm = (tl + tr) / 2;
      return merge(
          query(2 * v, tl, tm, l, Math.min(r, tm)),
          query(2 * v + 1, tm + 1, tr, Math.max(l, tm + 1), r));
    }

    public void update(int pos, long newVal) {
      update(1, 0, n - 1, pos, newVal);
    }

    private void update(int v, int tl, int tr, int pos, long newVal) {
      if (tl == tr) {
        tree[v] = new Pair(newVal, 1);
      } else {
        int tm = (tl + tr) / 2;
        if (pos <= tm) update(2 * v, tl, tm, pos, newVal);
        else update(2 * v + 1, tm + 1, tr, pos, newVal);
        tree[v] = merge(tree[2 * v], tree[2 * v + 1]);
      }
    }

    private Pair merge(Pair a, Pair b) {
      if (a.min < b.min) return new Pair(a.min, a.count);
      if (a.min > b.min) return new Pair(b.min, b.count);
      return new Pair(a.min, a.count + b.count);
    }
  }

  static class Pair {
    long min;
    long count;

    Pair(long min, long count) {
      this.min = min;
      this.count = count;
    }
  }
}
