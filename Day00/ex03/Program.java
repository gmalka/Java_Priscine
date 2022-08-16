import java.util.Scanner;

public class Program {
    private static final int COUNT_OF_RATING_PER_WEEK = 5, MAX_WEEK = 18;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        long min = 0;
        int i = 0, wnum = 0;
        String str;
        try {
            while (wnum < MAX_WEEK && !(str = scanner.nextLine()).equals("42")) {
                if (str.isEmpty() || str.equals("\n"))
                    continue;
                wnum++;
                if (wnum != Integer.parseInt(str.substring(5))) {
                    System.err.println("IllegalArgument");
                    System.exit(-1);
                }
                int c = 0;
                min = min * 10 + 9;
                while (c++ < COUNT_OF_RATING_PER_WEEK) {
                    int cur = scanner.nextInt();
                    if (cur <= 0 || cur >= 10) {
                        System.err.println("IllegalArgument");
                        System.exit(-1);
                    }
                    if ((min % 10) > cur) {
                        min /= 10;
                        min = min * 10 + cur;
                    }
                }
                scanner.nextLine();
            }
            ft_printWeeks(min);
        }
        catch (Exception e)
        {
            System.out.println("Error!");
            System.exit(-1);
        }
    }

    public static int ft_printWeeks(long min)
    {
        if (min <= 9 && min >= 1)
        {
            int i = 1;
            System.out.print("Week ");
            System.out.print(i);
            System.out.print("\t");
            for (int j = 0; j < min; j++) {
                System.out.print("=");
            }
            System.out.println(">");
            return i + 1;
        }
        int f = ft_printWeeks(min / 10);
        System.out.print("Week ");
        System.out.print(f);
        System.out.print("\t");
        for (int j = 0; j < min % 10; j++) {
            System.out.print("=");
        }
        System.out.println(">");
        return f + 1;
    }
}
