package vista;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import estructuras.ListaHojas;
import controlador.GestorArchivos;
import controlador.MotorFormulas;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;


public class VentanaPrincipal extends JFrame {
	
	
    
    private JTabbedPane panelPestanias;
    private JTextField txtFormula;
    private JButton btnAplicar, btnRechazar;
    private ListaHojas espacioTrabajo;
    private MotorFormulas motor;

    public VentanaPrincipal() {
    	// Inicializamos las estructuras backend
        this.espacioTrabajo = new ListaHojas();
        this.espacioTrabajo.agregarHoja("Hoja 1"); // Sincronizado con la primer pestaña
        this.motor = new MotorFormulas(this.espacioTrabajo);
        
        // Configuración básica de la ventana
        setTitle("Hoja Electrónica - Proyecto IV");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. Inicializar y agregar los componentes
        crearMenu();
        crearBarraFormulas();
        crearEspacioTrabajo();
    }

    private void crearMenu() {
        JMenuBar barraMenu = new JMenuBar();
        
        // Menú Archivo
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem itemTablaHash = new JMenuItem("Tabla hash");
        JMenuItem itemGuardar = new JMenuItem("Guardar proyecto");
        JMenuItem itemCargar = new JMenuItem("Cargar proyecto");
        
        menuArchivo.add(itemTablaHash);
        menuArchivo.addSeparator();
        menuArchivo.add(itemGuardar);
        menuArchivo.add(itemCargar);
                
        // 1. Abrir la ventana de la Tabla Hash
        itemTablaHash.addActionListener(e -> {
            new VentanaHash().setVisible(true);
        });

        // 2. Guardar en almacenamiento secundario (Disco Duro) 
        itemGuardar.addActionListener(e -> {
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setDialogTitle("Guardar Proyecto");
            
            int seleccion = selectorArchivo.showSaveDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivoDestino = selectorArchivo.getSelectedFile();
                GestorArchivos gestor = new GestorArchivos();
                
                // Guardamos agregando una extensión personalizada, ej: .hoja
                gestor.guardarProyecto(this.espacioTrabajo, archivoDestino.getAbsolutePath() + ".hoja");
                JOptionPane.showMessageDialog(this, "El proyecto se guardó nítido en tu disco duro.");
            }
        });

