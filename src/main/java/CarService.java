import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class CarService implements AutoCloseable{
    private static String URL = "jdbc:postgresql://127.0.0.1:5432/homework5";
    private static String USER = "user";
    private static String PASSWORD = "password";

    private String url;
    private String user;
    private String password;

    private Connection connection;

    public CarService() {
        this.url = URL;
        this.user = USER;
        this.password = PASSWORD;
    }

    public CarService(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Engine getEngineById(int id){
        if(connection == null){
            init();
        }
        Engine engine = null;
        PreparedStatement statement = null;
        String sql = "SELECT id, displacement, power FROM engines WHERE id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if( resultSet.next()) {
                engine = new Engine(resultSet.getInt("id"), resultSet.getInt("displacement"),
                        resultSet.getInt("power"), null);
                engine.setApplications(getCarsWithEngine(engine));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  engine;
    }

    public Engine insertEngine(Engine engine){
        if(connection == null){
            init();
        }
        PreparedStatement statement = null;
        String sql = "INSERT INTO engines (displacement, power) VALUES (?, ?);";
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, engine.getDisplacement());
            statement.setInt(2, engine.getPower());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                engine.setId((int) generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return engine;
    }

    private HashSet<Car> getCarsWithEngine(Engine engine){
        PreparedStatement statement = null;
        if(engine.getId() < 0) {
            return null;
        }
        HashSet<Car> cars = new HashSet<>();
        String sql = "SELECT id, model, make, engine_id, price FROM cars WHERE engine_id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, engine.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                cars.add(new Car(resultSet.getInt("id"), resultSet.getString("model"),
                        resultSet.getString("make"), engine, resultSet.getInt("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car getCarById(int id){
        if(connection == null){
            init();
        }
        Car car = null;
        PreparedStatement statement = null;
        String sql = "SELECT id, model, make, engine_id, price FROM cars WHERE id = ?;";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                car = new Car(resultSet.getInt("id"), resultSet.getString("model"), resultSet.getString("make"),
                        null, resultSet.getInt("price"));
                Engine engine = getEngineById(resultSet.getInt("engine_id"));
                Set<Car> cars = engine.getApplications();
                cars.add(car);
                engine.setApplications(cars);
                car.setEngine(engine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public Car insertCar(Car car){
        if(connection == null){
            init();
        }
        PreparedStatement statement = null;
        String sql = "INSERT INTO cars (model, make, engine_id, price) VALUES (?, ?, ?, ?);";
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, car.getModel());
            statement.setString(2, car.getMake());
            if(car.getEngine() != null) {
                if(getEngineById(car.getEngine().getId()) != null) {
                    statement.setInt(3, car.getEngine().getId());
                }else {
                    Engine engine = insertEngine(car.getEngine());
                    statement.setInt(3, engine.getId());
                    car.setEngine(engine);
                }
            }
            statement.setInt(4, car.getPrice());
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()) {
                car.setId((int) generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    private void init(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
