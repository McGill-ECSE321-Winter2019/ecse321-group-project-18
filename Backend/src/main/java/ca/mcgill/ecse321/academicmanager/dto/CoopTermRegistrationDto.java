package ca.mcgill.ecse321.academicmanager.dto;

import ca.mcgill.ecse321.academicmanager.model.Grade;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;

public class CoopTermRegistrationDto {
	
	private String registrationID;
	private String jobID;
	private TermStatus status;
	private Grade grade;
	private String studentID;
	private String employerFormLink;
	private String studentFormLink;
	private String termName;
	
	public CoopTermRegistrationDto(String registrationID, String termName, String jobID, TermStatus status, Grade grade, String studentID, String employerFormLink, String studentFormLink) {
		this.registrationID = registrationID;
		this.termName = termName;
		this.jobID = jobID;
		this.status = status;
		this.grade = grade;
		this.studentID = studentID;
		this.employerFormLink = employerFormLink;
		this.studentFormLink = studentFormLink;
	}
	
	public String getRegistrationID() {
		return registrationID;
	}
	
	public String getTermName() {
		return this.termName;
	}

	public String getjobID() {
		return jobID;
	}

	public TermStatus getTermStatus() {
		return status;
	}

	public Grade getGrade() {
		return grade;
	}
	
	public Grade getStudentID() {
		return this.getStudentID();
	}
	
	public String getEmployerFormLink() {
		return this.employerFormLink;
	}
	
	public String getStudentFormLink() {
		return this.studentFormLink;
	}
}
