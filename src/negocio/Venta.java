package negocio;
import negocio.*;
import java.util.ArrayList;

public class Venta {
    ArrayList<Producto> productosVendidos;
    private int opcion=0;
    private MedioDePago medioDePago;
    private double total;
    private String fechaVenta;
    private double monto;
    private Catalogo catalogo;

    public Venta() {
        catalogo = new Catalogo();
        productosVendidos = new ArrayList<>();
        total = 0;
        fechaVenta="";
        monto=0;
    }

    public Venta(Catalogo catalogo,int opcion, double total, String fechaVenta, double monto) {
        this.catalogo = catalogo;
        this.productosVendidos = new ArrayList<>();
        this.opcion = opcion;
        this.total = total;
        this.fechaVenta = fechaVenta;
        this.monto = monto;
    }

    public void agregarProducto(int codigo, int cantidad) {
        Producto p = catalogo.buscarProducto(codigo);
        if (p.getStockDisponible()<cantidad){
            System.out.println("No se puede efectuar la venta por Stock no disponible, su Stock del producto es: "+ p.getStockDisponible());
        }
        else{
            productosVendidos.add(p);
            p.setStockDisponible(p.getStockDisponible()-cantidad);
            System.out.println("producto agregado correctamente");


        }
    }
    public void procesarVenta() {
        for (Producto p : productosVendidos) {
            total += p.getPrecioUnitario();
        }
        // luego se aplica el medio de pago
        total = medioDePago.procesarPago(total);
    }


    public void imprimirTicket(){
        if (total==0){
            System.out.println("venta todavía no efectuada");
        }else{
            for (Producto p: productosVendidos){
                System.out.println(p.getDescripcion()+"("+p.getCodigo()+") $"+p.getPrecioUnitario());

            }
        }
    }

    public void setMedioDePago(MedioDePago medioDePago){
        this.medioDePago=medioDePago;
    }
    public  void setMonto(double monto){
        this.monto=monto;
    }
    public void setTotal(double total){
        this.total=total;
    }
    public void setFechaVenta(String fechaVenta){
        this.fechaVenta=fechaVenta;
    }
    public MedioDePago getMedioDePago() {
        return medioDePago;
    }
    public double getTotal(){
        return total;
    }
    public String getFechaVenta() {
        return fechaVenta;
    }
    public void setOpcion(int opcion){
        this.opcion=opcion;
    }
    public int getOpcion() {
        return opcion;
    }

}
