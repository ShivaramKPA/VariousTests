//http://stackoverflow.com/questions/1522444/how-to-redirect-all-console-output-to-a-swing-jtextarea-jtextpane-with-the-right
package varioustests;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Reader;

public class MyOutputStream extends OutputStream {

private PipedOutputStream out = new PipedOutputStream();
private Reader reader;

public MyOutputStream() throws IOException {
    PipedInputStream in = new PipedInputStream(out);
    reader = new InputStreamReader(in, "UTF-8");
}

public void write(int i) throws IOException {
    out.write(i);
}

public void write(byte[] bytes, int i, int i1) throws IOException {
    out.write(bytes, i, i1);
}

public void flush() throws IOException {
    if (reader.ready()) {
        char[] chars = new char[1024];
        int n = reader.read(chars);

        // this is your text
        String txt = new String(chars, 0, n);

        // write to System.err in this example
        System.err.print(txt);
    }
}

public static void main(String[] args) throws IOException {

    PrintStream out = new PrintStream(new MyOutputStream(), true, "UTF-8");

    System.setOut(out);

    System.out.println("café résumé voilà");

}

}