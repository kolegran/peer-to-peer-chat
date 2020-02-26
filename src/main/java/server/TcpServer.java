package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TcpServer {

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        try {
            final ServerSocket serverSocket = new ServerSocket(8081);
            final Socket clientSocket = serverSocket.accept();
            System.out.println("Connect accepted...");

            CustomThread customThread = new CustomThread(clientSocket);
            customThread.setName("TcpServerThread-1");
            customThread.start();

            while (true) {
                final DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
                final String dataFromClient = inputStream.readUTF();
                System.out.println(dataFromClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class CustomThread extends Thread {
        private final Socket clientSocket;

        public CustomThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                final Scanner scanner = new Scanner(System.in);
                while (scanner.hasNext()) {
                    String text = scanner.nextLine();

                    DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
                    outputStream.writeUTF(text);
                    outputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
