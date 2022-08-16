import java.util.Scanner;

public class Program {

    public static int sqrt(int num) {
        int i = 1;
        while (i * i <= num)
            i++;
        return i;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt())
        {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        int i = scanner.nextInt(), n, e = 0;
        if (i <= 1) {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
        if (i == 2)
        {
            System.out.println("true 1");
            System.exit(0);
        }
        n = sqrt(i);
        for (int j = 2; j <= n; j++) {
            e++;
            if (i % j == 0) {
                System.out.println("false " + e);
                System.exit(0);
            }
        }
        System.out.println("true " + e);
    }
}
