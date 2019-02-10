/**
 * 
 */
package ca.mcgill.ecse321.academicmanager.exceptions;

/**
 * A NullArgumentException is thrown when null(s) are input into one of the method's arguments.
 * @author ecse321-winter2019-group18
 *
 */
public class NullArgumentException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a NullArgumentException object with its default message.
	 */
	public NullArgumentException() {
		super("one or more argument(s) is/are null/empty");
	}

	/**
	 * Create a NullArgumentException with a custom message.
	 * @param s custom message
	 */
	public NullArgumentException(String s) {
		super(s);
	}

}
