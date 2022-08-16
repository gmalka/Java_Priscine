import java.util.Scanner;

public class Program {

	private static final int TEN = 10;

    static int sqrt(int num)
    {
        int i = 0;
        while (i * i <= num)
            i++;
        return i;
    }

    static boolean isSimple(int number)
    {
        if (number <= 1)
            return false;
        if (number == 2)
            return true;
        int i = 2, num = sqrt(number);
        while (i <= num)
        {
            if (number % i == 0) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int num = 0, count = 0;
        while ((num = scanner.nextInt()) != 42)
        {
            int sum = 0;
            while (num > 0)
            {
                sum += num % TEN;
                num /= TEN;
            }
            if (isSimple(sum))
                count++;
        }
        System.out.println("Count of coffee-request – " + count);
    }
}
