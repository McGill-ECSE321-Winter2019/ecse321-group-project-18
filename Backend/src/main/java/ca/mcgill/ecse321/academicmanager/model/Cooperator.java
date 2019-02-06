package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

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

}
