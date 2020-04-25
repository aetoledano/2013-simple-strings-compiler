/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Runtime;

/**
 *
 * @author frodo
 */
public class RuntimeCargaDir extends RuntimeOperator
{
    public RuntimeCargaDir()
    {
        super();
    }

    @Override
    public  void execute(Context context)
    {
        Integer address = ((IntValue)context.getCode().get(context.getCurrent() + 1)).getValue();
        context.getStack().push(address);
        context.setCurrent(context.getCurrent() + 2);
    }
}
