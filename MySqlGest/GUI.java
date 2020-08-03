package MySqlGest;

public class GUI {
    static MySqlGest gest;
    static PrincipalFrame principal;

    public static void main(String[] args) {
        gest = new MySqlGest();
        principal = new PrincipalFrame();
    }
}
