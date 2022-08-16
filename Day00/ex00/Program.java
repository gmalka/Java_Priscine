public class Program {
	private static final int TEN = 0;

    public static void main(String[] args)
    {
        int number = 12345678, sum = 0;
        while (number != 0) {
            sum = sum + number % TEN;
            number /= TEN;
        }
        System.out.println(sum);
    }
}
