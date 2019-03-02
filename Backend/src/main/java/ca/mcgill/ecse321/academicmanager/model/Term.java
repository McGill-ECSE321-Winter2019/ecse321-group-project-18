package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.HashSet;
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
	
	private String termName;

	public void setTermName(String value) {
		this.termName = value;
	}
	public String getTermName() {
		return this.termName;
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
	
	@OneToMany(mappedBy="term", fetch=FetchType.EAGER)
	public Set<CoopTermRegistration> getCoopTermRegistration() {
		return this.coopTermRegistration;
	}
	
	public void setCoopTermRegistration(Set<CoopTermRegistration> coopTermRegistrations) {
	   this.coopTermRegistration = coopTermRegistrations;
	}

	public void addCoopTermRegistration(CoopTermRegistration coopTermRegistrations) {
		try {
			if(!this.coopTermRegistration.contains(coopTermRegistrations)) {
				this.coopTermRegistration.add(coopTermRegistrations);
				coopTermRegistrations.setTerm(this);
			}
		}
		catch(Exception e) {
			this.coopTermRegistration = new HashSet<CoopTermRegistration>();
			this.coopTermRegistration.add(coopTermRegistrations);
			coopTermRegistrations.setTerm(this);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coopEvalFormDeadline == null) ? 0 : coopEvalFormDeadline.hashCode());
		result = prime * result + ((studentEvalFormDeadline == null) ? 0 : studentEvalFormDeadline.hashCode());
		result = prime * result + ((termID == null) ? 0 : termID.hashCode());
		result = prime * result + ((termName == null) ? 0 : termName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Term other = (Term) obj;
		if (coopEvalFormDeadline == null) {
			if (other.coopEvalFormDeadline != null)
				return false;
		} else if (!coopEvalFormDeadline.equals(other.coopEvalFormDeadline))
			return false;
		if (studentEvalFormDeadline == null) {
			if (other.studentEvalFormDeadline != null)
				return false;
		} else if (!studentEvalFormDeadline.equals(other.studentEvalFormDeadline))
			return false;
		if (termID == null) {
			if (other.termID != null)
				return false;
		} else if (!termID.equals(other.termID))
			return false;
		if (termName == null) {
			if (other.termName != null)
				return false;
		} else if (!termName.equals(other.termName))
			return false;
		return true;
	}
	
}
