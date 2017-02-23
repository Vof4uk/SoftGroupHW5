import java.util.Set;

public class Engine {
    private int id;
    private int displacement;
    private int power;
    private Set<Car> applications;

    public Engine(int id, int displacement, int power, Set<Car> applications) {
        this.id = id;
        this.displacement = displacement;
        this.power = power;
        this.applications = applications;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Set<Car> getApplications() {
        return applications;
    }

    public void setApplications(Set<Car> applications) {
        this.applications = applications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Engine engine = (Engine) o;

        if (getId() != engine.getId()) return false;
        if (getDisplacement() != engine.getDisplacement()) return false;
        return getPower() == engine.getPower();

    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getDisplacement();
        result = 31 * result + getPower();
        return result;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "id=" + id +
                ", displacement=" + displacement +
                ", power=" + power +
                '}';
    }
}
