package MySqlGest;

public class GUI {
    static MySqlGest gest;
    static QueryFrame query;
    static PrincipalFrame principal;

    public static void main(String[] args) {
        gest = new MySqlGest();
        principal = new PrincipalFrame();
        query = new QueryFrame();
    }
}
