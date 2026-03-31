package ADS.Semester2.Lab1;

import java.io.IOException;
import java.io.InputStream;

public class FastScanner {
    private final InputStream in;
    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0;
    private int len = 0;

    public FastScanner(InputStream is) {
        in = is;
    }

    private int read() throws IOException {
        if (ptr >= len) {
            len = in.read(buffer);
            ptr = 0;
            if (len <= 0) return -1;
        }
        return buffer[ptr++];
    }

    public long nextLong() throws IOException {
        int c;
        do {
            c = read();
        } while (c <= ' ' && c != -1);

        long sign = 1;
        if (c == '-') {
            sign = -1;
            c = read();
        }

        long val = 0;
        while (c > ' ') {
            val = val * 10 + (c - '0');
            c = read();
        }
        return val * sign;
    }

    public int nextInt() throws IOException {
        return (int) nextLong();
    }
}