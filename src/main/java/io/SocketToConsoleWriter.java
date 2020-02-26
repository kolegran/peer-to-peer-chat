package io;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketToConsoleWriter {
    private final Socket socket;

    public SocketToConsoleWriter(Socket socket) {
        this.socket = socket;
    }

    public void write() throws IOException {
        while (!socket.isClosed()) {
            final DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            final String dataFromClient = inputStream.readUTF();
            System.out.println(dataFromClient);
        }
    }
}
