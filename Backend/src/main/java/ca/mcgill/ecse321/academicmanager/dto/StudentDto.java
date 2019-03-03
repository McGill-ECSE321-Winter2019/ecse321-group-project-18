package ca.mcgill.ecse321.academicmanager.dto;

public class StudentDto {

	private String studentID;
	private String firstName;
	private String lastName;
	private boolean isProblematic;
	
	public StudentDto() {
	}
	
	public StudentDto(String studentID, String firstName, String lastName) {
		this(studentID, firstName, lastName, false);
	}

	public StudentDto(String studentID, String firstName, String lastName, boolean isProblematic) {
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isProblematic = isProblematic;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean getStudentProblematicStatus() {
		return isProblematic;
	}
	
	public void setStudentProblematicStatus(boolean isProblematic) {
		this.isProblematic = isProblematic;
	}
}
