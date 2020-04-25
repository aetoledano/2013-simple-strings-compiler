/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Errors;

/**
 *
 * @author tomaso
 */



public class LexicalError extends CompilerError{
    
    public LexicalError(int line, String text)
    {
        super(line, text);
    }
}
