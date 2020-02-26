package server;

import io.ConsoleToSocketWriter;
import io.SocketToConsoleWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private static final int SERVER_INNER_PORT = 8081;
    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        try {
            final ServerSocket serverSocket = new ServerSocket(SERVER_INNER_PORT);
            final Socket clientSocket = serverSocket.accept();
            logger.info("Connect accepted");

            final ConsoleToSocketWriter consoleToSocketWriter = new ConsoleToSocketWriter(clientSocket);
            consoleToSocketWriter.setName("ServerThread");
            consoleToSocketWriter.start();
            logger.info("The ServerThread has been started");

            final SocketToConsoleWriter socketToConsoleWriter = new SocketToConsoleWriter(clientSocket);
            socketToConsoleWriter.write();

            clientSocket.close();
            serverSocket.close();
            logger.info("Sockets were closed");
        } catch (IOException e) {
            logger.error("I/O exception occurs when waiting for a connection or the Socket cannot open");
        }
    }
}
