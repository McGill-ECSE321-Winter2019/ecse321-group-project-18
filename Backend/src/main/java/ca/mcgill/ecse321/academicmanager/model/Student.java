package ca.mcgill.ecse321.academicmanager.model;
import CoopTermRegistration;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class Student{
private String firstName;

public void setFirstName(String value) {
   this.firstName = value;
}

public String getFirstName() {
   return this.firstName;
}

private CoopTermRegistration coopTermRegistration;

@OneToOne(mappedBy="student", optional=false)
public CoopTermRegistration getCoopTermRegistration() {
   return this.coopTermRegistration;
}

public void setCoopTermRegistration(CoopTermRegistration coopTermRegistration) {
   this.coopTermRegistration = coopTermRegistration;
}

private String lastName;

public void setLastName(String value) {
   this.lastName = value;
}

public String getLastName() {
   return this.lastName;
}


private Integer studentID;

public void setStudentID(Integer value) {
this.studentID = value;
    }
public Integer getStudentID() {
return this.studentID;
    }
private boolean isProblematic;

public void setIsProblematic(boolean value) {
this.isProblematic = value;
    }
public boolean isIsProblematic() {
return this.isProblematic;
    }
private Cooperator cooperator;

@ManyToOne(optional=false)
public Cooperator getCooperator() {
   return this.cooperator;
}

public void setCooperator(Cooperator cooperator) {
   this.cooperator = cooperator;
}

}
