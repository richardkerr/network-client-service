package kerr.richard;

import kerr.richard.socket.SocketHandler;
import kerr.richard.socket.SocketProcessorFactory;
import kerr.richard.socket.SocketServer;
import kerr.richard.socket.impl.ThreadedSocketHandler;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Richard Kerr
 */
public class RequestHandler {
    private static final int DEFAULT_LISTENING_PORT_NUMBER = 2468;
    private int port;
    private SocketHandler socketHandler;

    public RequestHandler(int port, SocketHandler socketHandler) {
        this.port = port;
        this.socketHandler = socketHandler;
    }

    public static void main(String[] args) throws IOException {
        ExecutorService executor = new ThreadPoolExecutor(5, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        SocketProcessorFactory factory = new ForwardingSocketProcessorFactory();
        SocketHandler socketHandler = new ThreadedSocketHandler(factory, executor);
        RequestHandler handler = new RequestHandler(DEFAULT_LISTENING_PORT_NUMBER, socketHandler);
        handler.run();
    }

    private void run() throws IOException {
        SocketServer socketServer = new SocketServer(port, socketHandler);
        socketServer.run();
    }
}
