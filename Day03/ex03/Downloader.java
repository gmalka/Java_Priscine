import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Downloader extends Thread {
    int id;
    final BufferedReader reader;

    public Downloader(BufferedReader reader, int id) {
        this.id = id;
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            String URL;
            String[] s;
            int index;
            while (true) {
                synchronized (reader) {
                    if (!reader.ready())
                        return;
                    URL = reader.readLine();
                    s = URL.split(" ");
                    index = Integer.parseInt(s[0]);
                    System.out.println("Thread " + id + " start read file number " + index);
                }
                String[] str = s[1].split("/+");
                InputStream in = new URL(s[1]).openStream();
                Files.copy(in, new File(str[str.length - 1]).toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Thread " + id + " end read file number " + index);
            }
        } catch (Exception e) {
            System.err.println("Something accused");
        }
    }
}

