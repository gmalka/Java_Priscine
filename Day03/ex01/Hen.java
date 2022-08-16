public class Hen extends Thread {
    int number;
    final Object object;
    String name;

    public Hen(int number, Object object, String name) {
        this.name = name;
        this.number = number;
        this.object = object;
    }

    @Override
    public void run() {
        synchronized (object) {
            for (int i = 0; i < number; i++) {
                object.notify();
                try {
                    object.wait();
                } catch (Exception e) {
                    System.err.println("4to to ne tak!");
                    System.exit(-1);
                }
                System.out.println(name);
            }
            object.notify();
        }
    }
}
