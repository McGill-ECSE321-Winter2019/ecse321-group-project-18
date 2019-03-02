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
	
	@OneToMany(mappedBy="cooperator", fetch=FetchType.EAGER)
	public Set<Student> getStudent() {
	   return this.student;
	}
	
	public void setStudent(Set<Student> students) {
	   this.student = students;
	}
	
	public void addStudent(Student student) {
		try {
			if(!this.student.contains(student)) {
				this.student.add(student);
				student.setCooperator(this);
			}
		}
		catch(Exception e) {	
			this.student = new HashSet<Student>();
			this.student.add(student);
			student.setCooperator(this);
		}
	}
	
	private Set<Course> course;
	
	@OneToMany(mappedBy="cooperator", fetch=FetchType.EAGER)
	public Set<Course> getCourse() {
	   return this.course;
	}
	
	public void setCourse(Set<Course> courses) {
	   this.course = courses;
	}
	
	public void addCourse(Course course) {
		try {
			if(!this.course.contains(course)) {
				this.course.add(course);
				course.setCooperator(this);
			}
		}
		catch(Exception e) {	
			this.course = new HashSet<Course>();
			this.course.add(course);
			course.setCooperator(this);
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
