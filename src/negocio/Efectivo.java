package negocio;

public class Efectivo extends MedioDePago{
    public Efectivo(){
        super();
    }
    @Override
    public double procesarPago(double monto) {
        monto=monto-calcularDescuento(monto);
        return monto;
    }

    public double calcularDescuento(double monto){
        double descuento=monto/0.10;
        return descuento;
    }


}
