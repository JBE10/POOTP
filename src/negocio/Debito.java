package negocio;

public class Debito extends MedioDePago{
    public Debito() {
        super();
    }
    @Override
    public double procesarPago(double monto) {
        return monto;
    }



}
