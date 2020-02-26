package io;

import client.TcpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleToSocketWriter extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);
    private final Socket socket;

    public ConsoleToSocketWriter(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            final Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext() && !socket.isClosed()) {
                final String message = scanner.nextLine(); // locked

                final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            logger.error("I/O exception during work with Socket");
        }
    }
}
