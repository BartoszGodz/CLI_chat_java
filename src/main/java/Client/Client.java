package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
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

            Thread listener = new MessageListener(input);
            Thread sender = new MessageSender(output);
            listener.start();
            sender.start();

            sender.join();     //If message sender terminates, connection lost
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}

class MessageSender extends Thread {
    private final DataOutputStream output;

    MessageSender(DataOutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            String userInput = IO.readln("~ ");

            try {
                output.writeUTF(userInput);
            } catch (IOException e) {
                IO.println("Unable to send message: " + e);
                return;
            }

            if ("EXIT".equals(userInput)) {
                return;
            }
        }
    }
}


class MessageListener extends Thread {
    private final DataInputStream input;

    MessageListener(DataInputStream input) {
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while (true) {
                IO.println(input.readUTF());
            }
        } catch (EOFException e) {
            IO.println("Disconnected from the server");
        } catch (IOException e) {
            IO.println("Error receiving messages: " + e.getMessage());
        }
    }
}