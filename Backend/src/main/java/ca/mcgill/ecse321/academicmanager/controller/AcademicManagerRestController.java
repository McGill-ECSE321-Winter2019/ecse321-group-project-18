package ca.mcgill.ecse321.academicmanager.controller;

import ca.mcgill.ecse321.academicmanager.dto.*;
import ca.mcgill.ecse321.academicmanager.model.*;
import ca.mcgill.ecse321.academicmanager.service.*;

import java.sql.Date;
import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
public class AcademicManagerRestController {
	@Autowired
	AcademicManagerService service;	
	Cooperator cooperator;	
	
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
    
    @RequestMapping("/CoopTermRegistrations")
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
	
	// convert to Dto cooperator
	private CooperatorDto convertToDto(Integer e) {
		if (e == null) {
			throw new IllegalArgumentException("Cannot create term");
		}
		CooperatorDto CoopDto = new CooperatorDto(e);
		return CoopDto;
	}
	
	// convert to Dto Term
	private TermDto convertToDto(Term e) {
		if (e == null) {
			throw new IllegalArgumentException("Cannot create term");
		}
		TermDto termDto = new TermDto(e.getTermID(),e.getTermName(),e.getStudentEvalFormDeadline(),e.getCoopEvalFormDeadline());
		return termDto;
	}
	
	
	// convert to Dto Form
	private FormDto convertFormToDto(Form e) {
		if (e == null) {
			throw new IllegalArgumentException("Cannot create form!");
		}
		FormDto formDto = new FormDto(e.getFormID(),e.getName(),e.getPdfLink());
		return formDto;
	}
	
	/*********** START OF USE CASES METHODS ************/
	
	
    // Method is to POST/CREATE cooperator
    // curl -X POST localhost:8082/Cooperator/1
    @PostMapping(value = { "/Cooperator/{coopID}"})
	public CooperatorDto CreateCooperator(@PathVariable("coopID") Integer coopID 			      
			) throws IllegalArgumentException {
	// @formatter:on
    	
    	service.createCooperator(coopID);
    	return convertToDto(coopID);
	}   
	
    // Method is to POST/CREATE cooperator
    // curl -X POST localhost:8082/Terms/2211/Winter2019/2019-3-22/2019-4-4
    @PostMapping(value = { "/Terms/{termID}/{termName}/{date1}/{date2}", "/Terms/{termID}/{termName}/{date1}/{date2}" })
	public TermDto CreateTerm(@PathVariable("termID") String termID,@PathVariable("termName") String termName ,
			@PathVariable("date1") String date1,@PathVariable("date2") String date2       
			) throws IllegalArgumentException {
	// @formatter:on
    	
    	Set<CoopTermRegistration> ctrs = new HashSet<CoopTermRegistration>();
		Date studentEvalFormDeadline = Date.valueOf(date1); // form of date: "2015-06-01"
		Date coopEvalFormDeadline = Date.valueOf(date2);
    	Term term = service.createTerm(termID, termName, studentEvalFormDeadline, coopEvalFormDeadline, ctrs);
    	return convertToDto(term);
	}   
	
	// This method is to report a list of problematic students
    // http://localhost:8082/Students/problematic
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
    //http://localhost:8082/Students/list
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
			return mystudentforms;
    	}
		return null;		
	}   

    // this method is to view the grades for internships
    // http://localhost:8082/CoopTermRegistrations
    // curl localhost:8082/CoopTermRegistrations
    @GetMapping("/CoopTermRegistrations/list")
    @ResponseBody
	public List<CoopTermRegistrationDto> viewCoopTermRegistrations() throws IllegalArgumentException {	
    	// @formatter:on

    	Set<CoopTermRegistration> internships = service.getAllCoopTermRegistration();
    	List<CoopTermRegistrationDto> internshipsDto = new ArrayList<CoopTermRegistrationDto>();
    			
    	if (internships != null) {
			for(CoopTermRegistration intern : internships) {
    			internshipsDto.add(convertToDto(intern));
			}
    	}
    	return internshipsDto;
	}
    
    // this method is to view the grades for internships
    // http://localhost:8082/CoopTermRegistrations/Grades
    //curl localhost:8082/CoopTermRegistrations/Grades
    @GetMapping("/CoopTermRegistrations/Grades")
    @ResponseBody
	public Set<Grade> viewGrades() throws IllegalArgumentException {
	
    	Set<CoopTermRegistration> internships = service.getAllCoopTermRegistration();
    	Set<Grade> grades = new HashSet<Grade>();
    	
    	for(CoopTermRegistration intern : internships) {
    		grades.add(intern.getGrade());
		}
    	return grades;
	}
}