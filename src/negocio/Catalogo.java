package negocio;

import java.util.ArrayList;

public class Catalogo {
    private ArrayList<Producto> productos;

    public Catalogo() {
        this.productos = new ArrayList<Producto>();
    }

    public Catalogo(ArrayList<Producto> productos) {
        this.productos = productos;
    }

    public ArrayList<Producto> devolverCatalogo() {
       return  this.productos;
    }

    public void obtenerStockBajo() {
        System.out.println("Los siguientes productos necesitan reposición.");
        System.out.println();
        for (Producto producto : productos) {
            if (producto.stockBajo()) {
                System.out.println("Codigo: " + producto.getIdProducto() + " Descripcion: " + producto.getDescripcion() + " Precio: " + producto.getPrecio() + " Stock disponible: " + producto.getStockDisponible() + " Stock minimo: " + producto.getStockMinimo());

            }
        }
    }

    public void agregarProducto(Producto producto) {
        for (Producto producto2 : productos) {
            if (producto2.getIdProducto() == producto.getIdProducto()) {
                System.out.println("producto ya agregado anteriormente. Actualice stock");
                return;
            }
        }
        productos.add(producto);
        System.out.println("Producto agregado correctamente.");
    }

    public void eliminarProducto(Producto producto) {
        boolean bandera = false;

        for (int i = productos.size() - 1; i >= 0; i--) {
            Producto producto2 = productos.get(i);
            if (producto2.getIdProducto() == producto.getIdProducto()) {
                productos.remove(i);
                bandera = true;
                System.out.println("Producto eliminado correctamente.");
                break;
            }
        }

        if (!bandera) {
            System.out.println("Producto no encontrado.");
        }
    }

    public Producto obtenerProducto(int id) {
        for (Producto producto2 : productos) {
            if (producto2.getIdProducto() == id) {
                return producto2;
            }
        }
        System.out.println("Producto no encontrado.");
        return null;
    }

}
