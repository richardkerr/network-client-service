package kerr.richard;

import kerr.richard.socket.SocketProcessor;
import kerr.richard.socket.SocketProcessorFactory;

import java.net.Socket;

/**
* @author Richard Kerr
*/
public class CalculatorSocketProcessorFactory implements SocketProcessorFactory {
    @Override
    public SocketProcessor getSocketProcessorRunnable(Socket socket) {
        return new CalculatorSocketProcessor(socket, Calculator.INSTANCE);
    }
}
