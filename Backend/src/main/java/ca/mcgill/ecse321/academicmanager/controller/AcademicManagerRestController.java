package ca.mcgill.ecse321.academicmanager.controller;

import ca.mcgill.ecse321.academicmanager.dto.*;
import ca.mcgill.ecse321.academicmanager.model.*;
import ca.mcgill.ecse321.academicmanager.service.*;


import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AcademicManagerRestController {
		@Autowired
		AcademicManagerService service;
	
		
		Cooperator cooperator;
		@PostMapping(value = { "/Students/{name}", "/Students/{name}/" })
	
	
	// This method is just for testing only , for the provisionning .
    private void Provision() {
		
		cooperator=service.createCooperator(1);
		
		
		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Introduction to Software Engineering";
		Integer courseRank = null;
		try {
			service.createCourse(courseID, term, courseName, courseRank, cooperator);
		} catch (Exception e) {}
			// Check that no error occurred

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
		} catch (IllegalArgumentException e) {}	
	}
	
	
	/************* CREATE OBJECTS METHODS ************/
	
    @RequestMapping("/Students")
    @ResponseBody
	public StudentDto createStudent(@PathVariable("studentID") String studentID, 
									@PathVariable("firstName") String firstName,
									@PathVariable("lastName") String lastName) throws IllegalArgumentException {
		// @formatter:on
		Cooperator coop = service.createCooperator(1);
		Student student = service.createStudent(studentID, firstName, lastName, coop);
		return convertToDto(student);
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
	
    
    /********** START OF convertToDto METHODS ************/
    
	private StudentDto convertToDto(Student e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		StudentDto studentDto = new StudentDto(e.getStudentID(),e.getFirstName(),e.getLastName(), e.isIsProblematic());
		return studentDto;
	}
	
	private CoopTermRegistrationDto convertToDto(CoopTermRegistration e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		CoopTermRegistrationDto coopTermRegistrationDto = new CoopTermRegistrationDto(e.getRegistrationID(),e.getJobID(),e.getTermStatus(), e.getGrade(), e.getStudent());
		return coopTermRegistrationDto;
	}
	
	private FormDto convertFormToDto(Form e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		FormDto formDto = new FormDto(e.getFormID(),e.getName(),e.getPdfLink());
		return formDto;
	}
	
	/*********** START OF USE CASES METHODS ************/
	// This method is to report a list of problematic students
    // http://localhost:8082/Student/problematic
    // curl localhost:8082/Students/problematic
	@GetMapping(value = { "/Students/problematic", "/Students/problematic" })
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
   
	// This method is to report a list of students
    //http://localhost:8082/Student/list
    //curl localhost:8082/Students/list
    @GetMapping(value = { "/Students/list", "/Students/list" })
	public List<StudentDto> getListStudents() throws IllegalArgumentException {
	// @formatter:on
    	
		
		Set<Student> students = service.getAllStudents();
		List<StudentDto> mylist = new ArrayList<StudentDto>();
	
		//check for every student;
		for(Student s : students) {
			mylist.add(convertToDto(s));
		}
		
		return mylist;
	}

    // Method is to get the student evaluation report 
    // curl localhost:8082/Students/report/2602231111
    @GetMapping(value = { "/Students/report/{studentID}", "/Students/report/{studentID}" })
	public StudentformDto getStudentReport(@PathVariable("studentID") String studentID) throws IllegalArgumentException {
	// @formatter:on
    	

    	Student mystudent=service.getStudent(studentID);
    	
    	if (mystudent != null ) {
			Set<Form> myformlist=service.getAllStudentEvalFormsOfStudent(mystudent);
			String myname = mystudent.getFirstName() + " " +  mystudent.getLastName();
			List<FormDto> arrayList = new ArrayList<FormDto>();
			for(Form f : myformlist) {
				FormDto myform=convertFormToDto(f);
				arrayList.add(myform);
			};
			StudentformDto mystudentforms= new StudentformDto(myname,arrayList);
			//return mystudentforms;
			return mystudentforms;
    	}

			
		 return null;
		
		
		
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