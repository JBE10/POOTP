package usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import negocio.*;


public class MainGUI extends JFrame {
    private Empresa e = new Empresa();

    public MainGUI() {
        // Configurar ventana principal
        setTitle("Sistema Minimercados");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear panel de botones
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1)); // 5 opciones de menú

        // Crear botones
        JButton btnCrearMinimercado = new JButton("Crear Minimercado");
        JButton btnIngresarMinimercado = new JButton("Ingresar al sistema de un Minimercado");
        JButton btnFacturacionTotal = new JButton("Obtener Facturación Total");
        JButton btnSalir = new JButton("Salir del Sistema");

        // Agregar botones al panel
        panel.add(btnCrearMinimercado);
        panel.add(btnIngresarMinimercado);
        panel.add(btnFacturacionTotal);
        panel.add(btnSalir);

        // Agregar panel al JFrame
        add(panel, BorderLayout.CENTER);

        // Agregar eventos a los botones
        btnCrearMinimercado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCrearMinimercado();
            }
        });

        btnIngresarMinimercado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioIngresarMinimercado();
            }
        });

        btnFacturacionTotal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFacturacionTotal();
            }
        });

        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    // Método para crear minimercado
    private void mostrarFormularioCrearMinimercado() {
        JFrame frame = new JFrame("Crear Minimercado");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 2));

        JLabel lblCodigo = new JLabel("Código: ");
        JTextField txtCodigo = new JTextField();
        JLabel lblNombre = new JLabel("Nombre: ");
        JTextField txtNombre = new JTextField();
        JButton btnCrear = new JButton("Crear");

        frame.add(lblCodigo);
        frame.add(txtCodigo);
        frame.add(lblNombre);
        frame.add(txtNombre);
        frame.add(new JLabel()); // Espaciador
        frame.add(btnCrear);

        frame.setVisible(true);

        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodigo.getText());
                    String nombre = txtNombre.getText();

                    Minimercado m = new Minimercado();
                    m.setCodigo(codigo);
                    m.setNombre(nombre);

                    // Solución: Usar MainGUI.this.e para acceder a la instancia de Empresa
                    MainGUI.this.e.agregarMinimercado(m);

                    JOptionPane.showMessageDialog(frame, "Minimercado creado correctamente.");
                    frame.dispose(); // Cierra la ventana de creación
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error al crear minimercado.");
                }
            }
        });
    }

    // Método para ingresar a un minimercado
    private void mostrarFormularioIngresarMinimercado() {
        JFrame frame = new JFrame("Ingresar al Minimercado");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(2, 2));

        JLabel lblCodigo = new JLabel("Código del Minimercado: ");
        JTextField txtCodigo = new JTextField();
        JButton btnIngresar = new JButton("Ingresar");

        frame.add(lblCodigo);
        frame.add(txtCodigo);
        frame.add(new JLabel()); // Espaciador
        frame.add(btnIngresar);

        frame.setVisible(true);

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(txtCodigo.getText());
                    Minimercado minimercado = MainGUI.this.e.obtenerMinimercadoPorCodigo(codigo);

                    if (minimercado == null) {
                        JOptionPane.showMessageDialog(frame, "Minimercado inexistente.");
                    } else {
                        frame.dispose();
                        mostrarMenuMinimercado(minimercado); // Solo se llama si minimercado no es nulo
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error al ingresar al minimercado.");
                }
            }
        });
    }

    // Mostrar el menú del minimercado
    private void mostrarMenuMinimercado(Minimercado minimercado) {
        JFrame frame = new JFrame("Menú del Minimercado: " + minimercado.getNombre());
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        JButton btnAgregarProducto = new JButton("Agregar Producto");
        JButton btnRealizarVenta = new JButton("Realizar Venta");
        JButton btnActualizarStock = new JButton("Actualizar Stock");
        JButton btnMostrarCatalogo = new JButton("Mostrar Catálogo");
        JButton btnMostrarFacturacion = new JButton("Mostrar Facturación");

        frame.add(btnAgregarProducto);
        frame.add(btnRealizarVenta);
        frame.add(btnActualizarStock);
        frame.add(btnMostrarCatalogo);
        frame.add(btnMostrarFacturacion);

        frame.setVisible(true);

        // Puedes agregar los eventos para cada botón de manera similar
        btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí agregas la funcionalidad para agregar productos
            }
        });

        btnRealizarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí agregas la funcionalidad para realizar ventas
            }
        });

        btnActualizarStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí agregas la funcionalidad para actualizar stock
            }
        });

        btnMostrarCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí muestras el catálogo
            }
        });

        btnMostrarFacturacion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Facturación: " + minimercado.getFacturacion());
            }
        });
    }

    // Método para mostrar la facturación total de la empresa
    private void mostrarFacturacionTotal() {
        double facturacionTotal = e.obtenerFacturacionTotal();
        JOptionPane.showMessageDialog(this, "Facturación total de la empresa: " + facturacionTotal);
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana principal
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainGUI gui = new MainGUI();
                gui.setVisible(true);
            }
        });
    }
}
