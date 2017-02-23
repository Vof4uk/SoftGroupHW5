public class Car {
    private int id;
    private String model;
    private String make;
    private Engine engine;
    private int price;

    public Car(int id, String model, String make, Engine engine, int price) {
        this.id = id;
        this.model = model;
        this.make = make;
        this.engine = engine;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (getId() != car.getId()) return false;
        if (getPrice() != car.getPrice()) return false;
        if (getModel() != null ? !getModel().equals(car.getModel()) : car.getModel() != null) return false;
        if (getMake() != null ? !getMake().equals(car.getMake()) : car.getMake() != null) return false;
        return getEngine() != null ? getEngine().equals(car.getEngine()) : car.getEngine() == null;

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + (getModel() != null ? getModel().hashCode() : 0);
        result = 31 * result + (getMake() != null ? getMake().hashCode() : 0);
        result = 31 * result + (getEngine() != null ? getEngine().hashCode() : 0);
        result = 31 * result + getPrice();
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", make='" + make + '\'' +
                ", engine=" + engine +
                ", price=" + price +
                '}';
    }
}
