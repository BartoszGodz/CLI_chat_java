import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    void main(){

        try(ServerSocket server = new ServerSocket(8080)) {
            IO.println("Server running on 8080");

            try(Socket socket = server.accept()) {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String message = input.readUTF();
                IO.println("Received: " + message);

                //Sending back
                //DataOutputStream output = new DataOutputStream(socket.getOutputStream();
                //output.writeUTF("Recived");
                //output.flush();

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
