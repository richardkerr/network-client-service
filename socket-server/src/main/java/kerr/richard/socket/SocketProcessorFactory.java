package kerr.richard.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Interface defining the factory method to create {@link SocketProcessor}
 * instances to handle connected {@link Socket}s
 * @author Richard Kerr
 */
public interface SocketProcessorFactory {
    public SocketProcessor getSocketProcessorRunnable(Socket socket) throws IOException;
}
