package kerr.richard.it;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Richard Kerr
 */
public class SocketServerIT {


    @Test
    public void testMultiConnectionHandling() throws IOException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            service.submit(new MathSocketTester(new Socket("127.0.0.1", 2468)));
        }
        Thread.sleep(60000);
        service.shutdownNow();
    }
}
