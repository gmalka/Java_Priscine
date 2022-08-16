import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Program {
    public static void main(String[] argv)
    {
        int arrSize = 0, thrCount = 0;

        if (argv.length != 2)
        {
            System.err.println("Not enough count of arguments!");
            System.exit(-1);
        }
        try {
            if (argv[0].startsWith("--arraySize=")) {
                arrSize = Integer.parseInt(argv[0].substring(12));
            }
            if (argv[1].startsWith("--threadsCount=")) {
                thrCount = Integer.parseInt(argv[1].substring(15));
            }
            if (arrSize > 2000000 || thrCount <= 0 || thrCount > arrSize)
            {
                System.err.println("Illigal argument!");
                System.exit(-1);
            }
        }
        catch (Exception e)
        {
            System.err.println("Error: Illigal Argument!");
            System.exit(-1);
        }
        int[]   ar = new int[arrSize];
        int     len = 0;
        try
        {
            len = arrSize / thrCount;
        }
        catch (ArithmeticException e)
        {
            len = arrSize;
        }
        catch (Exception e)
        {
            System.err.println("Something very wrong!");
            System.exit(-1);
        }

        if ((arrSize - len * (thrCount - 1)) > len && (len + 1) * (thrCount  - 1) < arrSize) {
            len++;
        }
        int last_len = arrSize - len * (thrCount - 1), cur_len = 0;
        AtomicLong sum = new AtomicLong();
        Random r = new Random();
        Summer[] th = new Summer[thrCount];
        for (int i = 0; i < ar.length; i++) {
            ar[i] = r.nextInt(2000) - 1000;
        }
        System.out.println("Sum: " + Arrays.stream(ar).sum());
        for (int i = 0; i < thrCount - 1; i++) {
            th[i] = new Summer(ar, cur_len, (cur_len = cur_len + len) - 1, i, sum);
            th[i].start();
        }
        try {
            th[th.length - 1] = new Summer(ar, cur_len, cur_len + last_len - 1, th.length - 1, sum);
            th[th.length - 1].start();
            for (Summer thread : th) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (Exception ignored)
        {}
        System.out.println("Sum by threads: " + sum.get());
    }
}

