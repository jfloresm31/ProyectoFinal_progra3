package estructuras;

public class ListaHojas implements java.io.Serializable {
    private NodoHoja cabeza;
    private NodoHoja cola;
    private NodoHoja hojaActual; // Puntero vital para saber dónde estamos trabajando

    public ListaHojas() {
        this.cabeza = null;
        this.cola = null;
        this.hojaActual = null;
    }

    // Agregar una nueva pestaña al espacio de trabajo
    public void agregarHoja(String nombreHoja) {
        NodoHoja nuevaHoja = new NodoHoja(nombreHoja);
        
        if (cabeza == null) {
            cabeza = nuevaHoja;
            cola = nuevaHoja;
            hojaActual = nuevaHoja; // Si es la primera, se vuelve la activa por defecto
        } else {
            cola.siguiente = nuevaHoja;
            nuevaHoja.anterior = cola;
            cola = nuevaHoja;
        }
    }

    // Método para acceder a la matriz de la hoja visible
    public MatrizOrtogonal getMatrizActual() {
        if (hojaActual != null) {
            return hojaActual.matriz;
        }
        return null;
    }

    // Cambiar de pestaña (Esto lo conectarás a los botones de la Interfaz Gráfica)
    public boolean cambiarPestania(String nombre) {
        NodoHoja temporal = cabeza;
        while (temporal != null) {
            if (temporal.nombreHoja.equals(nombre)) {
                hojaActual = temporal;
                return true; // Se cambió con éxito
            }
            temporal = temporal.siguiente;
        }
        return false; // No se encontró la hoja
    }
}