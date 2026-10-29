package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

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
            //TODO: name format
            String username = input.readUTF();
            while (true) {
                String received = input.readUTF();

                if (received.equals("EXIT")) {
                    IO.println("Client " + username + " log out");
                    socket.close();
                    break;
                }

                IO.println("Received new message from: " + username + "| " + received);

                //TODO: Sending new message to users

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}