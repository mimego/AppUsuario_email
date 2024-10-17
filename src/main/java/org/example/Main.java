package org.example;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        // Usamos SwingUtilities.invokeLater para garantizar que la GUI se construya y ejecute en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Probar la conexión antes de iniciar la aplicación
                if (ConexionDB.conectar() != null) {
                    // Crea una nueva instancia de AppUsuarios y la hace visible
                    new Appusuarios().setVisible(true);
                } else {
                    // Si falla la conexión, se muestra el mensaje y no se lanza la aplicación
                    JOptionPane.showMessageDialog(null,
                            "No se pudo iniciar la aplicación debido a problemas de conexión con la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
