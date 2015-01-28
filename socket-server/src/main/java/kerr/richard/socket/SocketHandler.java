package kerr.richard.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * An interface which allows generic handling of a new socket connection to a {@link SocketServer}
 * @author Richard Kerr
 */
public interface SocketHandler {
    public void handleNewSocket(Socket socket) throws IOException;
}
