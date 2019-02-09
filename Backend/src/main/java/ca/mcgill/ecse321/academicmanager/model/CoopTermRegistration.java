package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CoopTermRegistration{
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

@OneToOne(mappedBy="coopTermRegistration")
public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

}
