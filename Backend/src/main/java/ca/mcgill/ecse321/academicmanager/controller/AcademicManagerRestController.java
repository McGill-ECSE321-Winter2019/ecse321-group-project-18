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
	
	private StudentDto convertToDto(Student e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		StudentDto studentDto = new StudentDto(e.getStudentID(),e.getFirstName(),e.getLastName(), e.isIsProblematic());
		return studentDto;
	}
	
	private FormDto convertFormToDto(Form e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		FormDto formDto = new FormDto(e.getFormID(),e.getName(),e.getPdfLink());
		return formDto;
	}
	
	
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
    
    
}
