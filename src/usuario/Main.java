//package usuario;
//
//import negocio.*;
//
//import java.util.Scanner;
//
//public class Main {
//    static Detalle e=new Detalle();
//    public static void main(String[] args) {
//        Scanner sc=new Scanner(System.in);
//        int opcion=0;
//        do {
//            System.out.println("INGRESE UNA OPCIÓN");
//            System.out.println("(1) Agregar minimercado.");
//            System.out.println("(2) Ingresar al sistema de un minimercado.");
//            System.out.println("(3) Obtener facturación total de la empresa.");
//            System.out.println("(4) Eliminar minimercado");
//            System.out.println("(5) salir");
//            opcion=sc.nextInt();
//            switch (opcion) {
//                case 1:
//                    agregarMinimercado(sc);
//                    break;
//                case 2:
//                    ingresarMinimercado(sc);
//                    break;
//                case 3:
//                    imprimirFacturacionTotal(e);
//                case 4:
//                    eliminarMinimercado(sc);
//
//
//            }
//
//        }while((opcion== 1) || (opcion==2) || (opcion==3)|| (opcion==4));
//    }
//    public static void agregarMinimercado(Scanner sc){
//        System.out.println("Ingrese el codigo minimercado:");
//        int codigo=sc.nextInt();
//        System.out.println("Ingrese el nombre del minimercado:");
//        String nombre=sc.next();
//        Minimercado m=new Minimercado(codigo,nombre);
//        e.agregarMinimercado(m);
//
//    }
//    public  static void ingresarMinimercado(Scanner sc){
//        System.out.println("Ingrese el codigo minimercado:");
//        int codigo=sc.nextInt();
//        Minimercado m= e.obtenerMinimercadoPorCodigo(codigo);
//        if (m==null){
//            System.out.println("El codigo minimercado no existe");
//            return;
//        }else{
//            int opcion=0;
//            do {
//                System.out.println("INGRESE UNA OPCION: ");
//                System.out.println("(1) Obtener facturación minimercado.");
//                System.out.println("(2) Registrar venta");
//                System.out.println("(3) Mostrar catalogo");
//                System.out.println("(4) obtener stock de un producto");
//                System.out.println("(5) Obtener lista de productos con stock bajo");
//                System.out.println("(6) Agregar productos");
//                System.out.println("(7) eliminar producto");
//                System.out.println("(8) actualizar stock");
//                System.out.println("(9) salir");
//                opcion=sc.nextInt();
//                switch (opcion) {
//                    case 1:
//                        mostrarFacturacion(m);
//                        break;
//                    case 2:
//                        registrarVenta(m,sc);
//                        break;
//                    case 3:
//                        mostrarCatalogo(m);
//                        break;
//                    case 4:
//                        ProductoStockBajo(m,sc);
//                        break;
//                    case 5:
//                        obtenerListaStockBajo(m,sc);
//                        break;
//                    case 6:
//                        registrarProductos(m,sc);
//                        break;
//                    case 7:
//                        eliminarProducto(m,sc);
//                        break;
//                    case 8:
//                        actualizarStock(m,sc);
//                        break;
//
//
//
//                }
//
//            }while ((opcion==1)|| (opcion==2)||(opcion==3)||(opcion==4)||(opcion==5)||(opcion==8)||(opcion==6)||(opcion==7));
//        }
//
//    }
//    public static void mostrarFacturacion(Minimercado m){
//        System.out.println("La facturacion del minimercado "+m.getNombre()+" es "+m.getFacturacion());;
//
//    }
//    public static void registrarVenta(Minimercado m, Scanner sc) {
//        Venta v = new Venta();
//        int codigo = 0;
//
//        // Agregar productos a la venta
//        do {
//            System.out.println("Ingrese código de producto o -1 para finalizar carga:");
//            codigo = sc.nextInt();
//            if (codigo != -1) {
//                Producto producto = m.getCatalogo().obtenerProducto(codigo);
//                if (producto != null) {
//                    System.out.println("Ingrese la cantidad de ese producto:");
//                    int cantidad = sc.nextInt();
//                    v.agregarProductos(producto, cantidad);
//                } else {
//                    System.out.println("Producto no encontrado.");
//                }
//            }
//        } while (codigo != -1);
//
//        // Solicitar medio de pago
//        int opcion;
//        MedioDePago medioDePago = null;
//        do {
//            System.out.println("Ingrese el medio de pago con el que va a abonar: (1) débito (2) efectivo (3) crédito");
//            opcion = sc.nextInt();
//            switch (opcion) {
//                case 1:
//                    medioDePago = new Debito();
//                    break;
//                case 2:
//                    medioDePago = new Efectivo();
//                    break;
//                case 3:
//                    // Manejo para tarjeta de crédito
//                    int cuotas = 0;
//                    boolean cuotasValidas = false;
//                    while (!cuotasValidas) {
//                        System.out.println("Ingrese las cuotas (1, 2, 3 o 6):");
//                        cuotas = sc.nextInt();
//                        if (cuotas == 1 || cuotas == 2 || cuotas == 3 || cuotas == 6) {
//                            cuotasValidas = true;
//                        } else {
//                            System.out.println("Cuotas no válidas. Intente nuevamente.");
//                        }
//                    }
//                    medioDePago = new Credito(cuotas);
//                    break;
//                default:
//                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
//                    break;
//            }
//        } while (medioDePago == null);
//
//        // Asignar medio de pago y procesar la venta
//        v.setmedioDePago(medioDePago);
//        v.procesarVenta();
//        m.agregarVenta(v);
//        System.out.println("TOTAL A PAGAR: $"+v.getTotal());
//    }
//    public static void mostrarCatalogo(Minimercado m){
//        //m.getCatalogo().imprimirCatalogo();
//    }
//    public static void registrarProductos(Minimercado m, Scanner sc) {
//
//        int codigo = 0;
//        while (codigo != -1) {
//            System.out.println("Ingrese el codigo del producto o -1 para finalizar carga: ");
//            codigo = sc.nextInt();
//            if (codigo != -1) {
//                System.out.println("Ingrese el nombre del producto: ");
//                String nombre = sc.next();
//                System.out.println("ingrese el precio del producto");
//                double precio = sc.nextDouble();
//                while (precio < 0) {
//                    System.out.println("Precio invalido.Ingrese el precio del producto: ");
//                    precio = sc.nextDouble();
//                }
//                System.out.println("Ingrese el stock minimo de reposicion del producto: ");
//                int stockMinimo = sc.nextInt();
//                System.out.println("Ingrese el stock actual del producto: ");
//                int stockActual = sc.nextInt();
//                Producto p = new Producto(codigo, nombre, precio, stockMinimo, stockActual);
//                m.getCatalogo().agregarProducto(p);
//            }
//        }
//    }
//    public static void obtenerListaStockBajo(Minimercado m,Scanner sc){
//        m.getCatalogo().obtenerStockBajo();
//
//    }
//    public static void ProductoStockBajo(Minimercado m,Scanner sc){
//        int codigo=0;
//        while(codigo!=-1){
//            System.out.println("Ingrese el codigo del producto o -1 para finalizar validaciones: ");
//            codigo=sc.nextInt();
//            Producto p=m.getCatalogo().obtenerProducto(codigo);
//            if (p!=null){
//                if (p.stockBajo()==true){
//                    System.out.println("stock: "+p.getStockDisponible());
//                    System.out.println("Producto con stock bajo");
//                }else{
//                    System.out.println("stock: "+p.getStockDisponible());
//                    System.out.println("Producto sin stock bajo");
//                }
//            }
//        }
//    }
//    public static void eliminarProducto(Minimercado m,Scanner sc){
//        int codigo=0;
//        while(codigo!=-1){
//            System.out.println("Ingrese el codigo del producto o -1 para finalizar eliminacion: ");
//            codigo=sc.nextInt();
//            Producto p=m.getCatalogo().obtenerProducto(codigo);
//            if (p!=null){
//                m.getCatalogo().eliminarProducto(p);
//            }
//        }
//    }
//    public static void imprimirFacturacionTotal(Detalle e){
//        System.out.println("Facturacion total:"+e.obtenerFacturacionTotal());
//    }
//    public static void actualizarStock(Minimercado m,Scanner sc){
//        int codigo=0;
//        while(codigo!=-1){
//            System.out.println("Ingrese el codigo del producto o -1 para finalizar actualizacion: ");
//            codigo=sc.nextInt();
//            Producto p=m.getCatalogo().obtenerProducto(codigo);
//            if (p!=null){
//                System.out.println("Ingrese la cantidad de productos a agregar: ");
//                int cantidad=sc.nextInt();
//                while(cantidad<0){
//                    System.out.println("Error.Ingrese el cantidad de productos a agregar: ");
//                    cantidad=sc.nextInt();
//                }
//                p.actualizarStock(cantidad);
//            }
//        }
//    }
//    public static void eliminarMinimercado(Scanner sc){
//        System.out.println("Ingrese  el coidgo de minimercado para eliminar");
//        int codigo=sc.nextInt();
//        e.eliminarMinimercado(codigo);
//
//    }
//}
