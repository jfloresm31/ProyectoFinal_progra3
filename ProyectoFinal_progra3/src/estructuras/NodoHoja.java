package estructuras;

public class NodoHoja implements java.io.Serializable {
    public String nombreHoja; 
    public MatrizOrtogonal matriz; // Cada hoja tiene su propia cuadricula de celdas
    
    public NodoHoja siguiente;
    public NodoHoja anterior; 

    public NodoHoja(String nombreHoja) {
        this.nombreHoja = nombreHoja;
        this.matriz = new MatrizOrtogonal(); // Al crear la hoja, nace con su matriz limpia
        this.siguiente = null;
        this.anterior = null;
    }
}