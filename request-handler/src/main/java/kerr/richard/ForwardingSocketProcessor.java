package kerr.richard;

import kerr.richard.socket.SocketProcessor;
import kerr.richard.socket.SocketProcessorFactory;

import java.io.IOException;
import java.net.Socket;

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
        try {
            Thread sourceToTarget = new Thread(requestSocket);
            Thread targetToSource = new Thread(targetSocket);

            sourceToTarget.start();
            targetToSource.start();

            sourceToTarget.join();
            targetToSource.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class ForwardingSocketProcessorFactory implements SocketProcessorFactory {
        private static final int DEFAULT_FORWARDING_PORT_NUMBER = 8642;
        private static final String DEFAULT_FORWARDING_IP_ADDRESS = "127.0.0.1";
        private final int port;
        private final String ip;

        public ForwardingSocketProcessorFactory() {
            this(DEFAULT_FORWARDING_IP_ADDRESS, DEFAULT_FORWARDING_PORT_NUMBER);
        }

        public ForwardingSocketProcessorFactory(String ip, int port) {
            this.port = port;
            this.ip = ip;
        }

        @Override
        public Runnable getSocketProcessorRunnable(Socket socket) throws IOException {
            Socket to = new Socket(ip, port);
            return new ForwardingSocketProcessor(new SocketForwarder(socket, to), new SocketForwarder(to, socket));
        }
    }
}
