 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.BLL.exception;

/**
 *
 * @author Frederik Jensen
 */
public class MTBllException extends Exception
{
    /**
     * The constructor of the MTBllException class
     * Throws a message if an exception occures
     * @param message the message getting thrown
     */
    public MTBllException(String message) {
        super(message);
    }
}
