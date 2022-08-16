import java.util.concurrent.atomic.AtomicLong;

public class Summer extends Thread {
    int[] ar;
    int i, a, id;
    final AtomicLong sum;

    public Summer(int[] ar, int i, int a, int id, AtomicLong sum)
    {
        this.ar = ar;
        this.i = i;
        this.a = a;
        this.id = id + 1;
        this.sum = sum;
    }

    @Override
    public void run() {
        int sum = 0;
        int i = this.i;
        while (i <= a)
        {
            sum += ar[i];
            i++;
        }
        System.out.println("Thread " + id + ": from " + this.i + " to " + this.a + " sum is " + sum);
        synchronized (this.sum)
        {
            this.sum.set(this.sum.get() + sum);
        }
    }
}

