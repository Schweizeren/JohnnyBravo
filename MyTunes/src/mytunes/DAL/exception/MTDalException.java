/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.DAL.exception;

/**
 * A class which extends from the exception class. This class is used to throw
 * exceptions in the DAL layer to bundle them up and throw them up to the BLL
 * layer
 * @author Frederik Jensen
 */
public class MTDalException extends Exception
{
    /**
     * A cunstructor of the exception class MTDalException
     */
    public MTDalException() 
    {
        super();
    }
    
    /**
     * A cunstructor of the exception class MTDalException class. 
     * Throws a message if the an exception has been catched
     * @param message the message getting thrown
     */
    public MTDalException(String message)
    {
        super(message);
    }
    
    /**
     * A cunstructor of the exception class MTDalExceptio class.
     * Throws a message and the exception if an exception has been catched
     * @param message the message getting thrown
     * @param ex the exception getting thrown
     */
    public MTDalException(String message, Exception ex)
    {
        super(message, ex);
    }
}
