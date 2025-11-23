package utils;
import java.util.regex.Pattern;

public class Validador {
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public static boolean texto(String t) { return t != null && !t.trim().isEmpty(); }
    public static boolean email(String e) { return e != null && EMAIL_REGEX.matcher(e).matches(); }
    public static boolean nota(double n) { return n >= 0.0 && n <= 5.0; }
    public static void require(boolean cond, String msg) { if(!cond) throw new IllegalArgumentException(msg); }
}
