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
  
	// Method is to POST/CREATE cooperator
	// curl -X POST -i 'http://localhost:8082/cooperators/create/?id=1'
	@PostMapping(value = { "/cooperators/create", "/cooperators/create/" })
	public CooperatorDto CreateCooperator(@RequestParam("id") Integer coopID) throws IllegalArgumentException {
		// @formatter:on

		service.createCooperator(coopID);
		return convertToDto(coopID);
	}

	// Method is to POST/CREATE term
	//  curl -X POST -i 'localhost:8082/terms/create/?id=2112&name=Winter2019&studentdeadline=2019-3-22&coopdeadline=2019-4-4'
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

	// curl -X POST -i 'http://localhost:8082/students/create/?id=226433222&firstname=Yen-Vi&lastname=Huynh&cooperatorid=1'
	@PostMapping(value = { "/students/create", "/students/create/" })
	public StudentDto createStudent(@RequestParam("id") String studentID, @RequestParam("firstname") String firstName,
			@RequestParam("lastname") String lastName, @RequestParam("cooperatorid") Integer cooperatorID)
			throws IllegalArgumentException {
		// @formatter:on
		Cooperator coop = service.getCooperator(cooperatorID);

		Student student = service.createStudent(studentID, firstName, lastName, coop);
		return convertToDto(student);
	}
	
	
    // To convert the TermStatus so:
    // 0: ONGOING, 1: FINISHED, 2: FAILED
    
    public static TermStatus ConvertTermStatus(int x) {
        switch(x) {          
        case 0:
            return TermStatus.ONGOING;
        case 1:
            return TermStatus.FINISHED;
        case 2:
        	return TermStatus.FAILED;
        }
        return null;
    }
    
    // To convert the Grade for student in this case
    // 0:A, 1:B, 2:C, 3:D, 4:F, 5: NotGraded
    public static Grade ConvertGrade(int x) {
        switch(x) {          
        case 0:
            return Grade.A;
        case 1:
            return Grade.B;
        case 2:
        	return Grade.C;
        case 3:
        	return Grade.D;
        case 4:
        	return Grade.F;
        case 5:
        	return Grade.NotGraded;
        }
        return null;
    }
    
        

	// curl -X POST -i 'https://cooperatorapp-backend-18.herokuapp.com/coopTermRegistrations/create/?registrationid=1&jobid=142412&studentid=226433222&termid=2112&termstat=0&gradeid=5'
	@PostMapping(value = { "/coopTermRegistrations/create", "/coopTermRegistrations/create/" })
	public CoopTermRegistrationDto createCoopTermRegistration(@RequestParam("registrationid") String registrationID,
			@RequestParam("jobid") String jobID, @RequestParam("studentid") String studentID, @RequestParam("termid") String termID,
			@RequestParam("termstat") Integer termStat,@RequestParam("gradeid") Integer gradeID) {
		//	throws IllegalArgumentException {
		Student student = service.getStudent(studentID);
		Term term = service.getTerm(termID);
		
		TermStatus mystatus = ConvertTermStatus(termStat);
		Grade mygrade = ConvertGrade(gradeID);
		CoopTermRegistration internship = service.createCoopTermRegistration(registrationID, jobID, mystatus,
				mygrade, student, term);
		return convertToDto(internship);
	}

	// curl -X POST 'https://cooperatorapp-backend-18.herokuapp.com/courses/create?id=1234&term=2112&name=hahaha&rank=10&cooperatorid=1'
    @PostMapping(value = { "/courses/create", "/events/create/" })
    @ResponseBody
    public CourseDto createCourse(@RequestParam("id") String id, 
    		@RequestParam("term") String term, 
    		@RequestParam("name") String name, 
    		@RequestParam("rank") String rank,
    		@RequestParam("cooperatorid") Integer cooperatorID) {
    	    	
		Cooperator c = service.getCooperator(cooperatorID);

    	Course course = service.createCourse(id, term, name, Integer.parseInt(rank), c);
    	return convertCourseToDto(course);
    }
    
    /********** GENERAL GET METHODS ****************/
    
    // this method is to view the grades for internships
 	// http://localhost:8082/CoopTermRegistrations
    
    //curl https://cooperatorapp-backend-18.herokuapp.com/coopTermRegistrations/list 
 	// curl localhost:8082/CoopTermRegistrations
 	@GetMapping(value = { "/coopTermRegistrations/list", "/coopTermRegistrations/list/", "/coopTermRegistrations",
 			"/coopTermRegistrations/" })
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
 	// curl https://cooperatorapp-backend-18.herokuapp.com/coopTermRegistrations/grades/
 	// http://localhost:8082/cooptermregistrations/grades
 	// curl localhost:8082/cooptermregistrations/grades
 	@GetMapping(value = { "/coopTermRegistrations/grades", "/coopTermRegistrations/grades/" })
 	@ResponseBody
 	public Set<Grade> viewGrades() throws IllegalArgumentException {

 		Set<CoopTermRegistration> internships = service.getAllCoopTermRegistration();
 		Set<Grade> grades = new HashSet<Grade>();

 		for (CoopTermRegistration intern : internships) {
 			grades.add(intern.getGrade());
 		}
 		return grades;
 	}
 	
 	//curl https://cooperatorapp-backend-18.herokuapp.com/courses/specific/1234
 	
    @GetMapping(value = {"/courses/specific", "/courses/specific/"})
    @ResponseBody
    public CourseDto getCourse(@RequestParam("id") String courseID, 
    		@RequestParam("term") String term) {
    	if (courseID == null || courseID.isEmpty()) {
    		throw new IllegalArgumentException();
    	}
    	return convertCourseToDto(service.getCourse(courseID, term));
    }
    
    /**
     * RESTful service: retrieves n first useful course, using courseRank to make comparison.
     * @param quantity number of courses wanted to retrieve.
     * @return a list of n useful courses.
     * */
    //curl https://cooperatorapp-backend-18.herokuapp.com/courses/filter?quantity=2
    @GetMapping(value = {"/courses/filter", "courses/filter/"})
    @ResponseBody
    public List<CourseDto> getCourses(@RequestParam("quantity")int quantity) {
    	return new ArrayList<CourseDto>(getCourses().subList(0, (quantity < getCourses().size()) ? quantity : getCourses().size()));
    }
    
	/********** START OF convertToDto METHODS ************/

    // convert to Dto Student
	private StudentDto convertToDto(Student e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		StudentDto studentDto = new StudentDto(e.getStudentID(), e.getFirstName(), e.getLastName(),
				e.isIsProblematic());
		return studentDto;
	}

	// convert to Dto CoopTermRegistration
	private CoopTermRegistrationDto convertToDto(CoopTermRegistration e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this CoopTermRegistration!");
		}
		CoopTermRegistrationDto coopTermRegistrationDto = new CoopTermRegistrationDto(e.getRegistrationID(),
				e.getJobID(), e.getTermStatus(), e.getGrade(), e.getStudent().getStudentID());
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

	// convert to Dto Course
	private CourseDto convertCourseToDto (Course e) throws IllegalArgumentException {
    	if (e == null) {
    		throw new IllegalArgumentException("No course to convert!");
    	}
    	return new CourseDto(e.getCourseID(), e.getTerm(), e.getCourseName(), e.getCourseRank());
    }
	
	/*********** START OF USE CASES GET METHODS ************/

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
	// http://localhost:8082/students/list
	// curl localhost:8082/students/list
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
	// curl http://localhost:8082/students/report/226433222
	@GetMapping(value = { "/students/report/{studentID}", "/students/report/{studentID}" })
	public StudentformDto getStudentReport(@PathVariable("studentID") String studentID)
			throws IllegalArgumentException {
		// @formatter:on

		/*Student mystudent = service.getStudent(studentID);

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
		return null;*/
		
		Set<Form> myformlist = service.getAllStudentEvalForms();
		List<FormDto> arrayList = new ArrayList<FormDto>();
		for (Form f : myformlist) {
			if(f.getCoopTermRegistration().getStudent().getStudentID().equals(studentID)) {
				FormDto myform = convertFormToDto(f);
				arrayList.add(myform);
			}
		}
		StudentformDto mystudentforms = new StudentformDto(studentID, arrayList);
		return mystudentforms;
	}

	@GetMapping(value = { "/students/employereval/{studentID}", "/students/employereval/{studentID}" })
	public EmployerformDto getAllEmployerEval(@PathVariable("studentID") String studentID)
			throws IllegalArgumentException {

/*		Student mystudent = service.getStudent(studentID);

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
		return null;*/
		
		Set<Form> myformlist = service.getAllEmployerEvalForms();
		List<FormDto> arrayList = new ArrayList<FormDto>();
		for (Form f : myformlist) {
			if(f.getCoopTermRegistration().getStudent().getStudentID().equals(studentID)) {
				FormDto myform = convertFormToDto(f);
				arrayList.add(myform);
			}
		}
		EmployerformDto myemployerforms = new EmployerformDto(studentID, arrayList);
		return myemployerforms;
	}
	
    /**
     * RESTful service: retrieves a list of all courses, sorted by their usefulness (courseRank).
     * @author Bach Tran
     * @return a sorted List<CourseDto> object by courseRank.
     * */
    @GetMapping(value = {"/courses", "/courses/"})
    @ResponseBody
    public List<CourseDto> getCourses() {
    	// dummy...
    	Set<Course> courseSet = service.getAllCourses();
    	List<CourseDto> courseList = new ArrayList<CourseDto>();
    	for (Course course : courseSet) {
    		courseList.add(convertCourseToDto(course));
    	}
    	Collections.sort(courseList);
    	return courseList;
    }
    
    /************ START OF USE CASES POST METHODS ****************/
    //curl -X POST 'https://cooperatorapp-backend-18.herokuapp.com/coopTermRegistrations/1/adjudicate/?success=true'
    // http://localhost:8082/coopTermRegistrations/1/adjudicate/?success=true
 	@PostMapping(value = { "/coopTermRegistrations/{registrationID}/adjudicate",
 			"/coopTermRegistrations/{registrationID}/adjudicate/" })
 	public CoopTermRegistrationDto adjudicateTermRegistration(@PathVariable("registrationID") String registrationID,
 			@RequestParam("success") boolean success) throws IllegalArgumentException {
 		CoopTermRegistration termRegistration = service.getCoopTermRegistration(registrationID);
 		if (success)
 			termRegistration.setTermStatus(TermStatus.FINISHED);
 		else
 			termRegistration.setTermStatus(TermStatus.FAILED);
 		return convertToDto(termRegistration);
 	}
}