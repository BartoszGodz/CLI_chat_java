package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    void main() {

        String name = this.getUsername();

        try (Socket socket = new Socket("localhost", 8080)) {
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            DataInputStream input = new DataInputStream(socket.getInputStream());
            //Reading welcome message
            IO.println(input.readUTF());
            //Sending username to the server
            output.writeUTF(name);

            Thread listener = new MessageListener(input);
            Thread sender = new MessageSender(output);
            listener.start();
            sender.start();

            sender.join();     //If message sender terminates, connection will end
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private String getUsername() {
        String userName = IO.readln("Enter username (max 10 characters): ");
        if (userName.length() > 10) {
            userName = userName.substring(0, 10);
        } else {
            String missingSpace = " ".repeat(10 - userName.length());
            userName = userName + missingSpace;
        }
        return userName;
    }

}