package edu.school21.sockets.server;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.UserServiceImpl;
import edu.school21.sockets.services.UsersService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void makeServer(String[] args) {
        if (args.length != 1)
        {
            System.err.println("Incorrect number of arguments!");
            System.exit(-1);
        }
        if (!args[0].substring(0, 7).equals("--port="))
        {
            System.err.println("Incorrect arguments!");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0].substring(7));

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String command = in.readLine();
            System.out.println(command);
            while (!command.equals("signUp"))
            {
                out.write("Error\n");
                out.flush();
                command = in.readLine();
            }
            out.write("OK\n");
            out.flush();
            ApplicationContext context = new AnnotationConfigApplicationContext(SocketsApplicationConfig.class);
            UsersService service = context.getBean(UsersService.class);

            service.saveUser(in.readLine(), in.readLine());

            out.write("Successful!\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
