package negocio;

public class Credito extends MedioDePago {
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
            return 0.0;
        }
        else if (cuotas==2){
            return monto*0.06;
        }else if (cuotas==3){
            return monto*0.12;
        }else if (cuotas==4){
            return monto*0.20;
        }
        else {
            return 0.0;
        }
    }

    @Override
    public double procesarPago(double monto) {
        return monto+calcularRecargo(cuotas,monto);
    }
}
