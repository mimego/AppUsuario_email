package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Appusuarios extends JFrame {
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtEmail;
    private JButton btnGuardar;
    private JButton btnMostrar;
    private JTextArea areaUsuarios;

    public Appusuarios() {
        // Configuración básica de la ventana
        setTitle("Gestión de Usuarios");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        // Inicializar los componentes de la interfaz
        JLabel lblNombre = new JLabel("Nombre:");
        JLabel lblApellido = new JLabel("Apellido:");
        JLabel lblEmail = new JLabel("Email:");

        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtEmail = new JTextField();

        btnGuardar = new JButton("Guardar");
        btnMostrar = new JButton("Mostrar Usuarios");

        areaUsuarios = new JTextArea();
        areaUsuarios.setEditable(false); // El área de texto es solo para mostrar

        // Agregar componentes al Frame
        add(lblNombre);
        add(txtNombre);
        add(lblApellido);
        add(txtApellido);
        add(lblEmail);
        add(txtEmail);
        add(btnGuardar);
        add(btnMostrar);
        add(new JScrollPane(areaUsuarios)); // Agregamos el JTextArea dentro de un JScrollPane

        // Acción para guardar usuario en la base de datos
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarUsuario();
            }
        });

        // Acción para mostrar usuarios
        btnMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarUsuarios();
            }
        });
    }

    // Método para guardar usuario en la base de datos
    private void guardarUsuario() {
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String email = txtEmail.getText();

        Connection conexion = ConexionDB.conectar();
        try {
            String query = "INSERT INTO usuarios (nombre, apellido, email) VALUES (?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, email);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Usuario guardado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar usuario: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para mostrar usuarios en el JTextArea
    private void mostrarUsuarios() {
        Connection conexion = ConexionDB.conectar();
        areaUsuarios.setText(""); // Limpiar el área de texto antes de mostrar los usuarios
        try {
            String query = "SELECT * FROM usuarios";
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String email = rs.getString("email");

                areaUsuarios.append(id + " - " + nombre + " " + apellido + " - " + email + "\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar usuarios: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método main para ejecutar la aplicación
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Appusuarios().setVisible(true); // Aquí se instancia y muestra la interfaz gráfica
            }
        });
    }
}
