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

	/************* CREATE/POST OBJECTS METHODS ************/

	@PostMapping(value = { "/cooptermregistrations/{registrationID}/adjudicate",
			"/cooptermregistrations/{registrationID}/adjudicate/" })
	public CoopTermRegistrationDto adjudicateTermRegistration(@PathVariable("registrationID") String registrationID,
			@RequestParam("success") boolean success) throws IllegalArgumentException {
		CoopTermRegistration termRegistration = service.getCoopTermRegistration(registrationID);
		if (success)
			termRegistration.setTermStatus(TermStatus.FINISHED);
		else
			termRegistration.setTermStatus(TermStatus.FAILED);
		return convertToDto(termRegistration);
	}

	// Method is to POST/CREATE cooperator
	// curl -X POST -i 'http://localhost:8082/Cooperator/Create/?coopID=1'
	@PostMapping(value = { "/cooperators/create/", "/cooperators/create" })
	public CooperatorDto CreateCooperator(@RequestParam("id") Integer coopID) throws IllegalArgumentException {
		// @formatter:on

		service.createCooperator(coopID);
		return convertToDto(coopID);
	}

	// Method is to POST/CREATE term
	// curl -X POST -i
	// http://localhost:8082/terms/create/?id=2112&name=Winter2019&studentdeadline=2019-3-22&coopdeadline=2019-4-4
	@PostMapping(value = { "/terms/create", "/terms/create/" })
	public TermDto CreateTerm(@RequestParam("id") String termID, @RequestParam("name") String name,
			@RequestParam("studentdeadline") String date1, @RequestParam("coopdeadline") String date2) {
		// @formatter:on
		Date studentEvalFormDeadline = Date.valueOf(date1); // form of date: "2015-06-01"
		Date coopEvalFormDeadline = Date.valueOf(date2);
		Term term = service.createTerm(termID, name, studentEvalFormDeadline, coopEvalFormDeadline);
		return convertToDto(term);
	}

	/**
	 * RESTful service: create a new, non-problematic Student. This method
	 * automatically creates a new Cooperator object and assigns it to this Student.
	 * 
	 * @order create
	 * @param studentID
	 *            (primary key) unique ID of this Student.
	 * @param firstName
	 *            the Student's first name.
	 * @param lastName
	 *            the Student's last name.
	 * @param cooperatorID
	 *            the ID of the Cooperator of this Student.
	 * @return a TermDto object that represent the object to be persisted in the
	 *         database.
	 */
	// curl -X POST -i
	// 'http://localhost:8082/students/create/?id="226433222"&firstname=Yen-Vi&lastname="Huynh"&cooperatorid=1'
	@PostMapping(value = { "/students/create", "/students/create/" })
	public StudentDto createStudent(@RequestParam("id") String studentID, @RequestParam("firstname") String firstName,
			@RequestParam("lastname") String lastName, @RequestParam("cooperatorid") Integer cooperatorID)
			throws IllegalArgumentException {
		// @formatter:on
		Cooperator coop = service.getCooperator(cooperatorID);

		Student student = service.createStudent(studentID, firstName, lastName, coop);
		return convertToDto(student);
	}

	// http://localhost:8082/cooptermregistrations/create/?registrationid=1&jobid=142412&studentid=226433222
	@PostMapping(value = { "/cooptermregistrations/create", "/cooptermregistrations/create/" })
	public CoopTermRegistrationDto createCoopTermRegistration(@RequestParam("registrationid") String registrationID,
															  @RequestParam("jobid") String jobID,
															  @RequestParam("studentid") String studentID,
															  @RequestParam("termid") String termID)
			throws IllegalArgumentException {
		Student student = service.getStudent(studentID);
		Term term = service.getTerm(termID);
		CoopTermRegistration internship = service.createCoopTermRegistration(registrationID, jobID, TermStatus.ONGOING,
				Grade.NotGraded, student, term);
		return convertToDto(internship);
	}

	/********** START OF convertToDto METHODS ************/

	private StudentDto convertToDto(Student e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		StudentDto studentDto = new StudentDto(e.getStudentID(), e.getFirstName(), e.getLastName(),
				e.isIsProblematic());
		return studentDto;
	}

	private CoopTermRegistrationDto convertToDto(CoopTermRegistration e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this CoopTermRegistration!");
		}
		CoopTermRegistrationDto coopTermRegistrationDto = new CoopTermRegistrationDto(e.getRegistrationID(),
				e.getJobID(), e.getTermStatus(), e.getGrade(), e.getStudent());
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
		TermDto termDto = new TermDto(e.getTermID(), e.getTermName(), e.getStudentEvalFormDeadline(),
				e.getCoopEvalFormDeadline());
		return termDto;
	}

	// convert to Dto Form
	private FormDto convertFormToDto(Form e) {
		if (e == null) {
			throw new IllegalArgumentException("Cannot create form!");
		}
		FormDto formDto = new FormDto(e.getFormID(), e.getName(), e.getPdfLink());
		return formDto;
	}

	/*********** START OF USE CASES METHODS ************/

	// This method is to report a list of problematic students
	// http://localhost:8082/Students/problematic
	// curl localhost:8082/Students/problematic
	@GetMapping(value = { "/students/problematic", "/students/problematic" })
	@ResponseBody
	public List<StudentDto> getProblematicStudents() throws IllegalArgumentException {
		// @formatter:on
		List<Student> students = service.getAllProblematicStudents();
		List<StudentDto> mylist = new ArrayList<StudentDto>();
		// check for every student;
		for (Student s : students) {
			mylist.add(convertToDto(s));
		}
		return mylist;
	}

	// This method is to report a list of students
	// http://localhost:8082/Students/list
	// curl localhost:8082/Students/list
	@GetMapping(value = { "/students/list", "/students/list", "/students", "/students/" })
	@ResponseBody
	public List<StudentDto> getListStudents() throws IllegalArgumentException {
		// @formatter:on
		Set<Student> students = service.getAllStudents();
		List<StudentDto> mylist = new ArrayList<StudentDto>();

		// check for every student;
		for (Student s : students) {
			mylist.add(convertToDto(s));
		}

		return mylist;
	}

	// Method is to get the student evaluation report
	// curl localhost:8082/Students/report/2602231111
	@GetMapping(value = { "/students/report/{studentID}", "/students/report/{studentID}" })
	public StudentformDto getStudentReport(@PathVariable("studentID") String studentID)
			throws IllegalArgumentException {
		// @formatter:on

		Student mystudent = service.getStudent(studentID);

		if (mystudent != null) {
			Set<Form> myformlist = service.getAllStudentEvalFormsOfStudent(mystudent);
			String myname = mystudent.getFirstName() + " " + mystudent.getLastName();
			List<FormDto> arrayList = new ArrayList<FormDto>();
			for (Form f : myformlist) {
				FormDto myform = convertFormToDto(f);
				arrayList.add(myform);
			}
			StudentformDto mystudentforms = new StudentformDto(myname, arrayList);
			return mystudentforms;
		}
		return null;
	}

	@GetMapping(value = { "/Students/EmployerEval/{studentID}", "/Students/EmployerEval/{studentID}" })
	public EmployerformDto getAllEmployerEval(@PathVariable("studentID") String studentID)
			throws IllegalArgumentException {

		Student mystudent = service.getStudent(studentID);

		if (mystudent != null) {
			Set<Form> myformlist = service.getAllEmployerEvalFormsOfStudent(mystudent);
			String myname = mystudent.getFirstName() + " " + mystudent.getLastName();
			List<FormDto> arrayList = new ArrayList<FormDto>();
			for (Form f : myformlist) {
				FormDto myform = convertFormToDto(f);
				arrayList.add(myform);
			}
			EmployerformDto myemployerforms = new EmployerformDto(myname, arrayList);
			return myemployerforms;
		}
		return null;
	}

	// this method is to view the grades for internships
	// http://localhost:8082/CoopTermRegistrations
	// curl localhost:8082/CoopTermRegistrations
	@GetMapping(value = { "/cooptermregistrations/list", "/cooptermregistrations/list/", "/cooptermregistrations",
			"/cooptermregistrations/" })
	@ResponseBody
	public List<CoopTermRegistrationDto> viewCoopTermRegistrations() {
		// @formatter:on
		Set<CoopTermRegistration> internships = service.getAllCoopTermRegistration();
		List<CoopTermRegistrationDto> internshipsDto = new ArrayList<CoopTermRegistrationDto>();

		if (internships != null) {
			for (CoopTermRegistration intern : internships) {
				internshipsDto.add(convertToDto(intern));
			}
		}
		return internshipsDto;
	}

	// this method is to view the grades for internships
	// http://localhost:8082/CoopTermRegistrations/Grades
	// curl localhost:8082/CoopTermRegistrations/Grades
	@GetMapping(value = { "/cooptermregistrations/grades", "/cooptermregistrations/grades/" })
	@ResponseBody
	public Set<Grade> viewGrades() throws IllegalArgumentException {

		Set<CoopTermRegistration> internships = service.getAllCoopTermRegistration();
		Set<Grade> grades = new HashSet<Grade>();

		for (CoopTermRegistration intern : internships) {
			grades.add(intern.getGrade());
		}
		return grades;
	}
}