package ca.mcgill.ecse321.academicmanager.model;

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

@OneToOne(mappedBy="coopTermRegistration", optional=false)
public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

}
