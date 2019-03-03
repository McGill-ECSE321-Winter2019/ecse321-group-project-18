package ca.mcgill.ecse321.academicmanager.model;

/**
 * A Term may have 3 statuses:
 * Ongoing (in progress), Finished (the term is finished and the Student has passed the course),
 * Failed (the term is finished but the Student failed the course).
 * @author ecse321-winter2019-group18
 * @see Term
 * */
public enum TermStatus
{
	ONGOING, FINISHED, FAILED;
}