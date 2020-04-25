package OUT;

public class InOut {

    private static Console console = new DefaultConsole();

    public static void Write(String format, Object[] args) {
        console.Write(format, args);
    }

    public static String Read() {
        return console.Read();
    }

    public static void SetConsole(Console newConsole) {
        console = newConsole;
    }
    
    
}
