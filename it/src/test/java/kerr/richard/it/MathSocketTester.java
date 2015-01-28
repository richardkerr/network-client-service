package kerr.richard.it;

import org.junit.Assert;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;


/**
 * @author Richard Kerr
 */
public class MathSocketTester implements Callable<Exception> {

    public static volatile Exception exception = null;

    private static final List<Pair> questionResponsePair = new ArrayList<>();

    static {
        questionResponsePair.add(new Pair("5+5", "10.0"));
        questionResponsePair.add(new Pair("11*6", "66.0"));
        questionResponsePair.add(new Pair("120/4", "30.0"));
        questionResponsePair.add(new Pair("98-43", "55.0"));
        questionResponsePair.add(new Pair("(5+6)*3", "33.0"));
        questionResponsePair.add(new Pair("13+4", "17.0"));
        questionResponsePair.add(new Pair("4-9", "-5.0"));
        questionResponsePair.add(new Pair("55/11", "5.0"));
    }

    private final Socket socket;

    public MathSocketTester(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Exception call() {
        Random random = new Random();
        Pair p;
        String response;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
            while (true) {
                Thread.sleep(random.nextInt(1000)); // Sleep 0-5 seconds
                p = questionResponsePair.get(random.nextInt(questionResponsePair.size() - 1));
                writer.write(p.question);
                writer.newLine();
                writer.flush();
                response = reader.readLine();
            }
        } catch (IOException e) {
            return e;
        } catch (InterruptedException e) {
            return null;
        }
    }

    private static class Pair {
        public String question;
        public String response;

        public Pair(String question, String response) {
            this.question = question;
            this.response = response;
        }
    }
}
