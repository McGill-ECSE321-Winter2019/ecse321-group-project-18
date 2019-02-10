package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import java.sql.Time;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.ManyToMany;

@Entity
public class Meeting
{
	private Time startTime;
   
	public void setStartTime(Time value) {
		this.startTime = value;
    }
	public Time getStartTime() {
		return this.startTime;
	}
	private Time endTime;
	
	public void setEndTime(Time value) {
		this.endTime = value;
	}
	public Time getEndTime() {
		return this.endTime;
	}
	private String location;
	
	public void setLocation(String value) {
		this.location = value;
	}
	public String getLocation() {
		return this.location;
	}
	private String details;
	
	public void setDetails(String value) {
		this.details = value;
	}
	public String getDetails() {
		return this.details;
	}
	private String meetingID;
	
	public void setMeetingID(String value) {
		this.meetingID = value;
	}
	@Id
	public String getMeetingID() {
		return this.meetingID;
	}
	private Set<Student> student;
	
	@ManyToMany
	public Set<Student> getStudent() {
	   return this.student;
	}
	
	public void setStudent(Set<Student> students) {
	   this.student = students;
	}

}
