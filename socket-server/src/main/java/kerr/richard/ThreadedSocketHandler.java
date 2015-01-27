package kerr.richard;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author Richard Kerr
 */
public class ThreadedSocketHandler implements SocketHandler {
    private final SocketProcessorFactory socketProcessorFactory;
    ExecutorService executorService;

    public ThreadedSocketHandler(SocketProcessorFactory socketProcessorFactory, ExecutorService executorService) {
        this.socketProcessorFactory = socketProcessorFactory;
        this.executorService = executorService;
    }

    @Override
    public void handleNewSocket(Socket socket) throws IOException {
        executorService.submit(socketProcessorFactory.getSocketProcessorRunnable(socket));
    }
}
