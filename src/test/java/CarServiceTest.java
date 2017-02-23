import org.junit.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CarServiceTest {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static String DRIVER_LOCATION;

    @BeforeClass
    public static void loadProperties() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = CarService.class.getClassLoader().getResourceAsStream("db.properties");
            prop.load(input);
            URL = prop.getProperty("test.db.url");
            USER = prop.getProperty("test.db.username");
            PASSWORD = prop.getProperty("test.db.password");
            DRIVER_LOCATION = prop.getProperty("test.jdbc.driver");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private CarService service;

    @Before
    public void beforeTest(){
        new TestDBData(URL, USER, PASSWORD).flush();
        this.service = new CarService(DRIVER_LOCATION, URL, USER, PASSWORD);
    }

    @After
    public void afterTest(){
        try {
            service.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetEngine(){
        Engine engine = service.getEngineById(1);
        Assert.assertEquals(TestDBData.ENGINE1, engine);//set of cars is not in equals logic
        Assert.assertEquals(TestDBData.ENGINE1.getApplications(), engine.getApplications());
    }

    @Test
    public void testGetEngineNotFound(){
        Assert.assertEquals(null, service.getEngineById(150));
    }

    @Test
    public void testGetCar(){
        Assert.assertEquals(TestDBData.CAR3, service.getCarById(3));
    }

    @Test
    public void testGetCarNotFound(){
        Assert.assertEquals(null, service.getCarById(100));
    }

    @Test
    public void insertEngineTest(){
        Engine engine = TestDBData.ENGINE_NEW;
        int id = service.insertEngine(engine).getId();
        Assert.assertEquals(engine, service.getEngineById(id));
    }

    @Test
    public void insertCarTest(){
        Car car = TestDBData.CAR_NEW;
        int id = service.insertCar(car).getId();
        Assert.assertEquals(car, service.getCarById(id));
    }
}
