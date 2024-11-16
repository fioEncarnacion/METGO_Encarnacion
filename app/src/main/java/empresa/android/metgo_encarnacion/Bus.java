package empresa.android.metgo_encarnacion;

public class Bus {

    private String code;
    private String name;
    private int icon; // Resource ID for the icon

    public Bus(String code, String name, int icon) {
        this.code = code;
        this.name = name;
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
