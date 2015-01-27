package kerr.richard;

import kerr.richard.socket.SocketProcessorFactory;

import java.net.Socket;

/**
* @author Richard Kerr
*/
public class CalculatorSocketProcessorFactory implements SocketProcessorFactory {
    @Override
    public Runnable getSocketProcessorRunnable(Socket socket) {
        return new CalculatorSocketProcessor(socket);
    }
}
