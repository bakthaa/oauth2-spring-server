/**
 * 
 */
package xyz.baktha.oaas.data.exception;

/**
 * @author power-team
 *
 */
public class InvalidUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2723936767299247682L;
	
	public InvalidUserException(String msg) {
		
		super(msg);
	}

}
