package negocio;

public class Producto implements Stock {
    private int codigo;
    private String descripcion;
    private double precioUnitario;
    private int stockMinimo;
    private int stockDisponible;

    public Producto(int codigo, String descripcion, double precioUnitario, int stockMinimo, int stockDisponible) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioUnitario=precioUnitario;
        this.stockDisponible=stockDisponible;
        this.stockMinimo=stockMinimo;
    }
    public Producto() {
        codigo = 0;
        descripcion="";
        precioUnitario=0;
        stockMinimo=0;
        stockDisponible=0;
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public int getStockDisponible() {
        return stockDisponible;
    }
    public void setStockDisponible(int stockDisponible) {
        this.stockDisponible = stockDisponible;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    public int getStockMinimo() {
        return stockMinimo;
    }
    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }


    @Override
    public boolean stockBajo() {
        return false;
    }
}
