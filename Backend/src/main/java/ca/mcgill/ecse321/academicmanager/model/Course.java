package ca.mcgill.ecse321.academicmanager.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Course {
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

	@ManyToOne(optional = false)
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
		result = prime * result + ((courseID == null) ? 0 : courseID.hashCode());
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((courseRank == null) ? 0 : courseRank.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
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
		Course other = (Course) obj;
		if (courseID == null) {
			if (other.courseID != null)
				return false;
		} else if (!courseID.equals(other.courseID))
			return false;
		if (courseName == null) {
			if (other.courseName != null)
				return false;
		} else if (!courseName.equals(other.courseName))
			return false;
		if (courseRank == null) {
			if (other.courseRank != null)
				return false;
		} else if (!courseRank.equals(other.courseRank))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

}
