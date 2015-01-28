package kerr.richard;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Richard Kerr
 */
@RunWith(Parameterized.class)
public class CalculatorTest {

    private final String input, expected;

    public CalculatorTest(String input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameterized.Parameters
    public static Collection<String[]> values() {
        return Arrays.asList(new String[][] {
                {"5+5","10.0"},
                {"gfd","Unable to parse setVariable or function starting at pos 0 in expression 'gfd'"}
        });
    }

    @Test
    public void testCalculator() {
        Assert.assertEquals(expected,Calculator.INSTANCE.handleRequest(input));
    }
}
