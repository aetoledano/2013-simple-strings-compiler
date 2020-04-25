/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Runtime;

import OUT.InOut;

/**
 * @author frodo
 */
public class RuntimeHalt extends RuntimeOperator {
    public RuntimeHalt() {
        super();
    }

    @Override
    public void execute(Context context) {
        context.setHalt(true);
        InOut.Write("Terminated. :-)", null);
    }
}
