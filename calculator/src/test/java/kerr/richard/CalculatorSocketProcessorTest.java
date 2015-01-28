package kerr.richard;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;

/**
 * @author Richard Kerr
 */
@RunWith(MockitoJUnitRunner.class)
public class CalculatorSocketProcessorTest {

    @Mock
    private Calculator calculator;

    @Mock
    private Socket socket;

    @Test
    public void testCalculatorSocketProcessor() throws IOException {
        CalculatorSocketProcessor socketProcessor = new CalculatorSocketProcessor(socket, calculator);
        InputStream is = new ByteArrayInputStream("5+5\n".getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Mockito.when(socket.getInputStream()).thenReturn(is);
        Mockito.when(socket.getOutputStream()).thenReturn(os);
        Mockito.when(calculator.handleRequest(any(String.class))).thenReturn("mocked value");
        socketProcessor.run();

        Mockito.verify(calculator).handleRequest("5+5");
        assertEquals("mocked value\n", os.toString());
    }
}
