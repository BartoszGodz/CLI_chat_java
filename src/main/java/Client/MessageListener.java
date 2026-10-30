package Client;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;

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