/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL.exception;

/**
 *
 * @author Frederik Jensen
 */
public class MTDalException extends Exception
{
    public MTDalException() {
        super();
    }
    
    public MTDalException(String message) {
        super(message);
    }
    
    public MTDalException(String message, Exception ex) {
        super(message, ex);
    }
}
