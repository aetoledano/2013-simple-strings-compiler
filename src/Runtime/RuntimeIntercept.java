package Runtime;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RuntimeIntercept extends RuntimeOperator {

    @Override
    public void execute(Context context) throws Exception {
        // operands order is inverted int the stack
        StringValue right = (StringValue) context.getStack().pop();
        StringValue left = (StringValue) context.getStack().pop();

        Set<Character> a, b;
        a = new HashSet<>();
        b = new HashSet<>();
        for (char x : left.toString().toCharArray()) {
            a.add(x);
        }
        for (char x : right.toString().toCharArray()) {
            b.add(x);
        }
        a.retainAll(b);
        StringBuilder buffer = new StringBuilder();
        a.forEach(buffer::append);

        StringValue result = new StringValue(buffer.toString());
        context.getStack().push(result);
        context.setCurrent(context.getCurrent() + 1);
    }
}
