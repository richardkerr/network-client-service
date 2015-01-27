package kerr.richard;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Stack;

/**
 * @author Richard Kerr
 */
public class Calculator {
    /**
     * Handles maths expressions, for full details on the handling of syntax
     * refer to {@see <a href="http://www.objecthunter.net/exp4j"/>http://www.objecthunter.net/exp4j/</a>}
     * @param input
     * @return
     */
    public String handleRequest(String input) {
        Expression expression;
        try {
            expression = new ExpressionBuilder(input).build();
        } catch (IllegalArgumentException exception) {
            return exception.getMessage();
        }

        ValidationResult result = expression.validate(true);
        if(result.isValid()) {
            return String.valueOf(expression.evaluate());
        } else {
            return StringUtils.join(result.getErrors(), ", ");
        }
    }
}
