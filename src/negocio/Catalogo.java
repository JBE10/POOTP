package negocio;
import java.util.ArrayList;
public class Catalogo implements Stock {
    ArrayList<Producto> productos;
    public Catalogo() {}
    public Catalogo(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    public Producto buscarProducto(int codigo) {
        for (Producto p : productos){
            if (p.getCodigo()==codigo){
                return p;
            }


        }
        System.out.println("Producto no encontrado");
        return null;
    }
    public void agregarProducto(Producto producto) {
        boolean bandera=false;
        if (productos.isEmpty()){
            productos.add(producto);
        }
        else{
            for (Producto p : productos) {
                if (p.getCodigo() == producto.getCodigo()) {
                    System.out.println("producto ya agregado anteriormente. Si lo desea, puede actualizar stock");
                    bandera = true;
                }
            }
            if (!bandera) {
                productos.add(producto);
            }
        }

    }

    public void actualizarStock(int codigo,int cantidad){
        Producto p= this.buscarProducto(codigo);
        if (p==null){

        }else{
            int cant= p.getStockDisponible();
            cant=cant+cantidad;
            p.setStockDisponible(cant);
            System.out.println("Stock actualizado correctamente");
        }

    }
    public void mostrarCatalogoCliente(){
        System.out.println("CATÁLOGO");
        for (Producto p: productos){
            System.out.println("Código: "+p.getCodigo()+" Descripcion: "+p.getDescripcion()+" Precio: "+p.getPrecioUnitario());

        }
    }
    public void mostrarCatalogoEmpleado(){
        if (productos==null){
            System.out.println("El catalogo esta vacio");
        }
        else{
            System.out.println("CATALOGO");
            for (Producto p : productos) {
                System.out.println("Código: " + p.getCodigo() + " Descripcion: " + p.getDescripcion() + " Precio: " + p.getPrecioUnitario() + " Stock: " + p.getStockDisponible() + " StockMinimo: " + p.getStockMinimo());

            }
            this.mostrarProductosStockBajo();
        }


    }

    public void mostrarProductosStockBajo(){
        System.out.println("Los siguientes productos necesitan reposición");
        for (Producto p: productos){
            if (p.stockBajo()){
                System.out.println("El producto "+p.getDescripcion()+"("+p.getCodigo()+") necesita una reposición mínima de "+ (p.getStockMinimo()-p.getStockDisponible())+" unidades.");

            }
        }

    }

    public boolean stockBajo(){
        boolean bandera=false;
        for(Producto p: productos){
            if (p.stockBajo()){
                bandera=true;
            }
        }
        return bandera;
    }


}
