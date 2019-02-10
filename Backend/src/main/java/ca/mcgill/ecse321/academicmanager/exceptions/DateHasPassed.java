/**
 * 
 */
package ca.mcgill.ecse321.academicmanager.exceptions;

import java.time.DateTimeException;

/**
 * @author ecse321-winter2019-group18
 *
 */
public class DateHasPassed extends DateTimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create a DateHasPassed Exception with a default message:
	 * "The date provided has passed today. Please provide a different date."
	 */
	public DateHasPassed() {
		super("The date provided has passed today. Please provide a different date.");
	}

	/**
	 * @param message custom message
	 */
	public DateHasPassed(String message) {
		super(message);
	}

}
