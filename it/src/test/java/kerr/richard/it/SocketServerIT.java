package kerr.richard.it;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Richard Kerr
 */
public class SocketServerIT {


    @Test
    public void testMultiConnectionHandling() throws IOException, InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(100);
        List<Callable<Exception>> runs = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            runs.add(new MathSocketTester(new Socket("127.0.0.1", 2468)));
        }
        List<Future<Exception>> futures = service.invokeAll(runs, 60, TimeUnit.SECONDS);

        for(Future<Exception> future : futures) {
            try {
                Exception e = future.get();
                if (e != null) {
                    Assert.fail(e.toString());
                    return;
                }
            } catch (ExecutionException e1) {
                Assert.fail(e1.getMessage());
            } catch (CancellationException e2) {
                // Expect threads to be cancelled
            }

        }

        service.shutdownNow();
    }
}
