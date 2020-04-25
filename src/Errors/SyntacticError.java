/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Errors;

/**
 *
 * @author tomaso
 */


public class SyntacticError extends CompilerError{

    public SyntacticError(int line, String text)
    {
        super(line, text);
    }
}
