package kerr.richard;

import kerr.richard.socket.SocketProcessor;
import kerr.richard.socket.SocketProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

/**
* @author Richard Kerr
*/
public class ForwardingSocketProcessorFactory implements SocketProcessorFactory {
    private static final int DEFAULT_FORWARDING_PORT_NUMBER = 8642;
    private static final String DEFAULT_FORWARDING_IP_ADDRESS = "127.0.0.1";
    private static final Logger LOGGER = LoggerFactory.getLogger(ForwardingSocketProcessorFactory.class);
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
    public SocketProcessor getSocketProcessorRunnable(Socket socket) throws IOException {
        try {
            Socket to = new Socket(ip, port);
            return new ForwardingSocketProcessor(new SocketForwarder(socket, to), new SocketForwarder(to, socket));
        } catch (ConnectException e) {
            LOGGER.warn("Unable to open target socket ({}:{})", ip, port);
            socket.close();
            throw e;
        }
    }
}
