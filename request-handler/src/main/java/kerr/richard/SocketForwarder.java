package kerr.richard;

import kerr.richard.socket.SocketProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author Richard Kerr
 */
class SocketForwarder implements SocketProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SocketForwarder.class);
    private final Socket source;
    private final Socket target;

    public SocketForwarder(Socket source, Socket target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(source.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(target.getOutputStream()));

            String inLine;
            while ((inLine = reader.readLine()) != null) {
                LOGGER.info("SocketForwarder received and forwarded message: {}", inLine);
                writer.write(inLine + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}