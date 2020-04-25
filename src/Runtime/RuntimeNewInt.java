/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Runtime;

/**
 *
 * @author raven
 */
public class RuntimeNewInt extends RuntimeEntity {

    @Override
    public void execute(Context context) throws Exception {
        context.getMemory().addVal(new IntValue());
        context.setCurrent( context.getCurrent() + 1);
    }

}
