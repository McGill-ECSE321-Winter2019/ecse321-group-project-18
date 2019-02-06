package ca.mcgill.ecse321.academicmanager.model;
import Student;
import TermStatus;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Set;
import javax.persistence.OneToMany;
import java.sql.Date;

@Entity
public class CoopTerm{
private Date startDate;
   
   public void setStartDate(Date value) {
this.startDate = value;
    }
public Date getStartDate() {
return this.startDate;
    }
private Date endDate;

public void setEndDate(Date value) {
this.endDate = value;
    }
public Date getEndDate() {
return this.endDate;
    }
private TermStatus termStatus;

public void setTermStatus(TermStatus value) {
this.termStatus = value;
    }
public TermStatus getTermStatus() {
return this.termStatus;
    }
private Student student;

@OneToOne(optional=false)
public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

private Set<Form> form;

@OneToMany(mappedBy="coopTerm")
public Set<Form> getForm() {
   return this.form;
}

public void setForm(Set<Form> forms) {
   this.form = forms;
}
private String jobID;

public void setJobID(String value) {
this.jobID = value;
    }
public String getJobID() {
return this.jobID;
    }
   }
