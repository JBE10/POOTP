package negocio;
import java.util.ArrayList;

public class Minimercado {
    String nombre;
    int codigo;
    Catalogo catalogo;
    ArrayList<Venta> ventas;
    public Minimercado(String nombre, int codigo, Catalogo catalogo, ArrayList<Venta> ventas) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.catalogo = catalogo;
        this.ventas= ventas;

    }
    public Minimercado() {
        this.nombre = "";
        this.codigo = 0;
        this.catalogo = new Catalogo(); // Inicializar un nuevo catálogo aquí
        this.ventas = new ArrayList<Venta>(); // También inicializamos la lista de ventas
    }


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public Catalogo getCatalogo() {
        return catalogo;
    }
    public void registrarVenta(Venta venta) {
        this.ventas.add(venta);
    }
    public double calcularFacturacion(){
        double suma=0.0;
        for (Venta v: ventas){
            suma+=v.getTotal();
        }
        return suma;
    }

}
