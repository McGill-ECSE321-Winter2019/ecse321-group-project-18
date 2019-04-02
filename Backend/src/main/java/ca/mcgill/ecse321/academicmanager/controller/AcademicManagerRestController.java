package ca.mcgill.ecse321.academicmanager.controller;

import ca.mcgill.ecse321.academicmanager.dto.*;
import ca.mcgill.ecse321.academicmanager.model.*;
import ca.mcgill.ecse321.academicmanager.service.*;

import java.sql.Date;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This class stands in between all the Java services and the RESTful service calls.
 * @author Bach Tran, Edward Huang, Yen-Vi Huynh, Moetassem Abdelazim, Saleh Bakhit.
 * @since Sprint 2
 * */
@CrossOrigin(origins = "*")
@RestController
public class AcademicManagerRestController {
    // These followings constants are put here to make it easier in testing RESTful services manually.
	public static final String LOCAL_PORT = "8082";
	public static final String LOCAL_HOST = "http://localhost";
	public static final String BACKEND_HOST = "https://cooperatorapp-backend-18.herokuapp.com";
	public static final String BACKEND_PORT = "443";
	@Autowired
	private CooperatorService cooperatorService;
	@Autowired
	private CoopTermRegistrationService coopTermRegistrationService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private FormService formService;
	@Autowired
	private MeetingService meetingService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TermService termService;

	/**
	 * Responses to the HTTP POST call on creating a Cooperator.
	 * @param coopID the unique id of the Cooperator.
	 * @throws IllegalArgumentException if id is null.
	 * @return a CooperatorDto object.
	 * @sample /cooperators/create?id=1
	 * */
	@PostMapping(value = { "/cooperators/create", "/cooperators/create/" })
	public CooperatorDto CreateCooperator(@RequestParam("id") Integer coopID) throws IllegalArgumentException {
		// @formatter:on

		cooperatorService.create(coopID);
		return convertToDto(coopID);
	}
	/**
	 * Responses to the HTTP POST call on creating a Term.
	 * @param date1 deadline of Student's evaluation.
	 * @param date2 deadline of CoopTerm's evaluation.
	 * @return a TermDto object.
	 * @sample /terms/create/?id=2112&name=Winter2019&studentdeadline=2019-3-22&coopdeadline=2019-4-4
	 * */
	@PostMapping(value = { "/terms/create", "/terms/create/" })
	public TermDto CreateTerm(@RequestParam("id") String termID, @RequestParam("name") String name,
			@RequestParam("studentdeadline") String date1, @RequestParam("coopdeadline") String date2) {
		// @formatter:on
		Date studentEvalFormDeadline = Date.valueOf(date1); // form of date: "2015-06-01"
		Date coopEvalFormDeadline = Date.valueOf(date2);
		Term term = termService.create(termID, name, studentEvalFormDeadline, coopEvalFormDeadline);
		return convertToDto(term);
	}

