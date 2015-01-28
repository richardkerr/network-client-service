package kerr.richard.socket.impl;

import kerr.richard.socket.SocketHandler;
import kerr.richard.socket.SocketProcessorFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Simple {@link SocketHandler} implementation which creates a {@link kerr.richard.socket.SocketProcessor} instance
 * and submits it to the given {@link ExecutorService}
 *
 * @author Richard Kerr
 */
public class ThreadedSocketHandler implements SocketHandler {
    private SocketProcessorFactory socketProcessorFactory;
    private ExecutorService executorService;

    public ThreadedSocketHandler(SocketProcessorFactory socketProcessorFactory, ExecutorService executorService) {
        this.socketProcessorFactory = socketProcessorFactory;
        this.executorService = executorService;
    }

    @Override
    public void handleNewSocket(Socket socket) throws IOException {
        executorService.submit(socketProcessorFactory.getSocketProcessorRunnable(socket));
    }
}
