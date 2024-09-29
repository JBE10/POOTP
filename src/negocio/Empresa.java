package negocio;
import java.util.ArrayList;
public class Empresa {
    ArrayList<Minimercado> minimercados;


    public Empresa() {
        minimercados = new ArrayList<>();

    }
    public Empresa(ArrayList<Minimercado> minimercados) {
        this.minimercados = minimercados;

    }

    public double calcularFacturacionTotal() {
        double total=0.0;
        for (Minimercado minimercado : minimercados) {
            total += minimercado.calcularFacturacion();
        }
        return total;
    }
    public void agregarMinimercado(Minimercado minimercado) {
        minimercados.add(minimercado);
        System.out.println("Minimercado agregado correctamente");
    }
    public double calcularFacturacionMinimercado(int codigo){
        for (Minimercado minimercado : minimercados) {
            if(minimercado.getCodigo() == codigo){
                return minimercado.calcularFacturacion();
            }
        }
        System.out.println("Minimercado no encontrado");
        return 0.0;

    }

}

