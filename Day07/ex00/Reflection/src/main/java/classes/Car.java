package classes;

public class Car {
    private String  name;
    private int     oil;
    private boolean available;

    public Car()
    {
        name = "Копейка";
        oil = 200;
        available = true;
    }

    public Car(String name, int oil, boolean available) {
        this.name = name;
        this.oil = oil;
        this.available = available;
    }

    public void Drive()
    {
        if (oil > 10) {
            oil -= 10;
            System.out.println(name + " driving!");
        }
        else
            System.out.println("Too low oil: " + oil);
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", oil=" + oil +
                ", available=" + available +
                '}';
    }
}
