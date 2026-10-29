package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Client {
    void main() {

        String name = IO.readln("Enter username: ");

        try (Socket socket = new Socket("localhost", 8080)) {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //Reading welcome message
            IO.println(input.readUTF());
            //Sending username to the server
            output.writeUTF(name);

            //Sending message loop
            while (true) {
                String messege = IO.readln("Enter messege: ");
                output.writeUTF(messege);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
