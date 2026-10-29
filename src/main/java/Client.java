import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Client {

    void main() {
        try (Socket socket = new Socket("localhost", 8080)){
            IO.println("Connected to Serwer");

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            String messege = "abc";
            output.writeUTF(messege);

            output.flush();
            output.close();





        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
