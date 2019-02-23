package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * A CoopTermRegistration object is created when a Student register for a course.
 * Each registration has a unique ID and therefore stores necessary information on the course
 * and its term.
 * @author ecse321-winter2019-group18
 * @since 2019-02-10
 */
@Entity
public class CoopTermRegistration
{
private Grade grade;
   
   public void setGrade(Grade value) {
this.grade = value;
    }
public Grade getGrade() {
return this.grade;
    }
	private String registrationID;
   
   public void setRegistrationID(String value) {
	   this.registrationID = value;
   }
	@Id
	public String getRegistrationID() {
		return this.registrationID;
	}
	private TermStatus termStatus;
	
	public void setTermStatus(TermStatus value) {
		this.termStatus = value;
	}
	public TermStatus getTermStatus() {
		return this.termStatus;
	}
	private Set<Form> form;
	
	@OneToMany(mappedBy="coopTermRegistration")
	public Set<Form> getForm() {
	   return this.form;
	}
	
	public void setForm(Set<Form> forms) {
	   this.form = forms;
	}
	
	private Term term;
	
	@ManyToOne
	public Term getTerm() {
	   return this.term;
	}
	
	public void setTerm(Term term) {
	   this.term = term;
	}
	
	private String jobID;
	
	public void setJobID(String value) {
		this.jobID = value;
	}
	public String getJobID() {
		return this.jobID;
	}
	private Student student;
	
	@OneToOne(mappedBy="coopTermRegistration", optional=false,cascade = CascadeType.ALL)
	public Student getStudent() {
	   return this.student;
	}
	
	public void setStudent(Student student) {
	   this.student = student;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((form == null) ? 0 : form.hashCode());
		result = prime * result + ((jobID == null) ? 0 : jobID.hashCode());
		result = prime * result + ((registrationID == null) ? 0 : registrationID.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		CoopTermRegistration other = (CoopTermRegistration) obj;
		if (form == null) {
			if (other.form != null)
				return false;
		} else if (!form.equals(other.form))
			return false;
		if (jobID == null) {
			if (other.jobID != null)
				return false;
		} else if (!jobID.equals(other.jobID))
			return false;
		if (registrationID == null) {
			if (other.registrationID != null)
				return false;
		} else if (!registrationID.equals(other.registrationID))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}
	
}