	/**
	 * Response to the HTTP POST call on creating a Student.
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
	 * @sample /students/create/?id=226433222&firstname=Yen-Vi&lastname=Huynh&cooperatorid=1
	 */
	@PostMapping(value = { "/students/create", "/students/create/" })
	public StudentDto createStudent(@RequestParam("id") String studentID, @RequestParam("firstname") String firstName,
			@RequestParam("lastname") String lastName, @RequestParam("cooperatorid") Integer cooperatorID)
			throws IllegalArgumentException {
		// @formatter:on
		Cooperator coop = cooperatorService.get(cooperatorID);

		Student student = studentService.create(studentID, firstName, lastName, coop);
		return convertToDto(student);
	}
	/**
	 * Maps a number to the corresponding term status.
	 * 0 --> ONGOING,
	 * 1 --> FINISHED,
	 * 2 --> FAILED.
	 * @param x the number to be converted.
	 * @return a TermStatus after conversion.
	 * */
	private static TermStatus ConvertTermStatus(int x) {
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
    /**
	 * Maps a number to the corresponding grade.
	 * 0 --> A,
	 * 1 --> B,
	 * 2 --> C,
	 * 3 --> D,
	 * 4 --> F,
	 * 5 --> NOT GRADED.
	 * @param x the number to be converted.
	 * @return a Grade object after conversion.
	 * */
    private static Grade ConvertGrade(int x) {
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

	/**
	 * Responses to the HTTP POST call on creating a CoopTermRegistration.
	 * @param registrationID the id of the CoopTermRegistration.
	 * @param jobID the corresponding job id of the CoopTermRegistration.
	 * @param studentID the student ID attached to this CoopTermRegistration.
	 * @param termID the term ID attached to this CoopTermRegistration.
	 * @param termStat the status of the term related to this CoopTermRegistration.
	 * @param gradeID the outcome (grade) related to this CoopTermRegistration.
	 * @return a CoopTermRegistrationDto object.
	 * @sample /coopTermRegistrations/create/?registrationid=1&jobid=142412&studentid=226433222&termid=2112&termstat=0&gradeid=5
	 * */
	@PostMapping(value = { "/coopTermRegistrations/create", "/coopTermRegistrations/create/" })
	public CoopTermRegistrationDto createCoopTermRegistration(
			@RequestParam("registrationid") String registrationID,
			@RequestParam("jobid") String jobID,
			@RequestParam("studentid") String studentID,
			@RequestParam("termid") String termID,
			@RequestParam("termstat") Integer termStat,
			@RequestParam("gradeid") Integer gradeID) {
		// get relevant information
		Student student = studentService.get(studentID);
		Term term = termService.get(termID);
		// convert input data to the appropriate format
		TermStatus mystatus = ConvertTermStatus(termStat);
		Grade mygrade = ConvertGrade(gradeID);
		// finally, we can create a CoopTermRegistration.
		CoopTermRegistration internship = coopTermRegistrationService.create(registrationID, jobID, mystatus,
				mygrade, student, term);
		return convertToDto(internship, "NONE", "NONE");
	}
    /**
	 * Responses to the HTTP POST call on creating a Course.
	 * @param id the unique id of the Course.
	 * @param term the term id related to the Course.
	 * @param name the name of the Course.
	 * @param rank the rank of the Course.
	 * @param cooperatorID the related Cooperator of this Course.
	 * @return a CourseDto object.
	 * @sample /courses/create?id=1234&term=2112&name=hahaha&rank=10&cooperatorid=1
	 * */
	@PostMapping(value = { "/courses/create", "/events/create/" })
    @ResponseBody
    public CourseDto createCourse(@RequestParam("id") String id, 
    		@RequestParam("term") String term, 
    		@RequestParam("name") String name, 
    		@RequestParam("rank") String rank,
    		@RequestParam("cooperatorid") Integer cooperatorID) {
    	    	
		Cooperator c = cooperatorService.get(cooperatorID);

    	Course course = courseService.create(id, term, name, Integer.parseInt(rank), c);
    	return convertCourseToDto(course);
    }
    
    /**
	 * Response to the HTTP POST request on creating a Form.
     * The type of Form is Student's evaluation form.
	 * @param formID the unique form id.
	 * @param pdfLink the link to this Form (documentation).
	 * @param ctrID the CoopTermRegistration's id related to this Form.
	 * @return a FormDto.
	 * @sample /students/report/create/?formid=123&pdflink=test.com&ctrid=1
	 * */
    @PostMapping(value = { "/students/report/create", "/student/report/create/" })
    @ResponseBody
    public FormDto createStudentForm(@RequestParam("formid") String formID,
    		@RequestParam("pdflink") String pdfLink, 
    		@RequestParam("ctrid") String ctrID) {
    	    	
		CoopTermRegistration ctr = coopTermRegistrationService.get(ctrID);
		String formName = ctr.getStudent().getStudentID(); 
		FormType formType = FormType.STUDENTEVALUATION;

    	Form form = formService.create(formID, formName, pdfLink, formType, ctr);
    	return convertFormToDto(form);
    }

    /**
     * Response to the HTTP POST request on creating a Form.
     * The type of Form is Employer's evaluation form.
     * @param formID the unique id of the form
     * @param pdfLink the link leading to the pdf file.
     * @param ctrID the CoopTermRegistrations related to this form.
     * @return a FormDto object.
     * @sample /students/report/create/?formid=123&pdflink=test.com&ctrid=1
     */
    @PostMapping(value = { "/students/employereval/create", "/student/employereval/create/" })
    @ResponseBody
    public FormDto createEmployerForm(@RequestParam("formid") String formID,
    		@RequestParam("pdflink") String pdfLink, 
    		@RequestParam("ctrid") String ctrID) {
    	    	
		CoopTermRegistration ctr = coopTermRegistrationService.get(ctrID);
		String formName = ctr.getStudent().getStudentID(); 
		FormType formType = FormType.COOPEVALUATION;

    	Form form = formService.create(formID, formName, pdfLink, formType, ctr);
    	return convertFormToDto(form);
    }

    /**
     * Responses to the HTTP PUT request on updating Student's problematic status.
     * @param studentID the id of the Student to be updated.
     * @param isProblematic the new Problematic status.
     * @return a StudentDto object.
     * @sample /students/update/?id=226433222&status=true
     */
	@PutMapping(value = {"/students/update", "/students/update/"})
	public StudentDto updateStudentStatus(@RequestParam("id") String studentID, @RequestParam("status") boolean isProblematic) {
		return convertToDto(studentService.updateProblematicStatus(studentService.get(studentID), isProblematic));
	}

    /**
     * Response to HTTP GET request on retrieving a LIST of CoopTermRegistrations.
     * @return a List of CoopTermRegistrationDtos.
     * @sample /coopTermRegistrations/list
     */
 	@GetMapping(value = { "/coopTermRegistrations/list", "/coopTermRegistrations/list/", "/coopTermRegistrations",
 			"/coopTermRegistrations/" })
 	@ResponseBody
 	public List<CoopTermRegistrationDto> viewCoopTermRegistrations() {
 		// @formatter:on
 		Set<CoopTermRegistration> internships = coopTermRegistrationService.getAll();
 		List<CoopTermRegistrationDto> internshipsDto = new ArrayList<CoopTermRegistrationDto>();

 		if (internships != null) {
 			for (CoopTermRegistration intern : internships) {
 				Set<Form> forms = intern.getForm();
 				String employerFormLink = "NONE";
 				String studentFormLink = "NONE";
 				for(Form form : forms) {
 					if(form.getFormType() == FormType.STUDENTEVALUATION)
 						studentFormLink = form.getPdfLink();
 					else
 						employerFormLink = form.getPdfLink();
 				}
 				internshipsDto.add(convertToDto(intern, studentFormLink, employerFormLink));
 			}
 		}
 		return internshipsDto;
 	}

    /**
     * Responses to the HTTP GET request on retrieving all grades related to all CoopTermRegistration.
     * @return a Set of Grade objects.
     * @sample /coopTermRegistrations/grades/
     */
 	@GetMapping(value = { "/coopTermRegistrations/grades", "/coopTermRegistrations/grades/" })
 	@ResponseBody
 	public Set<Grade> viewGrades() {

 		Set<CoopTermRegistration> internships = coopTermRegistrationService.getAll();
 		Set<Grade> grades = new HashSet<Grade>();

 		for (CoopTermRegistration intern : internships) {
 			grades.add(intern.getGrade());
 		}
 		return grades;
 	}

    /**
     * Responses to HTTP GET request on retrieving a specific Course given its id and term.
     * @param courseID the unique id of the Course to be retrieved.
     * @param term the Term name that the Course belongs to.
     * @return a CourseDto object wrapping the result.
     * @sample /courses/specific/1234
     */
    @GetMapping(value = {"/courses/specific", "/courses/specific/"})
    @ResponseBody
    public CourseDto getCourse(@RequestParam("id") String courseID, 
    		@RequestParam("term") String term) {
    	if (courseID == null || courseID.isEmpty()) {
    		throw new IllegalArgumentException();
    	}
    	return convertCourseToDto(courseService.get(courseID, term));
    }
    
    /**
     * Responses to HTTP GET request on retrieving n first useful course, using courseRank to make comparison.
     * @param quantity number of courses wanted to retrieve.
     * @return a list of n useful courses.
     * @sample /courses/filter?quantity=2
     * */
    @GetMapping(value = {"/courses/filter", "courses/filter/"})
    @ResponseBody
    public List<CourseDto> getCourses(@RequestParam(value = "quantity", defaultValue = "-1", required = false)int quantity,
									  @RequestParam(value = "order", defaultValue = "descending", required = false) String order) {
		ArrayList<CourseDto> returnList = new ArrayList<>(getCourses());
    	if (!order.equals("ascending")) {
			Collections.reverse(returnList);
		}
		// new ArrayList<CourseDto>(getCourses().subList(0, (quantity < getCourses().size()) ? quantity : getCourses().size()))
		if (quantity != -1) {
			return returnList.subList(0, (quantity < getCourses().size()) ? quantity : getCourses().size());
		}
		return returnList;
    }

    /**
     * Responses to HTTP GET request on retrieving all CoopTermRegistrations related to a SPECIFIC Student.
     * @param studentID the id of the Student
     * @return a List of CoopTermRegistrationDtos related to the Student.
     */
    @GetMapping(value = { "/coopTermRegistrations/listByStudent/", "/coopTermRegistrations/listByStudent/" })
 	@ResponseBody
 	public List<CoopTermRegistrationDto> viewCoopTermRegistrationsOfStudent(@RequestParam("studentid") String studentID) {

 		Set<CoopTermRegistration> internships = coopTermRegistrationService.getByStudentID(studentID);
 		List<CoopTermRegistrationDto> internshipsDto = new ArrayList<CoopTermRegistrationDto>();

 		if (internships != null) {
 			for (CoopTermRegistration intern : internships) {
 				Set<Form> forms = intern.getForm();
 				String employerFormLink = "NONE";
 				String studentFormLink = "NONE";
 				for(Form form : forms) {
 					if(form.getFormType() == FormType.STUDENTEVALUATION)
 						studentFormLink = form.getPdfLink();
 					else
 						employerFormLink = form.getPdfLink();
 				}
 				internshipsDto.add(convertToDto(intern, studentFormLink, employerFormLink));
 			}
 		}
 		return internshipsDto;
 	}

    /**
     * Responses to the HTTP GET request on retrieving all CoopTermRegistrations related to a SPECIFIC Student,
     * filtered by a SPECIFIC Term.
     * @param termName the name of the Term to be filtered.
     * @param studentID the unique id of the Student.
     * @return a List of CoopTermRegistrationDtos wrapping the results.
     */
    @GetMapping(value = { "/coopTermRegistrations/listByTermAndStudent", "/coopTermRegistrations/listByTermAndStudent/" })
 	@ResponseBody
 	public List<CoopTermRegistrationDto> viewCoopTermRegistrationsByTermNameAndStudentID(@RequestParam("termname") String termName, @RequestParam("studentid") String studentID) {

 		Set<CoopTermRegistration> internships = coopTermRegistrationService.getByTermNameAndStudentID(termName, studentID);
 		List<CoopTermRegistrationDto> internshipsDto = new ArrayList<CoopTermRegistrationDto>();

 		if (internships != null) {
 			for (CoopTermRegistration intern : internships) {
 				Set<Form> forms = intern.getForm();
 				String employerFormLink = "NONE";
 				String studentFormLink = "NONE";
 				for(Form form : forms) {
 					if(form.getFormType() == FormType.STUDENTEVALUATION)
 						studentFormLink = form.getPdfLink();
 					else
 						employerFormLink = form.getPdfLink();
 				}
 				internshipsDto.add(convertToDto(intern, studentFormLink, employerFormLink));
 			}
 		}
 		return internshipsDto;
 	}

    /**
     * Responses to the HTTP GET request on retrieving
     * all CoopTermRegistrations on a specific Term.
     * @param termName the name of the Term.
     * @return a List of CoopTermRegistrationDtos wrapping the results.
     */
    @GetMapping(value = { "/coopTermRegistrations/listByTerm", "/coopTermRegistrations/listByTerm/" })
 	@ResponseBody
 	public List<CoopTermRegistrationDto> viewCoopTermRegistrationsByTermName(@RequestParam("termname") String termName) {

 		Set<CoopTermRegistration> internships = coopTermRegistrationService.getByTermName(termName);
 		List<CoopTermRegistrationDto> internshipsDto = new ArrayList<CoopTermRegistrationDto>();

 		if (internships != null) {
 			for (CoopTermRegistration intern : internships) {
 				Set<Form> forms = intern.getForm();
 				String employerFormLink = "NONE";
 				String studentFormLink = "NONE";
 				for(Form form : forms) {
 					if(form.getFormType() == FormType.STUDENTEVALUATION)
 						studentFormLink = form.getPdfLink();
 					else
 						employerFormLink = form.getPdfLink();
 				}
 				internshipsDto.add(convertToDto(intern, studentFormLink, employerFormLink));
 			}
 		}
 		return internshipsDto;
 	}
    



 	/*
     * HELPER METHODS
     */
	private StudentDto convertToDto(Student e) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this Cooperator!");
		}
		StudentDto studentDto = new StudentDto(e.getStudentID(), e.getFirstName(), e.getLastName(),
				e.isIsProblematic());
		return studentDto;
	}

