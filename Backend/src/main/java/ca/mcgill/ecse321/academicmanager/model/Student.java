package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

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
	
	@ManyToMany(mappedBy="student", cascade= {CascadeType.ALL})
	public Set<Meeting> getMeeting() {
	   return this.meeting;
	}
	
	public void setMeeting(Set<Meeting> meetings) {
	   this.meeting = meetings;
	}
	
	public void addMeeting(Meeting meeting) {
		try {
			if(!this.meeting.contains(meeting)) {
				this.meeting.add(meeting);
			}
		}
		catch(Exception e) {
			this.meeting = new HashSet<Meeting>();
			this.meeting.add(meeting);
		}
	}
	
	private Set<CoopTermRegistration> coopTermRegistration;
	
	@OneToMany(mappedBy="student", cascade= {CascadeType.ALL})
	public Set<CoopTermRegistration> getCoopTermRegistration() {
	   return this.coopTermRegistration;
	}
	
	public void setCoopTermRegistration(Set<CoopTermRegistration> coopTermRegistration) {
	   this.coopTermRegistration = coopTermRegistration;
	}
	
	public void addCoopTermRegistration(CoopTermRegistration coopTermRegistration) {
		try {
			if(!this.coopTermRegistration.contains(coopTermRegistration)) {
				this.coopTermRegistration.add(coopTermRegistration);
				coopTermRegistration.setStudent(this);
			}
		}
		catch(Exception e) {
			this.coopTermRegistration = new HashSet<CoopTermRegistration>();
			this.coopTermRegistration.add(coopTermRegistration);
			coopTermRegistration.setStudent(this);
		}
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + (isProblematic ? 1231 : 1237);
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((studentID == null) ? 0 : studentID.hashCode());
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
