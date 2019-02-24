package ca.mcgill.ecse321.academicmanager.controller;
import ca.mcgill.ecse321.academicmanager.dto.*;
import ca.mcgill.ecse321.academicmanager.model.*;
import ca.mcgill.ecse321.academicmanager.service.*;

import java.util.*;

import javax.xml.ws.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*")
@RestController
public class AcademicManagerRestController {
	@Autowired
	AcademicManagerService service;
	Cooperator cooperator;
	
	// This method is just for testing only , for the provisionning .
    private void Provision() {
		
		cooperator=service.createCooperator(1);
		
		
		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Introduction to Software Engineering";
		Integer courseRank = null;
		try {
			service.createCourse(courseID, term, courseName, courseRank, cooperator);
		} catch (Exception e) {
			// Check that no error occurred

		}		
				
	
		String studentID = "260632353";
		String firstname = "Yen Vi";
		String lastname = "Huynh";
		

		Cooperator cooperator=service.createCooperator(1);

		Student student=service.createStudent(studentID, firstname, lastname, cooperator);
				
		service.updateStudentProblematicStatus(student, true);
		
		studentID = "260632354";
		firstname = "Bach";
		lastname = "Tran";
		

		cooperator=service.createCooperator(1);

		try {
			service.createStudent(studentID, firstname, lastname, cooperator);
			service.createCoopTermRegistration("testregID23", "123", TermStatus.ONGOING, Grade.NotGraded, student);
		} catch (IllegalArgumentException e) {
	
		}
		
		
		studentID = "260632355";
		firstname = "Saleh";
		lastname = "Bakhit";
		

		cooperator=service.createCooperator(1);

		student=service.createStudent(studentID, firstname, lastname, cooperator);
		
		service.updateStudentProblematicStatus(student, true);
		
		studentID = "260632356";
		firstname = "Moetassem";
		lastname = "Abdelazim";
		

		cooperator=service.createCooperator(1);

		try {
			service.createStudent(studentID, firstname, lastname, cooperator);
		} catch (IllegalArgumentException e) {
	
		}
		studentID = "260632357";
		firstname = "Edward";
		lastname = "Huang";
		

		cooperator=service.createCooperator(1);

		try {
			service.createStudent(studentID, firstname, lastname, cooperator);
		} catch (IllegalArgumentException e) {
	
		}
		
    }
	
    @RequestMapping("/students")
    @ResponseBody
	public StudentDto createStudent(@PathVariable("studentID") String studentID, 
									@PathVariable("firstName") String firstName,
									@PathVariable("lastName") String lastName) throws IllegalArgumentException {
		// @formatter:on
    	Provision();
		Cooperator coop = service.createCooperator(1);
		Student student = service.createStudent(studentID, firstName, lastName, coop);
		return convertToDto(student);
	}
    
	
	private StudentDto convertToDto(Student e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		StudentDto studentDto = new StudentDto(e.getStudentID(),e.getFirstName(),e.getLastName(), e.isIsProblematic());
		return studentDto;
	}
	
	
    @RequestMapping("/cooptermregistrations")
    @ResponseBody
	public CoopTermRegistrationDto createCoopTermRegistration(@PathVariable("registrationID") String registrationID, 
												@PathVariable("jobID") String jobID,
												@PathVariable("status") TermStatus status,
												@PathVariable("grade") Grade grade,
												@PathVariable("student") Student student) throws IllegalArgumentException {

		CoopTermRegistration internship = service.createCoopTermRegistration(registrationID, jobID, status, grade, student);
		return convertToDto(internship);
	}
	
	
    private CoopTermRegistrationDto convertToDto(CoopTermRegistration e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		CoopTermRegistrationDto coopTermRegistrationDto = new CoopTermRegistrationDto(e.getRegistrationID(),e.getJobID(),e.getTermStatus(), e.getGrade(), e.getStudent());
		return coopTermRegistrationDto;
	}
	
	
    // this method is to report a list of problematic students
    //http://localhost:8081/students/problematic
    //curl localhost:8081/students/problematic
    @RequestMapping("/students/problematic")
    @ResponseBody
	public List<StudentDto> getProblematicStudents() throws IllegalArgumentException {
	// @formatter:on
    	
		
		List<Student> students = service.getAllProblematicStudents();
		List<StudentDto> mylist = new ArrayList<StudentDto>();
	
		//check for every student;
		for(Student s : students) {
			mylist.add(convertToDto(s));
		}
		
		return mylist;
	}

    // this method is to view the grades for internships
    // http://localhost:8081/CoopTermRegistration/Grades
    //curl localhost:8081/CoopTermRegistration/Grades
    @RequestMapping("/CoopTermRegistration/Grades")
    @ResponseBody
	public Set<Grade> viewGrades() throws IllegalArgumentException {
	
    	Provision();
    	Set<CoopTermRegistration> internships = service.getAllCoopTermRegistration();
    	Set<Grade> grades = new HashSet<Grade>();
    	
    	for(CoopTermRegistration intern : internships) {
    		grades.add(intern.getGrade());
		}
    	return grades;
	}
    
}
