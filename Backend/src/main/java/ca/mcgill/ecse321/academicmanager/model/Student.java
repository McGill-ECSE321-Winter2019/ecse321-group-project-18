package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

@Entity
public class Student{
private String name;

public void setName(String value) {
   this.name = value;
}

public String getName() {
   return this.name;
}

private CoopTerm coopTerm;

@OneToOne(mappedBy="student")
public CoopTerm getCoopTerm() {
   return this.coopTerm;
}

public void setCoopTerm(CoopTerm coopTerm) {
   this.coopTerm = coopTerm;
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
