package Runtime;

import java.util.HashSet;
import java.util.Set;

public class RuntimeSubstring extends RuntimeOperator {

    @Override
    public void execute(Context context) throws Exception {
        // operands order is inverted int the stack
        IntValue right = (IntValue) context.getStack().pop();
        StringValue left = (StringValue) context.getStack().pop();

        StringValue result;
        try {
            result = new StringValue(left.toString().substring(right.getValue()));
        } catch (Exception any) {
            result = new StringValue(any.getMessage());
        }
        context.getStack().push(result);
        context.setCurrent(context.getCurrent() + 1);
    }
}
