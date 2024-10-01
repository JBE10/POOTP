package usuario;

import negocio.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainGUI {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton crearMinimercadoButton;
    private JButton ingresarMinimercadoButton;
    private JButton obtenerFacturacionButton;
    private JButton salirDelSistemaButton;
    private Empresa empresa;

    public MainGUI() {
        empresa = new Empresa();

        mainFrame = new JFrame("Sistema de Minimercados");
        mainPanel = new JPanel();

        crearMinimercadoButton = new JButton("Crear Minimercado");
        ingresarMinimercadoButton = new JButton("Ingresar a Minimercado");
        obtenerFacturacionButton = new JButton("Obtener Facturación");
        salirDelSistemaButton = new JButton("Salir del Sistema");

        mainPanel.add(crearMinimercadoButton);
        mainPanel.add(ingresarMinimercadoButton);
        mainPanel.add(obtenerFacturacionButton);
        mainPanel.add(salirDelSistemaButton);

        obtenerFacturacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double facturacion = empresa.calcularFacturacionTotal();
                JOptionPane.showMessageDialog(mainPanel, "Facturación: $" + facturacion);
            }

        });

        crearMinimercadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = JOptionPane.showInputDialog(mainFrame, "Ingrese el nombre del minimercado:");
                int codigo = Integer.parseInt(JOptionPane.showInputDialog(mainFrame, "Ingrese el número del minimercado:"));

                Minimercado nuevoMinimercado = new Minimercado();
                nuevoMinimercado.setCodigo(codigo);
                nuevoMinimercado.setNombre(nombre);
                empresa.agregarMinimercado(nuevoMinimercado);

                JOptionPane.showMessageDialog(mainFrame, "Minimercado creado correctamente.");
            }
        });


        ingresarMinimercadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int codigo = Integer.parseInt(JOptionPane.showInputDialog(mainFrame, "Ingrese el número del minimercado:"));
                Minimercado minimercado = empresa.buscarMinimercado(codigo);

                if (minimercado == null) {
                    JOptionPane.showMessageDialog(mainFrame, "Minimercado inexistente.");
                } else {
                    abrirMenuMinimercado(minimercado);
                }
            }
        });

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void abrirMenuMinimercado(Minimercado minimercado) {
        JFrame minimercadoFrame = new JFrame("Menú del Minimercado");
        JPanel minimercadoPanel = new JPanel();


        JButton agregarProductoButton = new JButton("Agregar Producto");
        JButton realizarVentaButton = new JButton("Realizar Venta");
        JButton actualizarStockButton = new JButton("Actualizar Stock");
        JButton obtenerFacturacionButton = new JButton("Obtener Facturación");
        JButton verCatalogoButton = new JButton("Ver Catálogo");
        JButton productosStockBajoButton = new JButton("Productos con Stock Bajo");


        agregarProductoButton.addActionListener(e -> {
            int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese el código del producto:"));
            String descripcion = JOptionPane.showInputDialog(minimercadoFrame, "Ingrese la descripción:");
            double precioUnitario = Double.parseDouble(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese el precio unitario:"));
            int stockDisponible = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese el stock disponible:"));
            int stockMinimo = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese el stock mínimo:"));

            Producto producto = new Producto(codigoProducto, descripcion, precioUnitario, stockDisponible, stockMinimo);
            minimercado.getCatalogo().agregarProducto(producto);

            JOptionPane.showMessageDialog(minimercadoFrame, "Producto agregado correctamente.");
        });

        realizarVentaButton.addActionListener(e -> {
            Catalogo c = minimercado.getCatalogo();
            c.mostrarCatalogoCliente();  // Mostrar catálogo
            Venta v = new Venta(c);
            int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese el código de producto o -1 para finalizar:"));
            while (codigoProducto != -1) {
                if (c.buscarProducto(codigoProducto) == null) {
                    JOptionPane.showMessageDialog(minimercadoFrame, "Producto inexistente.");
                } else {
                    int cantidad = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese cantidad de unidades:"));
                    v.agregarProducto(codigoProducto, cantidad);
                }
                codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese código de producto o -1 para finalizar:"));
            }

            int medioPago = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Seleccione el medio de pago: Efectivo (1), Débito (2), Crédito (3)"));
            MedioDePago medioDePago;
            switch (medioPago) {
                case 1 -> medioDePago = new Efectivo();
                case 2 -> medioDePago = new Debito();
                case 3 -> {
                    int cuotas = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese la cantidad de cuotas: 2, 3, 6"));
                    medioDePago = new Credito(cuotas);
                }
                default -> {
                    JOptionPane.showMessageDialog(minimercadoFrame, "Opción inválida. Se seleccionará Efectivo por defecto.");
                    medioDePago = new Efectivo();
                }
            }

            v.setMedioDePago(medioDePago);
            v.procesarVenta();
            minimercado.registrarVenta(v);
            JOptionPane.showMessageDialog(minimercadoFrame, "Venta registrada correctamente.");
        });

        actualizarStockButton.addActionListener(e -> {
            int codigoProducto = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese código del producto:"));
            if (minimercado.getCatalogo().buscarProducto(codigoProducto) == null) {
                JOptionPane.showMessageDialog(minimercadoFrame, "Producto inexistente.");
            } else {
                int cantidad = Integer.parseInt(JOptionPane.showInputDialog(minimercadoFrame, "Ingrese cantidad extra de stock:"));
                minimercado.getCatalogo().actualizarStock(codigoProducto, cantidad);
                JOptionPane.showMessageDialog(minimercadoFrame, "Stock actualizado correctamente.");
            }
        });

        obtenerFacturacionButton.addActionListener(e -> {
            double facturacion = minimercado.calcularFacturacion();
            JOptionPane.showMessageDialog(minimercadoFrame, "Facturación: $" + facturacion);
        });

        verCatalogoButton.addActionListener(e -> {
            Catalogo c = minimercado.getCatalogo();
            if (c == null) {
                JOptionPane.showMessageDialog(minimercadoFrame, "El catálogo está vacío.");
            } else {
                StringBuilder catalogo = new StringBuilder();
                List<Producto> productos = c.mostrarCatalogoEmpleado();
                for (Producto p : productos) {
                    catalogo.append(p.getCodigo())
                            .append(" - ")
                            .append(p.getDescripcion())
                            .append(" (Stock: ")
                            .append(p.getStockDisponible())
                            .append(")\n");
                }

                JOptionPane.showMessageDialog(minimercadoFrame, catalogo.toString(), "Catálogo", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        productosStockBajoButton.addActionListener(e -> {
            Catalogo c = minimercado.getCatalogo();
            if (c != null) {
                StringBuilder productosBajoStock = new StringBuilder();
                List<Producto> productos = c.mostrarProductosStockBajo();
                for (Producto p : productos) {
                    productosBajoStock.append(p.getCodigo())
                            .append(" - ")
                            .append(p.getDescripcion())
                            .append(" (Stock actual: ")
                            .append(p.getStockDisponible())
                            .append(", Stock mínimo: ")
                            .append(p.getStockMinimo())
                            .append(")\n");
                }
                JOptionPane.showMessageDialog(minimercadoFrame, productosBajoStock.toString(), "Productos con Stock Bajo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(minimercadoFrame, "No hay productos con stock bajo.");
            }
        });


        minimercadoPanel.add(agregarProductoButton);
        minimercadoPanel.add(realizarVentaButton);
        minimercadoPanel.add(actualizarStockButton);
        minimercadoPanel.add(obtenerFacturacionButton);
        minimercadoPanel.add(verCatalogoButton);
        minimercadoPanel.add(productosStockBajoButton);


        minimercadoFrame.add(minimercadoPanel);
        minimercadoFrame.pack();
        minimercadoFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new MainGUI();
    }
}




