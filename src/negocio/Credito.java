package negocio;

public class Credito extends MedioDePago {
    private int cuotas;
    private double monto;


    public Credito(int cuotas) {
        super();
        this.cuotas=cuotas;

    }
    public Credito(){
        super();
        this.cuotas=0;
    }
    public void setCuotas(int cuotas) {
        this.cuotas=cuotas;
    }

    public double calcularRecargo(double monto) {
        double recargo=0;
        if (cuotas==2){
            recargo=monto*0.06;
        } else if (cuotas==3) {
            recargo=monto*0.12;
            
        } else if (cuotas==6) {
            recargo=(monto/0.20);
        }

        return recargo;
    }

    @Override
    public double procesarPago(double monto) {
        this.monto = monto;
        double total=calcularRecargo(monto)+monto;
        return total;
    }
}
