package OUT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public abstract class Console {

    public abstract void Write(String format, Object[] args);

    public abstract String Read();
}

class DefaultConsole extends Console {

    PrintWriter writer;
    
    public DefaultConsole() {
        writer = new PrintWriter(System.out, true);
    }

    public DefaultConsole(PrintWriter writer) {
        this.writer = writer;
    }

    public void Write(String format, Object[] args) {
        writer.println(String.format(format, args));
    }

    public String Read() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            return br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }
}
