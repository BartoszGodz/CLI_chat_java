package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

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
        String username = null;
        try (Socket clientSocket = socket) {
            output.writeUTF("Connected to the server, Type EXIT to close connection");

            username = input.readUTF();

            while (true) {
                String received = input.readUTF();

                if (received.equals("EXIT")) {
                    IO.println("Client " + username + " log out");
                    break;
                }

                String message = username + "| " + received;

                IO.println("Received new message from: " + message);


                UserOutputSet.userSet.forEach(output -> {
                    try {
                        output.writeUTF(message);
                    } catch (IOException e) {
                        throw new RuntimeException("Error when sending message to user" + e);
                    }
                });

            }

        } catch (SocketException e) {
            IO.println("Client " + (username == null ? "without a username" : username) + " disconnected");
        } catch (IOException e) {
            throw new RuntimeException("Error communicating with client", e);
        } finally {
            UserOutputSet.userSet.remove(output);
        }
    }
}