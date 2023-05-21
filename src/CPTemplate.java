import java.io.*;

public class CPTemplate {
    static class Reader {
        private final int BUFFER_SIZE = 1 << 16;
        private final DataInputStream din;
        private final byte[] buffer;
        private int bufferPointer;
        private int bytesRead;

        public Reader() {
            din = new DataInputStream(System.in);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public Reader(String file_name) throws IOException {
            din = new DataInputStream(
                    new FileInputStream(file_name));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }

        public String readLine() throws IOException {
            byte[] buf = new byte[64]; // line length
            int cnt = 0, c;
            while ((c = read()) != -1) {
                if (c == '\n') {
                    if (cnt != 0) {
                        break;
                    }
                    else {
                        continue;
                    }
                }
                buf[cnt++] = (byte)c;
            }
            return new String(buf, 0, cnt);
        }

        public int nextInt() throws IOException {
            int ret = 0;
            byte c = read();
            while (c <= ' ') {
                c = read();
            }
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (neg)
                return -ret;
            return ret;
        }

        public long nextLong() throws IOException {
            long ret = 0;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();
            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');
            if (neg)
                return -ret;
            return ret;
        }

        public double nextDouble() throws IOException {
            double ret = 0, div = 1;
            byte c = read();
            while (c <= ' ')
                c = read();
            boolean neg = (c == '-');
            if (neg)
                c = read();

            do {
                ret = ret * 10 + c - '0';
            } while ((c = read()) >= '0' && c <= '9');

            if (c == '.') {
                while ((c = read()) >= '0' && c <= '9') {
                    ret += (c - '0') / (div *= 10);
                }
            }

            if (neg)
                return -ret;
            return ret;
        }

        private void fillBuffer() throws IOException {
            bytesRead = din.read(buffer, bufferPointer = 0,
                    BUFFER_SIZE);
            if (bytesRead == -1)
                buffer[0] = -1;
        }

        private byte read() throws IOException {
            if (bufferPointer == bytesRead)
                fillBuffer();
            return buffer[bufferPointer++];
        }

        public void close() throws IOException {
            if (din == null)
                return;
            din.close();
        }
    }

    static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
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

    public static void main(String[] args) throws IOException {
        Reader reader = new Reader();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

        int n = reader.nextInt();
        out.println(n);

        out.flush();
        out.close();
    }
}
