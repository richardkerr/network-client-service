package kerr.richard;

import kerr.richard.socket.SocketProcessor;
import kerr.richard.socket.SocketProcessorFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Richard Kerr
 */
public class ForwardingSocketProcessor implements SocketProcessor {
    private final SocketForwarder requestSocket;
    private final SocketForwarder targetSocket;

    public ForwardingSocketProcessor(SocketForwarder requestSocket, SocketForwarder targetSocket) {
        this.requestSocket = requestSocket;
        this.targetSocket = targetSocket;
    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(requestSocket);
        executorService.submit(targetSocket);
        executorService.shutdown();
    }

}
