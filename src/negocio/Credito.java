package negocio;

public class Credito extends MedioDePago {
    public double recargo=0.0;
    private int cuotas;
    public Credito(){
        super();
        this.cuotas=1;
    }
    public Credito(int cuotas) {
        super();
        this.cuotas = cuotas;
    }
    public int getCuotas() {
        return cuotas;
    }
    public void setCuotas(int cuotas) {
        this.cuotas = cuotas;
    }
    public double calcularRecargo(int cuotas,double monto){
        if (cuotas==1){
            recargo=0.0;
            return recargo;
        }
        else if (cuotas==2){
            recargo=monto*0.06;
            return recargo;
        }else if (cuotas==3){
            recargo=monto*0.12;
            return recargo;
        }else if (cuotas==4){
            recargo=monto*0.20;
            return recargo;
        }
        else {
            recargo=0.0;
            return recargo;
        }
    }

    @Override
    public double procesarPago(double monto) {
        return monto+calcularRecargo(cuotas,monto);
    }
}
