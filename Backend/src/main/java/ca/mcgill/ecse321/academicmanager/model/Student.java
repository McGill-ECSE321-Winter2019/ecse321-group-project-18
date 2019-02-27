package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;

/**
 * Student Entity.
 * @author ecse321-winter2019-group18
 */
@Entity
public class Student{
	private String studentID;
   
    public void setStudentID(String value) {
	   this.studentID = value;
    }
	@Id
	public String getStudentID() {
		return this.studentID;
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
	private boolean isProblematic;
	
	public void setIsProblematic(boolean value) {
		this.isProblematic = value;
    }
	public boolean isIsProblematic() {
		return this.isProblematic;
    }
	private Set<Meeting> meeting;
	
	@ManyToMany(mappedBy="student")
	public Set<Meeting> getMeeting() {
	   return this.meeting;
	}
	
	public void setMeeting(Set<Meeting> meetings) {
	   this.meeting = meetings;
	}
	
	public void addMeeting(Meeting meeting) {
		this.meeting.add(meeting);
	}
	
	private CoopTermRegistration coopTermRegistration;
	
	@OneToOne
	public CoopTermRegistration getCoopTermRegistration() {
	   return this.coopTermRegistration;
	}
	
	public void setCoopTermRegistration(CoopTermRegistration coopTermRegistration) {
	   this.coopTermRegistration = coopTermRegistration;
	}
	
	private Cooperator cooperator;
	
	@ManyToOne(optional=false)
	public Cooperator getCooperator() {
	   return this.cooperator;
	}
	
	public void setCooperator(Cooperator cooperator) {
	   this.cooperator = cooperator;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (isProblematic != other.isProblematic)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (studentID == null) {
			if (other.studentID != null)
				return false;
		} else if (!studentID.equals(other.studentID))
			return false;
		return true;
	}
}
