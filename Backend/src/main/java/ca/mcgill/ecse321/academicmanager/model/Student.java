package ca.mcgill.ecse321.academicmanager.model;
import javax.persistence.Id;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Student{
private Cooperator cooperator;

@ManyToOne(optional=false)
public Cooperator getCooperator() {
   return this.cooperator;
}

public void setCooperator(Cooperator cooperator) {
   this.cooperator = cooperator;
}

private Integer studentID;

public void setStudentID(Integer value) {
this.studentID = value;
    }
@Id
public Integer getStudentID() {
return this.studentID;
    }
private CoopTermRegistration coopTermRegistration;

@OneToOne(mappedBy="student", optional=false)
public CoopTermRegistration getCoopTermRegistration() {
   return this.coopTermRegistration;
}

public void setCoopTermRegistration(CoopTermRegistration coopTermRegistration) {
   this.coopTermRegistration = coopTermRegistration;
}


private boolean isProblematic;

public void setIsProblematic(boolean value) {
   this.isProblematic = value;
}

public boolean isIsProblematic() {
   return this.isProblematic;
}

private String firstName;

public void setFirstName(String value) {
   this.firstName = value;
}

public String getFirstName() {
   return this.firstName;
}

private String lastName;

public void setLastName(String value) {
   this.lastName = value;
}

public String getLastName() {
   return this.lastName;
}

}
