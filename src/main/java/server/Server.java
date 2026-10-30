package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    void main() {

        try (ServerSocket server = new ServerSocket(8080)) {
            IO.println("server.Server running on 8080");

            while (true) {
                Socket socket = server.accept();
                IO.println("New client connected");

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                //Adding user output stream to userSet
                UserOutputSet.userSet.add(output);

                //Assigning new thread to a client
                Thread thread = new ClientHandler(socket, input, output);
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}