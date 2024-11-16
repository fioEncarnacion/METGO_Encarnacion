package empresa.android.metgo_encarnacion;

import java.util.List;

public class Parada {

    private String codigo;
    private String nombre;
    private int iconoParada;
    private int[] iconosBuses;

    public Parada(String codigo, String nombre, int iconoParada, int[] iconosBuses) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.iconoParada = iconoParada;
        this.iconosBuses = iconosBuses;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIconoParada() {
        return iconoParada;
    }

    public int[] getIconosBuses() {
        return iconosBuses;
    }
}