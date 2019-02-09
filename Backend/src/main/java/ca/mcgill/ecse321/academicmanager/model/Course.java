package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Course{
private String courseID;
   
   public void setCourseID(String value) {
this.courseID = value;
    }
@Id
public String getCourseID() {
return this.courseID;
    }
private String term;

public void setTerm(String value) {
this.term = value;
    }
public String getTerm() {
return this.term;
    }
private String courseName;

public void setCourseName(String value) {
this.courseName = value;
    }
public String getCourseName() {
return this.courseName;
    }
private Integer courseRank;

public void setCourseRank(Integer value) {
this.courseRank = value;
    }
public Integer getCourseRank() {
return this.courseRank;
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