        // 3. Recuperar datos desde el Disco Duro 
        itemCargar.addActionListener(e -> {
            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setDialogTitle("Cargar Proyecto");
            
            int seleccion = selectorArchivo.showOpenDialog(this);
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivoOrigen = selectorArchivo.getSelectedFile();
                GestorArchivos gestor = new GestorArchivos();
                
                // Recuperamos la memoria
                estructuras.ListaHojas espacioRecuperado = gestor.cargarProyecto(archivoOrigen.getAbsolutePath());
                
                if (espacioRecuperado != null) {
                    this.espacioTrabajo = espacioRecuperado;
                    JOptionPane.showMessageDialog(this, "Datos recuperados. \nNota técnica: Para ver los datos en la cuadrícula, se debe programar el refresco visual del JTable iterando sobre la MatrizOrtogonal cargada.");
                } else {
                    JOptionPane.showMessageDialog(this, "Hubo un error al intentar leer el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Menús adicionales
        JMenu menuInsertar = new JMenu("Insertar");
        JMenu menuAyuda = new JMenu("Ayuda");

        barraMenu.add(menuArchivo);
        barraMenu.add(menuInsertar);
        barraMenu.add(menuAyuda);

        setJMenuBar(barraMenu);
    }

    private void crearBarraFormulas() {
        JPanel panelNorte = new JPanel(new BorderLayout());
        
        // Etiqueta indicadora
        JLabel lblFx = new JLabel(" f(x): ");
        lblFx.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Campo de texto para la fórmula
        txtFormula = new JTextField();
        txtFormula.setToolTipText("Ingrese aquí la formula para la celda actual");
        
        // Botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnAplicar = new JButton("Aplicar");
        btnRechazar = new JButton("Rechazar");
        panelBotones.add(btnAplicar);
        panelBotones.add(btnRechazar);

        panelNorte.add(lblFx, BorderLayout.WEST);
        panelNorte.add(txtFormula, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.EAST);

        add(panelNorte, BorderLayout.NORTH);
        btnAplicar.addActionListener(e -> {
            String formulaText = txtFormula.getText();
            if (!formulaText.isEmpty()) {
                // Pasamos la fórmula al motor para evaluarla
                double resultado = motor.evaluarFormula(formulaText);
                
                // Aquí obtenemos la celda que el usuario tiene seleccionada actualmente
                Component comp = panelPestanias.getSelectedComponent();
                if (comp instanceof JScrollPane) {
                    JViewport viewport = ((JScrollPane) comp).getViewport();
                    JTable tablaActual = (JTable) viewport.getView();
                    
                    int filaSel = tablaActual.getSelectedRow();
                    int colSel = tablaActual.getSelectedColumn();
                    
                    if (filaSel != -1 && colSel != -1) {
                        // Ponemos el resultado en la vista
                        tablaActual.setValueAt(String.valueOf(resultado), filaSel, colSel);
                        // El TableModelListener del paso anterior se encargará de guardarlo en la matriz ortogonal automáticamente
                    } else {
                        JOptionPane.showMessageDialog(this, "Por favor seleccione una celda para el resultado.");
                    }
                }
            }
        });
        
        // Limpiar la barra con el botón rechazar 
        btnRechazar.addActionListener(e -> txtFormula.setText(""));
    }
    

    private void crearEspacioTrabajo() {
        panelPestanias = new JTabbedPane();
        
        // Agregamos un par de hojas por defecto para visualizar
        agregarNuevaHojaVisual("Hoja 1");
        agregarNuevaHojaVisual("Hoja 2");
        agregarNuevaHojaVisual("Hoja 3");

        add(panelPestanias, BorderLayout.CENTER);
    }

    // Método para crear una cuadrícula visual (JTable) y añadirla como pestaña
    private void agregarNuevaHojaVisual(String titulo) {
        // Configuramos una tabla de 50 filas x 26 columnas (A-Z)
        int filas = 50;
        int columnas = 26;
        
        // Nombres de las columnas (A, B, C...)
        String[] nombreColumnas = new String[columnas];
        for (int i = 0; i < columnas; i++) {
            nombreColumnas[i] = String.valueOf((char)('A' + i));
        }

        JTable tabla = new JTable(filas, columnas);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setCellSelectionEnabled(true);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        
        // Agregamos la regla de números al lado izquierdo (1, 2, 3...)
        JList<String> filaNumeros = new JList<>(generarNumeros(filas));
        filaNumeros.setFixedCellWidth(30);
        filaNumeros.setFixedCellHeight(tabla.getRowHeight());
        filaNumeros.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, false, false);
                label.setBackground(new Color(230, 230, 230));
                label.setHorizontalAlignment(CENTER);
                label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY));
                return label;
            }
        });
        scrollPane.setRowHeaderView(filaNumeros);
     // Escuchar cuando el usuario edita una celda
        tabla.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int filaVisual = e.getFirstRow();
                    int colVisual = e.getColumn();
                    
                    // Aseguramos que no sean valores negativos y obtenemos el dato
                    if (filaVisual >= 0 && colVisual >= 0) {
                        String dato = (String) tabla.getValueAt(filaVisual, colVisual);
                        
                        // Ajustamos índices (JTable empieza en 0, nuestra matriz asume filas/cols 1,2,3...)
                        int filaMatriz = filaVisual + 1;
                        int colMatriz = colVisual + 1;
                        
                        // Guardamos en la matriz de la hoja actual [cite: 5]
                        if (dato != null && !dato.trim().isEmpty()) {
                            espacioTrabajo.getMatrizActual().insertar(filaMatriz, colMatriz, dato);
                            System.out.println("Guardado en matriz: (" + filaMatriz + "," + colMatriz + ") -> " + dato);
                        }
                    }
                }
            }
        });

        panelPestanias.addTab(titulo, scrollPane);
    }

    // Utilidad para numerar las filas visuales
    private String[] generarNumeros(int cantidad) {
        String[] numeros = new String[cantidad];
        for (int i = 0; i < cantidad; i++) {
            numeros[i] = String.valueOf(i + 1);
        }
        return numeros;
    }

    public static void main(String[] args) {
        // Lanzar la ventana
        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal().setVisible(true);
        });
    }
}