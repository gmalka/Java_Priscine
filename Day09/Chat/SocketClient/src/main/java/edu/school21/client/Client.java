package edu.school21.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            if (args.length != 1)
            {
                System.err.println("Incorrect number of arguments!");
                System.exit(-1);
            }
            if (!args[0].substring(0, 14).equals("--server-port="))
            {
                System.err.println("Incorrect arguments!");
                System.exit(-1);
            }
            int port = Integer.parseInt(args[0].substring(14));
            String result = "";

            Scanner scanner = new Scanner(System.in);
            Socket socket = new Socket("localhost", port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            System.out.println("Hello from Server!");
            out.write(scanner.nextLine() + "\n");
            out.flush();
            while (in.readLine().equals("Error")) {
                System.out.println(in.readLine());
                out.write(scanner.nextLine() + "\n");
                out.flush();
            }
            while (!result.equals("Successful!")) {
                System.out.println("Enter username:");
                out.write(scanner.nextLine() + "\n");
                out.flush();
                System.out.println("Enter password:");
                out.write(scanner.nextLine() + "\n");
                out.flush();
                result = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
