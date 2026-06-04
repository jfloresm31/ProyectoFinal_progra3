package estructuras;

public class MatrizOrtogonal implements java.io.Serializable {
    // Puntero al inicio absoluto (arriba a la izquierda)
    private NodoCelda raiz;

    public MatrizOrtogonal() {
        // Nodo (0,0) que sirve como punto de partida
        this.raiz = new NodoCelda(0, 0, "RAIZ");
    }

    // 1. Buscar o crear la cabecera de la columna
    private NodoCelda obtenerCabeceraColumna(int columna) {
        NodoCelda actual = raiz;
        // Avanzamos hacia la derecha buscando la columna
        while (actual.derecha != null && actual.derecha.columna <= columna) {
            actual = actual.derecha;
        }
        if (actual.columna == columna) {
            return actual; // La columna ya existe
        }
        
        // Si no existe, creamos la cabecera y la enlazamos
        NodoCelda nuevaCabecera = new NodoCelda(0, columna, "C" + columna);
        nuevaCabecera.derecha = actual.derecha;
        if (actual.derecha != null) {
            actual.derecha.izquierda = nuevaCabecera;
        }
        actual.derecha = nuevaCabecera;
        nuevaCabecera.izquierda = actual;
        return nuevaCabecera;
    }

    // 2. Buscar o crear la cabecera de la fila
    private NodoCelda obtenerCabeceraFila(int fila) {
        NodoCelda actual = raiz;
        // Avanzamos hacia abajo buscando la fila
        while (actual.abajo != null && actual.abajo.fila <= fila) {
            actual = actual.abajo;
        }
        if (actual.fila == fila) {
            return actual; // La fila ya existe
        }
        
        // Si no existe, creamos la cabecera y la enlazamos
        NodoCelda nuevaCabecera = new NodoCelda(fila, 0, "F" + fila);
        nuevaCabecera.abajo = actual.abajo;
        if (actual.abajo != null) {
            actual.abajo.arriba = nuevaCabecera;
        }
        actual.abajo = nuevaCabecera;
        nuevaCabecera.arriba = actual;
        return nuevaCabecera;
    }

    // 3. Insertar o actualizar la celda con el dato
    public void insertar(int fila, int columna, String contenido) {
        NodoCelda cabeceraColumna = obtenerCabeceraColumna(columna);
        NodoCelda cabeceraFila = obtenerCabeceraFila(fila);

        NodoCelda nuevo = new NodoCelda(fila, columna, contenido);

        // --- Enlazar en la COLUMNA (de arriba hacia abajo) ---
        NodoCelda actualCol = cabeceraColumna;
        while (actualCol.abajo != null && actualCol.abajo.fila < fila) {
            actualCol = actualCol.abajo;
        }
        
        // Si la celda ya existe en esa posición, solo actualizamos el contenido
        if (actualCol.abajo != null && actualCol.abajo.fila == fila) {
            actualCol.abajo.contenido = contenido;
            return; // Terminamos el proceso
        }
        
        // Enlaces verticales
        nuevo.abajo = actualCol.abajo;
        if (actualCol.abajo != null) {
            actualCol.abajo.arriba = nuevo;
        }
        actualCol.abajo = nuevo;
        nuevo.arriba = actualCol;

        // --- Enlazar en la FILA (de izquierda a derecha) ---
        NodoCelda actualFil = cabeceraFila;
        while (actualFil.derecha != null && actualFil.derecha.columna < columna) {
            actualFil = actualFil.derecha;
        }
        
        // Enlaces horizontales
        nuevo.derecha = actualFil.derecha;
        if (actualFil.derecha != null) {
            actualFil.derecha.izquierda = nuevo;
        }
        actualFil.derecha = nuevo;
        nuevo.izquierda = actualFil;
    }
}