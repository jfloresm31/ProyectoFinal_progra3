package controlador;

import estructuras.ListaHojas;
import java.io.*;

public class GestorArchivos {

    // Método para guardar el estado completo de la memoria al disco duro
    public void guardarProyecto(ListaHojas espacioTrabajo, String rutaArchivo) {
        try {
            FileOutputStream archivoFisico = new FileOutputStream(rutaArchivo);
            ObjectOutputStream escritorObjetos = new ObjectOutputStream(archivoFisico);
            
            // Guardamos toda la lista enlazada (que contiene las hojas y sus matrices) de un solo golpe
            escritorObjetos.writeObject(espacioTrabajo);
            
            escritorObjetos.close();
            archivoFisico.close();
            System.out.println("Proyecto guardado exitosamente en: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar en almacenamiento secundario: " + e.getMessage());
        }
    }

    // Método para recuperar los datos desde el disco duro
    public ListaHojas cargarProyecto(String rutaArchivo) {
        ListaHojas espacioRecuperado = null;
        try {
            FileInputStream archivoFisico = new FileInputStream(rutaArchivo);
            ObjectInputStream lectorObjetos = new ObjectInputStream(archivoFisico);
            
            // Leemos los bytes y los reconstruimos a nuestra clase ListaHojas
            espacioRecuperado = (ListaHojas) lectorObjetos.readObject();
            
            lectorObjetos.close();
            archivoFisico.close();
            System.out.println("Proyecto recuperado exitosamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al recuperar el archivo: " + e.getMessage());
        }
        return espacioRecuperado;
    }
}