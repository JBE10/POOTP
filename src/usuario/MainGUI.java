package usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import negocio.*;

public class MainGUI extends JFrame {
    private Empresa e = new Empresa();

    public MainGUI() {
        configurarVentanaPrincipal();
        agregarPanelBotones();
    }

    private void configurarVentanaPrincipal() {
        setTitle("Sistema Minimercados");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void agregarPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(5, 1)); // 5 opciones de menú

        JButton btnCrearMinimercado = new JButton("Crear Minimercado");
        JButton btnIngresarMinimercado = new JButton("Ingresar al sistema de un Minimercado");
        JButton btnFacturacionTotal = new JButton("Obtener Facturación Total");
        JButton btnSalir = new JButton("Salir del Sistema");

        panel.add(btnCrearMinimercado);
        panel.add(btnIngresarMinimercado);
        panel.add(btnFacturacionTotal);
        panel.add(btnSalir);
        add(panel, BorderLayout.CENTER);

        // Agregar eventos a los botones
        btnCrearMinimercado.addActionListener(e -> mostrarFormularioCrearMinimercado());
        btnIngresarMinimercado.addActionListener(e -> mostrarFormularioIngresarMinimercado());
        btnFacturacionTotal.addActionListener(e -> mostrarFacturacionTotal());
        btnSalir.addActionListener(e -> System.exit(0));
    }

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

        btnCrear.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                String nombre = txtNombre.getText();

                Minimercado m = new Minimercado();
                m.setCodigo(codigo);
                m.setNombre(nombre);

                MainGUI.this.e.agregarMinimercado(m);

                JOptionPane.showMessageDialog(frame, "Minimercado creado correctamente.");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al crear minimercado.");
            }
        });
    }

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

        btnIngresar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(txtCodigo.getText());
                Minimercado minimercado = MainGUI.this.e.obtenerMinimercadoPorCodigo(codigo);

                if (minimercado == null) {
                    JOptionPane.showMessageDialog(frame, "Minimercado inexistente.");
                } else {
                    frame.dispose();
                    mostrarMenuMinimercado(minimercado);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al ingresar al minimercado.");
            }
        });
    }

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

        btnActualizarStock.addActionListener(e -> actualizarStock(minimercado));
        btnMostrarCatalogo.addActionListener(e -> mostrarCatalogo(minimercado));
        btnMostrarFacturacion.addActionListener(e -> JOptionPane.showMessageDialog(null, "Facturación: " + minimercado.getFacturacion()));
        btnAgregarProducto.addActionListener(e -> agregarProducto(minimercado));
        btnRealizarVenta.addActionListener(e -> realizarVenta(minimercado));
    }



    private void realizarVenta(Minimercado minimercado) {
        JFrame frame = new JFrame("Realizar Venta");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        JLabel lblCodigoProducto = new JLabel("Código del Producto:");
        JTextField txtCodigoProducto = new JTextField();
        JLabel lblCantidad = new JLabel("Cantidad:");
        JTextField txtCantidad = new JTextField();
        JButton btnAgregarProducto = new JButton("Agregar Producto");
        JButton btnFinalizarVenta = new JButton("Finalizar Venta");

        frame.add(lblCodigoProducto);
        frame.add(txtCodigoProducto);
        frame.add(lblCantidad);
        frame.add(txtCantidad);
        frame.add(new JLabel()); // Espaciador
        frame.add(btnAgregarProducto);
        frame.add(new JLabel()); // Espaciador
        frame.add(btnFinalizarVenta);

        frame.setVisible(true);

        Venta venta = new Venta();

        btnAgregarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigoProducto = Integer.parseInt(txtCodigoProducto.getText());
                    int cantidad = Integer.parseInt(txtCantidad.getText());

                    Producto producto = minimercado.getCatalogo().obtenerProducto(codigoProducto);

                    if (producto != null) {
                        venta.agregarProductos(producto, cantidad);
                        JOptionPane.showMessageDialog(frame, "Producto agregado a la venta.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Producto no encontrado.");
                    }

                    txtCodigoProducto.setText("");
                    txtCantidad.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Datos inválidos, por favor ingrese un número.");
                }
            }
        });

        btnFinalizarVenta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioMedioDePago(venta, minimercado);
                frame.dispose(); // Cerrar ventana actual
            }
        });
    }

    private void mostrarFormularioMedioDePago(Venta venta, Minimercado minimercado) {
        JFrame frame = new JFrame("Seleccionar Medio de Pago");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 1));

        JLabel lblMedioPago = new JLabel("Seleccione el medio de pago:");
        JRadioButton rbtnDebito = new JRadioButton("Débito");
        JRadioButton rbtnEfectivo = new JRadioButton("Efectivo");
        JRadioButton rbtnCredito = new JRadioButton("Crédito");
        JButton btnConfirmarPago = new JButton("Confirmar Pago");

        ButtonGroup grupoMedioPago = new ButtonGroup();
        grupoMedioPago.add(rbtnDebito);
        grupoMedioPago.add(rbtnEfectivo);
        grupoMedioPago.add(rbtnCredito);

        frame.add(lblMedioPago);
        frame.add(rbtnDebito);
        frame.add(rbtnEfectivo);
        frame.add(rbtnCredito);
        frame.add(btnConfirmarPago);

        frame.setVisible(true);

        btnConfirmarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MedioDePago medioDePago = null;

                if (rbtnDebito.isSelected()) {
                    medioDePago = new Debito();
                } else if (rbtnEfectivo.isSelected()) {
                    medioDePago = new Efectivo();
                } else if (rbtnCredito.isSelected()) {
                    mostrarFormularioCuotas(venta, minimercado);
                    frame.dispose(); // Cerrar ventana actual
                    return;
                }

                procesarVenta(venta, medioDePago, minimercado);
                frame.dispose();
            }
        });
    }

    private void mostrarFormularioCuotas(Venta venta, Minimercado minimercado) {
        JFrame frame = new JFrame("Seleccionar Cuotas");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 1));

        JLabel lblCuotas = new JLabel("Ingrese el número de cuotas (1, 2, 3, 6):");
        JTextField txtCuotas = new JTextField();
        JButton btnConfirmarCuotas = new JButton("Confirmar Cuotas");

        frame.add(lblCuotas);
        frame.add(txtCuotas);
        frame.add(btnConfirmarCuotas);

        frame.setVisible(true);

        btnConfirmarCuotas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int cuotas = Integer.parseInt(txtCuotas.getText());
                    if (cuotas == 1 || cuotas == 2 || cuotas == 3 || cuotas == 6) {
                        MedioDePago medioDePago = new Credito(cuotas);
                        procesarVenta(venta, medioDePago, minimercado);
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Número de cuotas no válido.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor ingrese un número válido.");
                }
            }
        });
    }

    private void procesarVenta(Venta venta, MedioDePago medioDePago, Minimercado minimercado) {
        venta.setmedioDePago(medioDePago);
        venta.procesarVenta();
        minimercado.agregarVenta(venta);

        JOptionPane.showMessageDialog(null, "Venta procesada. TOTAL A PAGAR: $" + venta.getTotal());
    }

    private void actualizarStock(Minimercado minimercado) {
        JFrame frame = new JFrame("Actualizar Stock");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 2));

        JLabel lblCodigoProducto = new JLabel("Código del Producto: ");
        JTextField txtCodigoProducto = new JTextField();
        JLabel lblCantidadStock = new JLabel("Cantidad a Agregar: ");
        JTextField txtCantidadStock = new JTextField();
        JButton btnActualizar = new JButton("Actualizar");

        frame.add(lblCodigoProducto);
        frame.add(txtCodigoProducto);
        frame.add(lblCantidadStock);
        frame.add(txtCantidadStock);
        frame.add(new JLabel()); // Espaciador
        frame.add(btnActualizar);

        frame.setVisible(true);

        btnActualizar.addActionListener(e -> {
            try {
                int codigoProducto = Integer.parseInt(txtCodigoProducto.getText());
                int cantidadStock = Integer.parseInt(txtCantidadStock.getText());

                Producto producto = minimercado.getCatalogo().obtenerProducto(codigoProducto);
                if (producto != null) {
                    producto.setStockDisponible(cantidadStock+producto.getStockDisponible());
                    JOptionPane.showMessageDialog(frame, "Stock actualizado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Producto no encontrado.");
                }
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al actualizar stock.");
            }
        });
    }

    private void mostrarCatalogo(Minimercado minimercado) {
        Catalogo catalogo = minimercado.getCatalogo();
        ArrayList<Producto> listaCatalogo = catalogo.devolverCatalogo();

        DefaultListModel<Producto> modeloLista = new DefaultListModel<>();
        for (Producto item : listaCatalogo) {
            modeloLista.addElement(item);
        }

        JList<Producto> listaMostrar = new JList<>(modeloLista);
        JScrollPane scrollPane = new JScrollPane(listaMostrar);

        JFrame ventanaCatalogo = new JFrame("Catálogo del Minimercado");
        ventanaCatalogo.setSize(300, 200);
        ventanaCatalogo.add(scrollPane);
        ventanaCatalogo.setVisible(true);
    }

    private void agregarProducto(Minimercado minimercado) {
        JFrame frame = new JFrame("Agregar Producto");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(5, 2));

        JLabel lblCodigoProducto = new JLabel("Código del Producto: ");
        JTextField txtCodigoProducto = new JTextField();
        JLabel lblNombreProducto = new JLabel("Nombre del Producto: ");
        JTextField txtNombreProducto = new JTextField();
        JLabel lblPrecioProducto = new JLabel("Precio: ");
        JTextField txtPrecioProducto = new JTextField();
        JLabel lblStockMinimo = new JLabel("Stock mínimo: ");
        JTextField txtStockMinimo = new JTextField();
        JLabel lblStockActual = new JLabel("Stock actual: ");
        JTextField txtStockActual = new JTextField();
        JButton btnAgregar = new JButton("Agregar");

        frame.add(lblCodigoProducto);
        frame.add(txtCodigoProducto);
        frame.add(lblNombreProducto);
        frame.add(txtNombreProducto);
        frame.add(lblPrecioProducto);
        frame.add(txtPrecioProducto);
        frame.add(lblStockMinimo);
        frame.add(txtStockMinimo);
        frame.add(lblStockActual);
        frame.add(txtStockActual);
        frame.add(btnAgregar);

        frame.setVisible(true);

        btnAgregar.addActionListener(e -> {
            try {
                Producto producto=new Producto();
                int codigoProducto = Integer.parseInt(txtCodigoProducto.getText());
                String nombreProducto = txtNombreProducto.getText();
                double precioProducto = Double.parseDouble(txtPrecioProducto.getText());
                int stockMinimo = Integer.parseInt(txtStockMinimo.getText());
                int stockActual = Integer.parseInt(txtStockActual.getText());

                producto.setIdProducto(codigoProducto);
                producto.setDescripcion(nombreProducto);
                producto.setStockMinimo(stockMinimo);
                producto.setPrecio(precioProducto);
                producto.setStockDisponible(stockActual);
                minimercado.getCatalogo().agregarProducto(producto);

                JOptionPane.showMessageDialog(frame, "Producto agregado correctamente.");
                frame.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error al agregar producto.");
            }
        });
    }



    private void mostrarFacturacionTotal() {
        JOptionPane.showMessageDialog(null, "Facturación total de la empresa: " + e.obtenerFacturacionTotal());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGui = new MainGUI();
            mainGui.setVisible(true);
        });
    }
}
