package Runtime;

public class RuntimeConcat extends RuntimeOperator {

    @Override
    public void execute(Context context) throws Exception {
        // operands order is inverted int the stack
        StringValue r = (StringValue) context.getStack().pop();
        StringValue l = (StringValue) context.getStack().pop();
        StringValue result = new StringValue(l.toString() + r.toString());
            context.getStack().push(result);
        context.setCurrent(context.getCurrent() + 1);
    }
}
