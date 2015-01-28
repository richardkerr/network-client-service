package kerr.richard;

import kerr.richard.socket.SocketProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Richard Kerr
 */
public class CalculatorSocketProcessor implements SocketProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorSocketProcessor.class);
    private final Calculator calculator;
    private final Socket socket;

    public CalculatorSocketProcessor(Socket socket, Calculator calculator) {
        this.socket = socket;
        this.calculator = calculator;
    }

    @Override
    public void run() {
        try(Socket sct = socket) {
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
        } catch (SocketException e) {
            LOGGER.info("Calculator socket ({}) was closed unexpectedly ({})", socket.getRemoteSocketAddress(), e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
