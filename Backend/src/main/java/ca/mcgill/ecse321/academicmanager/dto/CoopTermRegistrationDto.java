package ca.mcgill.ecse321.academicmanager.dto;

import ca.mcgill.ecse321.academicmanager.model.Grade;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;

public class CoopTermRegistrationDto {
	
	private String registrationID;
	private String jobID;
	private TermStatus status;
	private Grade grade;
	private String studentID;
	
	public CoopTermRegistrationDto() {
	}
	
	public CoopTermRegistrationDto(String registrationID, String jobID, TermStatus status) {
		this(registrationID, jobID, status, Grade.NotGraded, null);
	}

	public CoopTermRegistrationDto(String registrationID, String jobID, TermStatus status, Grade grade) {
		this(registrationID, jobID, status, grade, null);
	}
	
	public CoopTermRegistrationDto(String registrationID, String jobID, TermStatus status, Grade grade, String studentID) {
		this.registrationID = registrationID;
		this.jobID = jobID;
		this.status = status;
		this.grade = grade;
		this.studentID = studentID;
	}
	
	public String getRegistrationID() {
		return registrationID;
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
	
	public String getStudentID() {
		return studentID;
	}
}
