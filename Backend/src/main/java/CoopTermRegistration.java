import javax.persistence.Entity;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;
import ca.mcgill.ecse321.academicmanager.model.Student;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import ca.mcgill.ecse321.academicmanager.model.Form;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class CoopTermRegistration{
private TermStatus coopTermStatus;
   
   public void setCoopTermStatus(TermStatus value) {
this.coopTermStatus = value;
    }
public TermStatus getCoopTermStatus() {
return this.coopTermStatus;
    }
private Student student;

@OneToOne(optional=false)
public Student getStudent() {
   return this.student;
}

public void setStudent(Student student) {
   this.student = student;
}

private CoopPosition coopCourse;

@ManyToOne(optional=false)
public CoopPosition getCoopCourse() {
   return this.coopCourse;
}

public void setCoopCourse(CoopPosition coopCourse) {
   this.coopCourse = coopCourse;
}

private Set<Form> form;

@OneToMany
public Set<Form> getForm() {
   return this.form;
}

public void setForm(Set<Form> forms) {
   this.form = forms;
}

private String registrationID;

public void setRegistrationID(String value) {
this.registrationID = value;
    }
@Id
public String getRegistrationID() {
return this.registrationID;
       }
   }
