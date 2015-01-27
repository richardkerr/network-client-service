package kerr.richard;

import kerr.richard.socket.SocketProcessorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author Richard Kerr
 */
public class CalculatorSocketProcessor implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorSocketProcessor.class);
    private static final Calculator calculator = new Calculator();
    private static final String MATH_REGEX = "^[0-9+/\\-*\\s]+$";
    private final Socket socket;

    public CalculatorSocketProcessor(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String inLine;
            String response;
            while ((inLine = reader.readLine()) != null) {
                LOGGER.info("Received request: {}", inLine);
                response = calculator.handleRequest(inLine);
                LOGGER.info("Writing response: {}", response);
                writer.write(response + "\n");
                writer.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (!socket.isClosed()) {
                    LOGGER.info("Closing socket: {}", socket.getLocalPort());
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
