package negocio;

import java.util.ArrayList;

public class Cliente {
    private String nombre;
    private int Dni;
    private String mail;
    private String telefono;

    public Cliente() {

    }

    public Cliente(String nombre, int Dni, String mail, String telefono) {
        this.nombre = nombre;
        this.Dni = Dni;
        this.mail = mail;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDni() {
        return Dni;
    }

    public void setDni(int Dni) {
        this.Dni = Dni;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ArrayList<Venta> registroCompras(Minimercado minimercado) {
        ArrayList<Venta> ventas = new ArrayList<>();

        ArrayList<Venta> ventasTotales = minimercado.getVentas();
        for (Venta venta : ventasTotales) {
            if (venta.getCliente().getDni()==this.getDni()){
                ventas.add(venta);
            }
        }
        return ventas;




    }
}


