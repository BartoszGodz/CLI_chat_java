import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    void main() {

        try (ServerSocket server = new ServerSocket(8080)) {
            IO.println("Server running on 8080");

            while (true) {
                Socket socket = server.accept();
                IO.println("New client connected");

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                //Assigning new thread to a client
                Thread thread = new ClientHandler(socket, input, output);
                thread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


//Creates new thread for each new connection
class ClientHandler extends Thread {
    Socket socket;
    DataInputStream input;
    DataOutputStream output;

    ClientHandler(Socket socket, DataInputStream input, DataOutputStream output) {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            output.writeUTF("Connected to the server, Type EXIT to close connection");
            String username = input.readUTF();
            while (true) {
                String received = input.readUTF();

                if (received.equals("EXIT")) {
                    socket.close();
                    IO.println("Client " + username + " log out");
                    break;
                }

                IO.println("Received new message from: " + username + "| " + received);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}