package ca.mcgill.ecse321.academicmanager.model;
import Student;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Cooperator{
private Set<Student> student;

@OneToMany(mappedBy="cooperator", cascade={CascadeType.ALL})
public Set<Student> getStudent() {
   return this.student;
}

public void setStudent(Set<Student> students) {
   this.student = students;
}

private Set<Course> course;

@OneToMany(mappedBy="cooperator", cascade={CascadeType.ALL})
public Set<Course> getCourse() {
   return this.course;
}

public void setCourse(Set<Course> courses) {
   this.course = courses;
}

}
