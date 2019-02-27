package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@Entity
public class Cooperator{
	private Integer id;
   
	public void setId(Integer value) {
		this.id = value;
    }
	@Id
	public Integer getId() {
		return this.id;
    }
	private Set<Student> student;
	
	@OneToMany(mappedBy="cooperator", cascade={CascadeType.ALL})
	public Set<Student> getStudent() {
	   return this.student;
	}
	
	public void setStudent(Set<Student> students) {
	   this.student = students;
	}
	
	public void addStudent(Student student) {
		try {
			this.student.add(student);
		}
		catch(Exception e) {	
			this.student = new HashSet<Student>();
			this.student.add(student);
		}
	}
	
	private Set<Course> course = new HashSet<Course>();
	
	@OneToMany(mappedBy="cooperator", cascade={CascadeType.ALL})
	public Set<Course> getCourse() {
	   return this.course;
	}
	
	public void setCourse(Set<Course> courses) {
	   this.course = courses;
	}
	
	public void addCourse(Course course) {
		this.course.add(course);
		try {
			this.course.add(course);
		}
		catch(Exception e) {	
			this.course = new HashSet<Course>();
			this.course.add(course);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cooperator other = (Cooperator) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
