package pruebas;
import models.*;
import utils.*;

public class CasosPrueba {
    public static void main(String[] args) {
        System.out.println("== TESTS ==");
        check("Email Valido", Validador.email("a@b.com"));
        check("Email Malo", !Validador.email("ab.com"));
        check("Nota OK", Validador.nota(5.0));
        check("Nota Fail", !Validador.nota(6.0));
        
        Estudiante e = new Estudiante("1", "Test", "t@t.com");
        check("Record Estudiante", e.nombre().equals("Test"));

        // Test Curso Ponderado
        Curso c = new Curso("1", "Java", "P1", 30, 30, 40);
        check("Curso Ponderacion", (c.p1()+c.p2()+c.p3()) == 100);

        System.out.println("== FIN ==");
    }
    static void check(String n, boolean ok) { System.out.println((ok ? "[OK] " : "[FAIL] ") + n); }
}
