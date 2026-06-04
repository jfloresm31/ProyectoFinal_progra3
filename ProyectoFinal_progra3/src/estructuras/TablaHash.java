package estructuras;

public class TablaHash implements java.io.Serializable {
    private NodoHash[] arreglo;
    private int capacidad;

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.arreglo = new NodoHash[capacidad];
    }

    // Algoritmo Hash propio: Convierte un String en un número entero
    private int calcularHash(String clave) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash += clave.charAt(i); // Suma el valor ASCII de cada letra
        }
        return hash % capacidad; // Asegura que el índice esté dentro del arreglo
    }

    // Método para insertar un dato en la tabla
    public void insertar(String clave, String valor) {
        int indice = calcularHash(clave);
        NodoHash nuevoNodo = new NodoHash(clave, valor);

        if (arreglo[indice] == null) {
            // Si la posición está libre, entra directo
            arreglo[indice] = nuevoNodo;
        } else {
            // Si ya hay un dato (colisión), lo mandamos al final de la lista de esa posición
            NodoHash actual = arreglo[indice];
            while (actual.siguiente != null) {
                // Si la clave ya existe, solo actualizamos el valor y salimos
                if (actual.clave.equals(clave)) {
                    actual.valor = valor;
                    return;
                }
                actual = actual.siguiente;
            }
            // Revisamos el último nodo por si es la misma clave
            if (actual.clave.equals(clave)) {
                actual.valor = valor;
            } else {
                actual.siguiente = nuevoNodo;
            }
        }
    }

    // Método para buscar un valor generado
    public String buscar(String clave) {
        int indice = calcularHash(clave);
        NodoHash actual = arreglo[indice];

        while (actual != null) {
            if (actual.clave.equals(clave)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null; // No se encontró
    }
}