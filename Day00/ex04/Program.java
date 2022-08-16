import java.util.Scanner;

public class Program {
	private static final int CHAR_SIMBOL_COUNT = 65535;

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        int[]     mas = new int[CHAR_SIMBOL_COUNT];
        int[]     c = new int[10];
        for (int i = 0; i < 10; i++) {
            c[i] = 0;
        }
        String str = scanner.nextLine();
        if (str.isEmpty())
        {
            System.out.println("Error");
            System.exit(-1);
        }
        for(char w : str.toCharArray()) {
            try {
                mas[w]++;
            }
            catch (Exception e)
            {
                System.out.println("Error " + e.getMessage());
                System.exit(-1);
            }
        }
        for (int i = 0; i < mas.length; i++) {
            for (int j = c.length - 1; j >= 0; j--) {
                if (mas[c[j]] < mas[i] || (mas[c[j]] == mas[i] && c[j] > i)) {
                    for (int k = 0; k < j; k++) {
                        c[k] = c[k + 1];
                    }
                    c[j] = i;
                    break;
                }
            }
        }
        ft_make_it_easy(mas, c);
    }

    public static void  ft_make_it_easy(int[] alph, int[] cou)
    {
        double  index = 100.0 / alph[cou[9]];
        int[]   kek = new int[11];

        kek[10] = 10;
        kek[0] = 0;
        for (int i = 9; i >= 0; i--) {
            int e = (int)(((index * alph[cou[i]]) / 10));
            kek[i + 1] = e;
        }
        System.out.println("");
        for (int j = 9; j >= -1; j--) {
            for (int i = 9; i >= 0; i--) {
                if (kek[i + 1] == (j + 1)) {
                    if (alph[cou[i]] >= 0 && alph[cou[i]] <= 9)
                        System.out.print(" " + alph[cou[i]] + " ");
                    else
                        System.out.print(alph[cou[i]] + " ");
                }
                if (kek[i + 1] > (j + 1)) {
                    System.out.print(" # ");
                }
                if (kek[i] < (j + 1))
                {
                    System.out.println("");
                    break;
                }
            }
        }
        System.out.print("\n");
        for (int i = 9; i >= 0; i--) {
            System.out.print(" " + (char)cou[i] + " ");
        }
    }
}


