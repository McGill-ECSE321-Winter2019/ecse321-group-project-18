package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.sql.Time;
import javax.persistence.Id;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ManyToMany;
import java.sql.Date;

/**
 * Represent a Meeting between students and the Academic Program Manager.
 * Date constraint: the Meeting should take place in one day only.
 * Time constraint: endTime has to happen after startTime.
 * @author ecse321-winter2019-group18
 */
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
	
	public void addStudent(Student student) {
		try {
			if(!this.student.contains(student)) {
				this.student.add(student);
			}
		}
		catch(Exception e) {
			this.student = new HashSet<Student>();
			this.student.add(student);
		}
	}

	private Date date;
	
	public void setDate(Date value) {
		this.date = value;
	}
	public Date getDate() {
		return this.date;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((meetingID == null) ? 0 : meetingID.hashCode());
		result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
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
		Meeting other = (Meeting) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (meetingID == null) {
			if (other.meetingID != null)
				return false;
		} else if (!meetingID.equals(other.meetingID))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}
	
	
}
