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
public class CalculatorHandler {
    private final SocketHandler socketHandler;
    private SocketServer socketServer;

    public CalculatorHandler(SocketHandler socketHandler) {
        this.socketHandler = socketHandler;
    }

    public static void main(String[] args) throws IOException {
        SocketProcessorFactory factory = new CalculatorSocketProcessor.CalculatorSocketProcessorFactory();
        ExecutorService executorService = new ThreadPoolExecutor(5, 100, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
        SocketHandler socketHandler = new ThreadedSocketHandler(factory, executorService);
        CalculatorHandler calculator = new CalculatorHandler(socketHandler);
        calculator.run();
    }

    private void run() throws IOException {
        socketServer = new SocketServer(8642, socketHandler);
        socketServer.run();
    }
}
