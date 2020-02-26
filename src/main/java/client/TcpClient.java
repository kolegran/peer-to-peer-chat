package client;

import io.ConsoleToSocketWriter;
import io.SocketToConsoleWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class TcpClient {
    private static final int SERVER_PORT = 8081;
    private static final String SERVER_HOST = "localhost";
    private static final Logger logger = LoggerFactory.getLogger(TcpClient.class);

    public static void main(String[] args) {
        runClient();
    }

    public static void runClient() {
        try {
            final Socket socket = new Socket(SERVER_HOST, SERVER_PORT);

            final ConsoleToSocketWriter consoleToSocketWriter = new ConsoleToSocketWriter(socket);
            consoleToSocketWriter.setName("ClientThread");
            consoleToSocketWriter.start();
            logger.info("The ClientThread has been started");

            final SocketToConsoleWriter socketToConsoleWriter = new SocketToConsoleWriter(socket);
            socketToConsoleWriter.write();

            socket.close();
            logger.info("Client Socket was closed");
        } catch (IOException e) {
            logger.error("I/O exception during creating the Socket");
        }
    }
}