	// convert to Dto CoopTermRegistration
	private CoopTermRegistrationDto convertToDto(CoopTermRegistration e, String studentFormLink, String employerFormLink) {
		if (e == null) {
			throw new IllegalArgumentException("There student doens't exist in this CoopTermRegistration!");
		}
		CoopTermRegistrationDto coopTermRegistrationDto = new CoopTermRegistrationDto(e.getRegistrationID(), e.getTerm().getTermName(),
				e.getJobID(), e.getTermStatus(), e.getGrade(), e.getStudent().getStudentID(), studentFormLink, employerFormLink);
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

    /**
     * Responses to HTTP GET request on retrieving a list of problematic Students.
     *
     * @return a List of StudentDtos containing problematic Students.
     * @sample /students/problematic
     */
	@GetMapping(value = { "/students/problematic", "/students/problematic" })
	@ResponseBody
	public List<StudentDto> getProblematicStudents() {
		// @formatter:on
		List<Student> students = studentService.getAllProblematicStudents();
		List<StudentDto> mylist = new ArrayList<StudentDto>();
		// check for every student;
		for (Student s : students) {
			mylist.add(convertToDto(s));
		}
		return mylist;
	}

    /**
     * Responses to the HTTP GET request on retrieving a List of all Students available
     * in the database.
     * @return a List of StudentDto having all the Students in the database.
     * @sample /students/list
     */
	@GetMapping(value = { "/students/list", "/students/list", "/students", "/students/" })
	@ResponseBody
	public List<StudentDto> getListStudents() {
		// @formatter:on
		Set<Student> students = studentService.getAll();
		List<StudentDto> mylist = new ArrayList<StudentDto>();

		// check for every student;
		for (Student s : students) {
			mylist.add(convertToDto(s));
		}

		return mylist;
	}

    /**
     * Responses to the HTTP GET request on
     * querying a specific Student in a list of all problematic Students.
     * @param studentID the specific id of a problematic Student.
     * @return a List of StudentDto.
     */
	@GetMapping(value = { "/students/problematic/listByID", "/students/problematic/listByID/" })
	@ResponseBody
	public List<StudentDto> getListStudentsByIDAndStatus(@RequestParam("studentid") String studentID) {
		// @formatter:on
		Set<Student> students = studentService.getByIDAndStatus(studentID, true);
		List<StudentDto> mylist = new ArrayList<StudentDto>();
		// check for every student;
		for (Student s : students) {
			mylist.add(convertToDto(s));
		}
		return mylist;
	}

    /**
     * Responses to HTTP GET request on retrieving a specific Student, but wraps this single Student
     * object to a List.
     * @param studentID the id of this specific Student
     * @return a List wrapped this Student.
     * @sample /students/report/226433222
     */
	@GetMapping(value = { "/students/listByID", "/students/listByID/" })
	@ResponseBody
	public List<StudentDto> getListStudentsByID(@RequestParam("studentid") String studentID) {
		// @formatter:on
		Student s = studentService.get(studentID);
		List<StudentDto> mylist = new ArrayList<StudentDto>();
		
		mylist.add(convertToDto(s));
			
		return mylist;
	}

    /**
     * Response to HTTP GET request on retrieving a report of a specific Student.
     * @param studentID the id of this specific Student.
     * @return the report (Form object) of the Student.
     */
	@GetMapping(value = { "/students/report/{studentID}", "/students/report/{studentID}" })
	public StudentformDto getStudentReport(@PathVariable("studentID") String studentID) {
		// @formatter:on

		/*Student mystudent = studentService.get(studentID);

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
		
		Set<Form> myformlist = formService.getAllStudentEvalForms();
		List<String> arrayList = new ArrayList<String>();
		for (Form f : myformlist) {
			if(f.getCoopTermRegistration().getStudent().getStudentID().equals(studentID)) {
				arrayList.add(f.getPdfLink());
			}
		}
		StudentformDto mystudentforms = new StudentformDto(studentID, arrayList);
		return mystudentforms;
	}

    /**
     * Responses to HTTP GET request on retrieving all employer's evaluation form of a specific Student.
     * @param studentID the ID of this specific Student.
     * @return a FormDto object, containing the Employer's evaluation form.
     */
	@GetMapping(value = { "/students/employereval/{studentID}", "/students/employereval/{studentID}" })
	public EmployerformDto getAllEmployerEval(@PathVariable("studentID") String studentID) {

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
		
		Set<Form> myformlist = formService.getAllEmployerEvalForms();
		List<String> arrayList = new ArrayList<String>();
		for (Form f : myformlist) {
			if(f.getCoopTermRegistration().getStudent().getStudentID().equals(studentID)) {
				arrayList.add(f.getPdfLink());
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
    	Set<Course> courseSet = courseService.getAll();
    	List<CourseDto> courseList = new ArrayList<CourseDto>();
    	for (Course course : courseSet) {
    		courseList.add(convertCourseToDto(course));
    	}
    	Collections.sort(courseList);
    	return courseList;
    }

    /**
     * Responses to HTTP POST request on adjudicating success of an internship.
     * @param registrationID the registration ID.
     * @param success the success status.
     * @return a CoopTermRegistration object.
     * @sample /coopTermRegistrations/1/adjudicate/?success=true
     */
 	@PostMapping(value = { "/coopTermRegistrations/{registrationID}/adjudicate",
 			"/coopTermRegistrations/{registrationID}/adjudicate/" })
 	public CoopTermRegistrationDto adjudicateTermRegistration(@PathVariable("registrationID") String registrationID,
 			@RequestParam("success") boolean success) {
 		CoopTermRegistration termRegistration = coopTermRegistrationService.get(registrationID);
 		if (success)
 			termRegistration = coopTermRegistrationService.updateTermStatus(termRegistration, TermStatus.FINISHED);
 		else
 			termRegistration = coopTermRegistrationService.updateTermStatus(termRegistration, TermStatus.FAILED);
 		
 		Set<Form> forms = termRegistration.getForm();
 		String employerFormLink = "NONE";
 		String studentFormLink = "NONE";
 		for(Form form : forms) {
 			if(form.getFormType() == FormType.STUDENTEVALUATION)
 				studentFormLink = form.getPdfLink();
 			else
 				employerFormLink = form.getPdfLink();
 		}

 		return convertToDto(termRegistration, studentFormLink, employerFormLink);
 	}
}