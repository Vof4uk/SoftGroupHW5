import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CarServiceTest {
    private static String URL = "jdbc:postgresql://127.0.0.1:5432/homework5";
    private static String USER = "user";
    private static String PASSWORD = "password";

    private CarService service;

    @Before
    public void beforeTest(){
        new TestDBData(URL, USER, PASSWORD).flush();
        this.service = new CarService(URL, USER, PASSWORD);
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
