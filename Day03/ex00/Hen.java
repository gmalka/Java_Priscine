public class Hen extends Thread {

    int number;

    public Hen(int number)
    {
        this.number = number;
    }

    public void run()
    {
        for (int i = 0; i < number; i++) {
            System.out.println("Hen");
        }
    }
}

