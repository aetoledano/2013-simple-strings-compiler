package Runtime;

import OUT.InOut;

public class RuntimePrint extends RuntimeOperator {


    @Override
    public void execute(Context context) throws Exception {
        Object obj = context.getStack().pop();
        context.setCurrent(context.getCurrent() + 1);

        InOut.Write(">>> " + obj, null);
    }
}
