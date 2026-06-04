package estructuras;

public class NodoHash implements java.io.Serializable {
    public String clave;
    public String valor;
    public NodoHash siguiente; // Para manejar colisiones

    public NodoHash(String clave, String valor) {
        this.clave = clave;
        this.valor = valor;
        this.siguiente = null;
    }
}