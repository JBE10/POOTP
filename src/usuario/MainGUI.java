package usuario;

import negocio.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends javax.swing.JFrame {
    static Minimercado minimercado = new Minimercado();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGui = new MainGUI();
            mainGui.setVisible(true);
        });
    }

    public MainGUI() {
        configurarVentanaPrincipal();
        agregarBotones();
    }

    private void configurarVentanaPrincipal() {
        setTitle("Sistema Minimercados");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    public void agregarBotones() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 5, 5));
        Dimension buttonSize = new Dimension(200, 40);

        // Crear botones
        JButton agregarProducto = crearBoton("Agregar Producto", buttonSize, this::agregarProducto);
        JButton eliminarProducto = crearBoton("Eliminar Producto", buttonSize, this::eliminarProducto);
        JButton realizarVenta = crearBoton("Realizar Venta", buttonSize, this::realizarVenta);
        JButton obtenerCatalogo = crearBoton("Obtener Catálogo", buttonSize, this::mostrarCatalogo);
        JButton obtenerFacturacion = crearBoton("Obtener Facturación", buttonSize, this::mostrarFacturacion);
        JButton obtenerProducto = crearBoton("Obtener Producto", buttonSize, this::mostrarProducto);
        JButton actualizarStock = crearBoton("Actualizar Stock", buttonSize, this::actualizarStock);
        JButton stockBajo = crearBoton("Productos con Stock Bajo", buttonSize, this::mostrarStockBajo);
        JButton salir = crearBoton("Salir", buttonSize, e -> System.exit(0));

        // Agregar botones al panel
        panel.add(agregarProducto);
        panel.add(eliminarProducto);
        panel.add(realizarVenta);
        panel.add(obtenerCatalogo);
        panel.add(obtenerFacturacion);
        panel.add(obtenerProducto);
        panel.add(actualizarStock);
        panel.add(stockBajo);
        panel.add(salir);

        add(panel, BorderLayout.CENTER);
    }

    private JButton crearBoton(String texto, Dimension tamaño, java.awt.event.ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setPreferredSize(tamaño);
        boton.addActionListener(action);
        return boton;
    }

    private void agregarProducto(java.awt.event.ActionEvent e) {
        try {
            int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el código del producto:"));
            String descripcion = JOptionPane.showInputDialog(this, "Ingrese la descripción:");
            double precioUnitario = Double.parseDouble(JOptionPane.showInputDialog(this, "Ingrese el precio unitario:"));
            int stockDisponible = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el stock disponible:"));
            int stockMinimo = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el stock mínimo:"));

            Producto producto = new Producto(codigoProducto, descripcion, precioUnitario, stockMinimo, stockDisponible);
            if (!minimercado.getCatalogo().agregarProducto(producto)) {
                JOptionPane.showMessageDialog(this, "Producto duplicado.");
            } else {
                JOptionPane.showMessageDialog(this, "El producto se ha agregado correctamente.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar producto: " + ex.getMessage());
        }
    }

    private void eliminarProducto(java.awt.event.ActionEvent e) {
        try {
            int codigo = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el código del producto que quiere eliminar:"));
            Producto producto = minimercado.getCatalogo().obtenerProducto(codigo);
            if (producto != null) {
                minimercado.getCatalogo().eliminarProducto(producto);
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar producto: " + ex.getMessage());
        }
    }

    private void realizarVenta(java.awt.event.ActionEvent e) {
        Catalogo catalogo = minimercado.getCatalogo();
        Detalle pedido = new Detalle();

        try {
            int codigoProducto;
            do {
                codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el código del producto o -1 para finalizar:"));
                if (codigoProducto != -1) {
                    Producto producto = catalogo.obtenerProducto(codigoProducto);
                    if (producto != null) {
                        int cantidad = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la cantidad:"));
                        pedido.agregarProducto(producto, cantidad);
                    } else {
                        JOptionPane.showMessageDialog(this, "Producto inexistente.");
                    }
                }
            } while (codigoProducto != -1);

            MedioDePago medioDePago = seleccionarMedioDePago();
            if (medioDePago == null) return;

            int idVenta = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el número de venta:"));
            Cliente cliente = obtenerDatosCliente();
            double subtotal = pedido.calcularTotal();

            Venta nuevaVenta = new Venta(idVenta, cliente, pedido, subtotal, medioDePago);
            nuevaVenta.procesarPago();
            minimercado.agregarVenta(nuevaVenta);

            JOptionPane.showMessageDialog(this, "Venta registrada correctamente. Precio total: $" + nuevaVenta.getTotal());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al realizar venta: " + ex.getMessage());
        }
    }

    private MedioDePago seleccionarMedioDePago() {
        try {
            int opcion = Integer.parseInt(JOptionPane.showInputDialog(this, "Seleccione el medio de pago:\n1. Efectivo\n2. Débito\n3. Crédito"));
            switch (opcion) {
                case 1: return new Efectivo();
                case 2: return new Debito();
                case 3: {
                    int cuotas = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese la cantidad de cuotas (2, 3, 6):"));
                    return new Credito(cuotas);
                }
                default: throw new IllegalArgumentException("Opción inválida.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al seleccionar medio de pago: " + ex.getMessage());
            return null;
        }
    }

    private Cliente obtenerDatosCliente() {
        try {
            int idCliente = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el número de cliente:"));
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del cliente:");
            String mail = JOptionPane.showInputDialog(this, "Ingrese el mail del cliente:");
            String direccion = JOptionPane.showInputDialog(this, "Ingrese la dirección del cliente:");
            return new Cliente(nombre, idCliente, mail, direccion);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del cliente: " + ex.getMessage());
            return null;
        }
    }

    private void mostrarCatalogo(java.awt.event.ActionEvent e) {
        Catalogo catalogo = minimercado.getCatalogo();
        ArrayList<Producto> productos = catalogo.obtenerProductos();

        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El catálogo está vacío.");
        } else {
            StringBuilder listado = new StringBuilder();
            for (Producto p : productos) {
                listado.append(p.getIdProducto()).append(" - ")
                        .append(p.getDescripcion()).append(" (Stock: ")
                        .append(p.getStockDisponible()).append(")\n");
            }
            JOptionPane.showMessageDialog(this, listado.toString(), "Catálogo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarFacturacion(java.awt.event.ActionEvent e) {
        double facturacion = minimercado.getFacturacion();
        JOptionPane.showMessageDialog(this, "Facturación total: $" + facturacion);
    }

    private void mostrarProducto(java.awt.event.ActionEvent e) {
        try {
            int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el código del producto:"));
            Producto producto = minimercado.getCatalogo().obtenerProducto(codigoProducto);

            if (producto != null) {
                JOptionPane.showMessageDialog(this, "Código: " + producto.getIdProducto() +
                        "\nNombre: " + producto.getDescripcion() +
                        "\nPrecio: $" + producto.getPrecio() +
                        "\nStock Disponible: " + producto.getStockDisponible());
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener producto: " + ex.getMessage());
        }
    }

    private void actualizarStock(java.awt.event.ActionEvent e) {
        try {
            int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el código del producto:"));
            Producto producto = minimercado.getCatalogo().obtenerProducto(codigoProducto);

            if (producto != null) {
                int nuevoStock = Integer.parseInt(JOptionPane.showInputDialog(this, "Ingrese el nuevo stock disponible:"));
                producto.setStockDisponible(nuevoStock);
                JOptionPane.showMessageDialog(this, "Stock actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar stock: " + ex.getMessage());
        }
    }

    private void mostrarStockBajo(java.awt.event.ActionEvent e) {
        List<Producto> productosConStockBajo = minimercado.getCatalogo().obtenerStockBajo();
        if (productosConStockBajo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay productos con stock bajo.");
        } else {
            StringBuilder listado = new StringBuilder("Productos con stock bajo:\n");
            for (Producto p : productosConStockBajo) {
                listado.append(p.getDescripcion()).append(" - Stock actual: ").append(p.getStockDisponible()).append("\n");
            }
            JOptionPane.showMessageDialog(this, listado.toString());
        }
    }
}
