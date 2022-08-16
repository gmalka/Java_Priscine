import java.io.*;

public class Program {
    public static void main(String[] args) {
        try {
            if (args.length != 1)
            {
                System.err.println("Error: not enough arguments!");
                System.exit(-1);
            }
            String ch = args[0].substring(0, 15);
            if (!ch.equals("--threadsCount="))
            {
                System.err.println("Error: incorrect argument!");
                System.exit(-1);
            }
            int thr_c = 0;
            try {
                thr_c = Integer.parseInt(args[0].substring(15));
                if (thr_c <= 0)
                    throw new Exception();
            }
            catch (Exception e)
            {
                System.err.println("Error: incorrect argument!");
                System.exit(-1);
            }
            Downloader[] th = new Downloader[thr_c];
            BufferedReader reader = new BufferedReader(new FileReader("files_urls.txt"));
            for (int i = 0; i < thr_c; i++) {
                th[i] = new Downloader(reader, i + 1);
                th[i].start();
            }
            for (int i = 0; i < thr_c; i++) {
                th[i].join();
            }
        } catch (Exception e) {
            System.err.println("Something accused");
        }
    }
}

