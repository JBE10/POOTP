package usuario;

public class Main {
    static Empresa e = new Empresa();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Ingrese una opción: crear minimercado (1) | ingresar al sistema de un minimercado (2) | obtener facturación total (3) | salir del sistema (4)");
            opcion = sc.nextInt();
            switch (opcion) {
                case 1:
                    String nombre;
                    int codigo;
                    System.out.println("Ingrese un número de minimercado: ");
                    codigo = sc.nextInt();
                    sc.nextLine(); // Limpiar el buffer
                    System.out.println("Ingrese el nombre del minimercado: ");
                    nombre = sc.nextLine();
                    Minimercado m = new Minimercado();
                    m.setCodigo(codigo);
                    m.setNombre(nombre);
                    e.agregarMinimercado(m);
                    System.out.println("Minimercado creado correctamente.");
                    break;

                case 2:
                    int opcion2;
                    System.out.println("Ingrese el número del minimercado: ");
                    codigo = sc.nextInt();
                    Minimercado m1 = e.buscarMinimercado(codigo);

                    if (m1 == null) {
                        System.out.println("Minimercado inexistente.");
                    } else {
                        Catalogo c = m1.getCatalogo();

                        do {
                            System.out.println("Agregar producto (1) | Realizar venta (2) | Actualizar Stock (3) | Obtener Facturación (4) | Mostrar catálogo (5) | Mostrar productos con stock bajo (6) | Volver al menú anterior (7)");
                            opcion2 = sc.nextInt();
                            switch (opcion2) {
                                case 1:
                                    Producto p = new Producto();
                                    System.out.println("Ingrese el código del producto: ");
                                    p.setCodigo(sc.nextInt());
                                    System.out.println("Ingrese la descripción del producto: ");
                                    p.setDescripcion(sc.next());
                                    System.out.println("Ingrese el precio unitario: ");
                                    p.setPrecioUnitario(sc.nextDouble());
                                    System.out.println("Ingrese el stock mínimo del producto: ");
                                    p.setStockMinimo(sc.nextInt());
                                    System.out.println("Ingrese el stock del producto: ");
                                    p.setStockDisponible(sc.nextInt());
                                    c.agregarProducto(p);
                                    System.out.println("Producto agregado correctamente.");
                                    break;


                                case 2:
                                    c.mostrarCatalogoCliente();
                                    int codigoProducto;
                                    Venta v = new Venta(c);
                                    System.out.println("Ingrese código de producto o -1 para finalizar carga: ");
                                    codigoProducto = sc.nextInt();
                                    while (codigoProducto != -1) {
                                        if (c.buscarProducto(codigoProducto) == null) {
                                            System.out.println("Producto inexistente.");
                                        } else {
                                            int cantidad = 0;
                                            System.out.println("Ingrese cantidad de unidades: ");
                                            cantidad = sc.nextInt();
                                            v.agregarProducto(codigoProducto, cantidad);
                                        }
                                        System.out.println("Ingrese código de producto o -1 para finalizar venta:");
                                        codigoProducto = sc.nextInt();
                                    }

                                    // Nuevo: Ingreso de medio de pago
                                    System.out.println("Seleccione el medio de pago: Efectivo (1) | Débito (2) | Crédito (3)");
                                    int medioPago = sc.nextInt();
                                    MedioDePago medioDePago;
                                    switch (medioPago) {
                                        case 1:
                                            medioDePago = new Efectivo();
                                            break;
                                        case 2:
                                            medioDePago = new Debito();
                                            break;
                                        case 3:
                                            System.out.println("Ingrese la cantidad de cuotas (2, 3, 6): ");
                                            int cuotas = sc.nextInt();
                                            medioDePago = new Credito(cuotas);
                                            break;
                                        default:
                                            System.out.println("Opción inválida. Se seleccionará Efectivo por defecto.");
                                            medioDePago = new Efectivo();
                                            break;
                                    }

                                    // Asignar medio de pago y procesar la venta
                                    v.setMedioDePago(medioDePago);
                                    v.procesarVenta();
                                    m1.registrarVenta(v);
                                    System.out.println("Venta registrada correctamente con medio de pago.");
                                    break;

                                case 3:
                                    System.out.println("Ingrese código de producto: ");
                                    codigoProducto = sc.nextInt();
                                    if (c.buscarProducto(codigoProducto) == null) {
                                        System.out.println("El producto no fue agregado.");
                                    } else {
                                        System.out.println("Ingrese la cantidad extra de stock: ");
                                        int cantidad = sc.nextInt();
                                        c.actualizarStock(codigoProducto, cantidad);
                                        System.out.println("Stock actualizado correctamente.");
                                    }
                                    break;

                                 case 4:

                                    System.out.println("Facturación del minimercado: " + m1.calcularFacturacion());
                                    break;

                                 case 5: if(c==null){
                                    System.out.println("El cantalogo esta vacion");
                                }
                                else{
                                    c.mostrarCatalogoEmpleado();
                                }
                                    break;

                                case 6: if(c==null){

                                }else {
                                    c.mostrarProductosStockBajo();
                                    break;
                                }

                                case 7:
                                    System.out.println("Volviendo al menú principal...");
                                    break;

                                default:
                                    System.out.println("Opción inválida.");
                                    break;
                            }
                        } while (opcion2 != 7); // Volver al menú principal
                    }
                    break;

                case 3:
                    System.out.println("Facturación total de la empresa: " + e.calcularFacturacionTotal());
                    break;

                case 4:
                    System.out.println("Saliendo del sistema...");
                    opcion = 99; // Para salir del bucle
                    break;

                default:
                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcion != 99);

        sc.close();
    }
}
