/**
 * 
 */
package ca.mcgill.ecse321.academicmanager.exceptions;

import java.time.DateTimeException;

/**
 * InvalidEndTime is thrown when customer input an endTime happens before the startTime.
 * For example, suppose customer creates a Meeting starts at 16:00:00 and ends at 15:00:00, this meeting
 * does not comply with time constraints since the endTime happens before the startTime.
 * @author ecse321-winter2019-group18
 *
 */
public class InvalidEndTimeException extends DateTimeException {

	/**
	 * Throw InvalidEndTime with its default message:
	 * "endTime occurs before startTime. Please provide different endTime."
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEndTimeException()
	{
		super("endTime occurs before startTime. Please provide different endTime.");
	}
	/**
	 * Throw InvalidEndTime with a custom message.
	 * @param message
	 */
	public InvalidEndTimeException(String message) {
		super(message);
	}


}
