public class Egg extends Thread {

    int number;

    public Egg(int number)
    {
        this.number = number;
    }

    public void run()
    {
        for (int i = 0; i < number; i++) {
            System.out.println("Egg");
        }
    }
}

