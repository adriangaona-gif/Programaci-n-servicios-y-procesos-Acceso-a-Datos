package pack;

import java.io.Serializable;

public class Calificacion implements Serializable {
    private static final long serialVersionUID = 3057545624874202352L;

    private String asignatura;
    private int nota; // Sobre 100

    public Calificacion(String asignatura, int nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }

    @Override
    public String toString() {
        return "Calificación [Asignatura=" + asignatura + ", Nota=" + nota + "]";
    }
}
