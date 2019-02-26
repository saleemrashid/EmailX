import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public abstract class TransformReader extends LineReader {
    private CharBuffer buf = null;

    public TransformReader(final Reader in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        fill();
        if (buf == null) {
            return -1;
        }

        return buf.get();
    }

    @Override
    public int read(final char[] cbuf, final int off, final int len) throws IOException {
        final CharBuffer target = CharBuffer.wrap(cbuf, off, len);

        return read(target);
    }

    @Override
    public int read(final CharBuffer target) throws IOException {
        while (target.hasRemaining()) {
            fill();
            if (buf == null) {
                break;
            }

            buf.read(target);
        }

        final int n = target.position();

        if (n == 0 && target.hasRemaining()) {
            return -1;
        }

        return n;
    }

    @Override
    public String readLine() throws IOException {
        final String line = super.readLine();

        if (line == null) {
            return null;
        }

        return transform(line);
    }

    protected abstract String transform(String line);

    private void fill() throws IOException {
        if (buf == null || !buf.hasRemaining()) {
            final String line = readLine();

            if (line == null) {
                buf = null;
            } else {
                buf = CharBuffer.wrap(line);
            }
        }
    }
}
