package kerr.richard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Richard Kerr
 */
public class SocketServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketServer.class);
    private final int port;
    private final SocketHandler handler;

    public SocketServer(int port, SocketHandler handler) {
        this.port = port;
        this.handler = handler;
    }

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                LOGGER.info("New connection: {}", socket.getRemoteSocketAddress());
                handler.handleNewSocket(socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
