
package Runtime;

/**
 * @author raven
 */
public class RuntimeNewString extends RuntimeEntity {

    @Override
    public void execute(Context context) throws Exception {
        context.getMemory().addVal(new StringValue(""));
        context.setCurrent(context.getCurrent() + 1);
    }

}
