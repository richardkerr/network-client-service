package kerr.richard;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Richard Kerr
 */
public interface SocketProcessorFactory {
    public Runnable getSocketProcessorRunnable(Socket socket) throws IOException;
}
