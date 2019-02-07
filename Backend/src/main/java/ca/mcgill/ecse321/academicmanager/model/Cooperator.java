package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.Id;

@Entity
public class Cooperator{
private Set<Course> course;

@OneToMany(mappedBy="cooperator", cascade={CascadeType.ALL})
public Set<Course> getCourse() {
   return this.course;
}

public void setCourse(Set<Course> courses) {
   this.course = courses;
}

private Set<Student> student;

@OneToMany(mappedBy="cooperator", cascade={CascadeType.ALL})
public Set<Student> getStudent() {
   return this.student;
}

public void setStudent(Set<Student> students) {
   this.student = students;
}

private Integer id;

public void setId(Integer value) {
this.id = value;
    }
@Id
public Integer getId() {
return this.id;
       }
   }
