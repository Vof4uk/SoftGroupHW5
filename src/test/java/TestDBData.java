import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TestDBData {
    private static String SQL = "DROP TABLE IF EXISTS cars;\n" +
                                "CREATE TABLE cars(\n" +
                                "  id SERIAL PRIMARY KEY,\n" +
                                "  model VARCHAR(32),\n" +
                                "  make VARCHAR(16),\n" +
                                "  engine_id INT,\n" +
                                "  price INT\n" +
                                ");\n" +
                                "\n" +
                                "DROP TABLE IF EXISTS engines;\n" +
                                "CREATE TABLE engines(\n" +
                                "  id SERIAL PRIMARY KEY,\n" +
                                "  displacement INT,\n" +
                                "  power INT\n" +
                                ");\n" +
                                "\n" +
                                "INSERT INTO cars(model, make, engine_id, price) VALUES \n" +
                                "  ('20clk', 'mercedez', 1, 200000),\n" +
                                "  ('TT', 'audi', 1, 1200000),\n" +
                                "  ('lanos', 'daewoo', 1, 1400),\n" +
                                "  ('passat', 'volkswagen', 2, 15000);\n" +
                                "\n" +
                                "INSERT INTO engines(displacement, power) VALUES \n" +
                                "  (1200, 230),\n" +
                                "  (1400, 400)\n" +
                                ";";

    static final Engine ENGINE1;
    static final Engine ENGINE2;
    static final Engine ENGINE_NEW;
    static final Car CAR1;
    static final Car CAR2;
    static final Car CAR3;
    static final Car CAR4;
    static final Car CAR_NEW;


    static {
        ENGINE1 = new Engine(1, 1200, 230, null);
        ENGINE2 = new Engine(2, 1400, 400, null);
        CAR1 = new Car(1, "20clk", "mercedez", ENGINE1, 200000);
        CAR2 = new Car(2, "TT", "audi", ENGINE1, 1200000);
        CAR3 = new Car(3, "lanos", "daewoo", ENGINE1, 1400);
        CAR4 = new Car(4, "passat", "volkswagen", ENGINE2, 15000);

        ENGINE_NEW = new Engine(3, 666, 666, null);
        CAR_NEW = new Car(5, "juke", "nissan", ENGINE2, 2000);

        Set<Car> cars1 = new HashSet<>();
        cars1.add(CAR1);
        cars1.add(CAR2);
        cars1.add(CAR3);

        Set<Car> cars2 = new HashSet<>();
        cars2.add(CAR4);

        ENGINE1.setApplications(cars1);
        ENGINE2.setApplications(cars2);
    }

    private String url;
    private String username;
    private String password;

    public TestDBData(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void flush(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            connection.prepareStatement(SQL).execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
