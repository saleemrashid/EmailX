import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class LineReader extends FilterReader {
    private static final int defaultBufferSize = 0x800;

    private final CharBuffer buf;

    public LineReader(final Reader in) {
        this(in, defaultBufferSize);
    }

    public LineReader(final Reader in, final int sz) {
        super(in);

        buf = CharBuffer.allocate(sz);
        buf.limit(0);
    }

    public String readLine() throws IOException {
        final StringBuilder line = new StringBuilder();

        boolean eol = false;
        boolean expectLF = false;

        while (!eol && fill() != -1) {
            if (expectLF) {
                if (buf.hasRemaining()) {
                    buf.mark();
                    final char ch = buf.get();
                    if (ch == '\n') {
                        line.append(ch);
                    } else {
                        buf.reset();
                    }
                }

                break;
            }

            while (buf.hasRemaining()) {
                final char ch = buf.get();
                line.append(ch);

                if (ch == '\n') {
                    eol = true;
                    break;
                } else if (ch == '\r') {
                    expectLF = true;
                    break;
                }
            }
        }

        if (line.length() == 0) {
            return null;
        }

        return line.toString();
    }

    private int fill() throws IOException {
        if (!buf.hasRemaining()) {
            buf.clear();
            final int n = in.read(buf);
            buf.flip();

            return n;
        }

        return 0;
    }
}
