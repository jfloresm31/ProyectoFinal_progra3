package estructuras;

public class NodoCelda implements java.io.Serializable {
    public int fila;
    public int columna;
    public String contenido; // La fórmula, texto o número ingresado
    public double valorCalculado; // El resultado numérico para operar

    // Punteros de la matriz ortogonal
    public NodoCelda arriba;
    public NodoCelda abajo;
    public NodoCelda izquierda;
    public NodoCelda derecha;

    public NodoCelda(int fila, int columna, String contenido) {
        this.fila = fila;
        this.columna = columna;
        this.contenido = contenido;
        this.valorCalculado = 0.0;
        
        this.arriba = null;
        this.abajo = null;
        this.izquierda = null;
        this.derecha = null;
    }
}