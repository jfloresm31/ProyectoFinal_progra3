package controlador;

import estructuras.ListaHojas;
import estructuras.MatrizOrtogonal;
import estructuras.NodoCelda;

public class MotorFormulas {
    private ListaHojas espacioTrabajo;

    public MotorFormulas(ListaHojas espacioTrabajo) {
        this.espacioTrabajo = espacioTrabajo;
    }

    public double evaluarFormula(String formula) {
        try {
            // Limpiamos la fórmula y verificamos que empiece con '='
            formula = formula.trim();
            if (!formula.startsWith("=")) return 0.0;

            // Extraemos la operación (ej: suma o mult)
            String operacion = formula.substring(1, formula.indexOf("(")).toLowerCase();
            
            // Extraemos todo lo que está dentro de los paréntesis principales
            String contenido = formula.substring(formula.indexOf("(") + 1, formula.lastIndexOf(")"));
            
            // Separamos la hoja de las coordenadas. Ej: "Hoja 1", "(2,5), (2,6)"
            String[] partes = contenido.split(", ", 2);
            String nombreHoja = partes[0].trim();
            String coordenadas = partes[1].trim(); // Queda: "(2,5), (2,6)"

            // Obtenemos las coordenadas de la celda 1 y celda 2
            String[] celdas = coordenadas.split("\\), \\(");
            String coord1 = celdas[0].replace("(", "").replace(")", ""); // "2,5"
            String coord2 = celdas[1].replace("(", "").replace(")", ""); // "2,6"

            // Buscamos los valores numéricos en la matriz
            double valor1 = obtenerValorCelda(nombreHoja, Integer.parseInt(coord1.split(",")[0].trim()), Integer.parseInt(coord1.split(",")[1].trim()));
            double valor2 = obtenerValorCelda(nombreHoja, Integer.parseInt(coord2.split(",")[0].trim()), Integer.parseInt(coord2.split(",")[1].trim()));

            // Ejecutamos la operación solicitada
            if (operacion.equals("suma")) {
                return valor1 + valor2;
            } else if (operacion.equals("multiplicacion") || operacion.equals("mult")) {
                return valor1 * valor2;
            }
            
        } catch (Exception e) {
            System.out.println("Error de sintaxis en la fórmula: " + e.getMessage());
        }
        return 0.0;
    }

    // Método auxiliar para ir a buscar el número exacto a la matriz ortogonal
    private double obtenerValorCelda(String nombreHoja, int fila, int columna) {
        // En tu ListaHojas necesitarás un método que devuelva la matriz por nombre
        // Por ahora asumimos que iteramos o usamos la actual
        MatrizOrtogonal matriz = espacioTrabajo.getMatrizActual(); 
        
        // Aquí deberías tener un método 'buscar' en tu MatrizOrtogonal
        // NodoCelda celda = matriz.buscar(fila, columna);
        // if(celda != null) return Double.parseDouble(celda.contenido);
        
        return 0.0; // Retorno temporal hasta que implementes buscar() en MatrizOrtogonal
    }
}