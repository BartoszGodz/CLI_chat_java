package Client;

import java.io.DataOutputStream;
import java.io.IOException;

class MessageSender extends Thread {
    private final DataOutputStream output;

    MessageSender(DataOutputStream output) {
        this.output = output;
    }

    @Override
    public void run() {
        while (true) {
            String userInput = IO.readln();

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
