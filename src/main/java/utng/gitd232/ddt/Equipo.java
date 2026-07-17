package utng.gitd232.ddt;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Equipo {

    private final SimpleStringProperty codigo;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty serie;
    private final SimpleIntegerProperty cantidad;
    private final SimpleStringProperty estado;

    public Equipo(String codigo,
                  String nombre,
                  String serie,
                  int cantidad,
                  String estado){

        this.codigo=new SimpleStringProperty(codigo);
        this.nombre=new SimpleStringProperty(nombre);
        this.serie=new SimpleStringProperty(serie);
        this.cantidad=new SimpleIntegerProperty(cantidad);
        this.estado=new SimpleStringProperty(estado);
    }

    public String getCodigo(){
        return codigo.get();
    }

    public void setCodigo(String c){
        codigo.set(c);
    }

    public String getNombre(){
        return nombre.get();
    }

    public void setNombre(String n){
        nombre.set(n);
    }

    public String getSerie(){
        return serie.get();
    }

    public void setSerie(String s){
        serie.set(s);
    }

    public int getCantidad(){
        return cantidad.get();
    }

    public void setCantidad(int c){
        cantidad.set(c);
    }

    public String getEstado(){
        return estado.get();
    }

    public void setEstado(String e){
        estado.set(e);
    }

}
