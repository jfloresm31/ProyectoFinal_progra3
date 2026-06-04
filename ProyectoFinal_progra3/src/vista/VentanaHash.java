package vista;

import estructuras.TablaHash;
import javax.swing.*;
import java.awt.*;

public class VentanaHash extends JFrame {
    private JTable tablaDatos;
    private TablaHash miTablaHash;

    public VentanaHash() {
        setTitle("Generador de Tabla Hash");
        setSize(400, 500);
        setLocationRelativeTo(null); // Centrar en pantalla
        setLayout(new BorderLayout());

        // Instanciamos tu algoritmo Hash nativo con una capacidad inicial
        miTablaHash = new TablaHash(100); 

        // Creamos una interfaz similar a la hoja de cálculo 
        String[] columnas = {"Columna A (Datos)", "Columna B (Índice Hash)"};
        String[][] datosVacios = new String[30][2]; // 30 filas disponibles
        
        tablaDatos = new JTable(datosVacios, columnas);
        tablaDatos.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tablaDatos);
        add(scrollPane, BorderLayout.CENTER);

        // Botón para procesar
        JButton btnGenerar = new JButton("Generar y Mostrar Índices");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 14));
        btnGenerar.addActionListener(e -> procesarDatosHash());
        add(btnGenerar, BorderLayout.SOUTH);
    }

    private void procesarDatosHash() {
        // Recorremos la Columna A ingresando datos y mostrando índices en la derecha 
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            Object datoObj = tablaDatos.getValueAt(i, 0); 
            
            if (datoObj != null && !datoObj.toString().trim().isEmpty()) {
                String clave = datoObj.toString();
                
                // Insertamos en tu estructura
                miTablaHash.insertar(clave, "Procesado"); 
                
                // Calculamos visualmente el índice para mostrarlo en la columna derecha [cite: 49]
                int indiceGenerado = calcularIndiceParaVista(clave, 100);
                tablaDatos.setValueAt("Índice [" + indiceGenerado + "]", i, 1); 
            }
        }
        JOptionPane.showMessageDialog(this, "Datos procesados en la Tabla Hash con éxito.");
    }

    // Método auxiliar que replica la lógica de índice de tu TablaHash para mostrarlo en pantalla
    private int calcularIndiceParaVista(String clave, int capacidad) {
        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash += clave.charAt(i);
        }
        return hash % capacidad;
    }
}