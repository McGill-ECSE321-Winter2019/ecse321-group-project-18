package ca.mcgill.ecse321.academicmanager.controller;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
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
	@Autowired
	private FormRepository formRepository;

	
	
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
	
	
	// this method is to report a list of problematic students
    //http://localhost:8082/Student/problematic
    //curl localhost:8082/Students/problematic
    @RequestMapping("/Students/problematic")
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

    //http://localhost:8082/Student/list
    //curl localhost:8082/Students/list
    @RequestMapping("/Students/list")
    @ResponseBody
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
    
    @RequestMapping("/Students/form/{studentid}")
    @ResponseBody
	public List<StudentDto> getStudentReport(@PathVariable("studentID") String studentID) throws IllegalArgumentException {
	// @formatter:on
    	

    	Student mystudent=service.getStudent(studentID);
    	Set<Form> myformlist=service.getAllStudentEvalFormsOfStudent(mystudent);


		Set<Student> students = service.getAllStudents();
		List<StudentDto> mylist = new ArrayList<StudentDto>();
	
		//check for every student;
		for(Student s : students) {
			mylist.add(convertToDto(s));
		}
		
		return mylist;
	}
    
    
}
