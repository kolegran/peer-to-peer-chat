package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {

    public static void main(String[] args) {
        runClient();
    }

    public static void runClient() {
        try {
            Socket socket = new Socket("localhost", 8081);

            CustomThread customThread = new CustomThread(socket);
            customThread.setName("TcpClientThread-1");
            customThread.start();

            while (true) {

                final DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                final String dataFromServer = inputStream.readUTF();
                System.out.println(dataFromServer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class CustomThread extends Thread {
        private final Socket socket;

        public CustomThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                final Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String text = scanner.nextLine();

                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                    outputStream.writeUTF(text);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
