package kerr.richard;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Richard Kerr
 */
public interface SocketHandler {
    public void handleNewSocket(Socket socket) throws IOException;
}
