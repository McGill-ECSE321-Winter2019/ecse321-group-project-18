package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;
import java.util.Set;
import javax.persistence.OneToMany;

/**
 * Represents a Term (semester) in a school year.
 * A deadline to submit evaluation forms is determined for each term.
 * @see Form
 * @author ecse321-winter2019-group18
 * */
@Entity
public class Term
{
	private String termID;
	   
	public void setTermID(String value) {
		this.termID = value;
	}
	@Id
	public String getTermID() {
		return this.termID;
	}
	private Date studentEvalFormDeadline;
	
	public void setStudentEvalFormDeadline(Date value) {
		this.studentEvalFormDeadline = value;
	}
	public Date getStudentEvalFormDeadline() {
		return this.studentEvalFormDeadline;
	}
	private Date coopEvalFormDeadline;
	
	public void setCoopEvalFormDeadline(Date value) {
		this.coopEvalFormDeadline = value;
	}
	public Date getCoopEvalFormDeadline() {
		return this.coopEvalFormDeadline;
	}
	private Set<CoopTermRegistration> coopTermRegistration;
	
	@OneToMany(mappedBy="term")
	public Set<CoopTermRegistration> getCoopTermRegistration() {
		return this.coopTermRegistration;
	}
	
	public void setCoopTermRegistration(Set<CoopTermRegistration> coopTermRegistrations) {
	   this.coopTermRegistration = coopTermRegistrations;
	}
}
