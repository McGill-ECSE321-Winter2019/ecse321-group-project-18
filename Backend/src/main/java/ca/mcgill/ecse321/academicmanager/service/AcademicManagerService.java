package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.*;

import ca.mcgill.ecse321.academicmanager.exceptions.*;

import ca.mcgill.ecse321.academicmanager.model.*;

/**
 * The Service class basically stands between Java and Hibernate's persistence layer.
 * Instead of directly used CRUD repository's methods, we interact with the database via
 * the set of public methods in this class.
 * @author Saleh Bakhit, Bach Tran, Yen-Vi Huynh, Edward Huang, Moetassem Abdelazim
 * @since Sprint 1
 * */
@Service
public class AcademicManagerService {

	@Autowired
	CooperatorRepository cooperatorRepository;
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	FormRepository formRepository;
	@Autowired
	MeetingRepository meetingRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	TermRepository termRepository;
	/*
	* Services for developers
	* */
	/**
	 * Clears all records (objects) currently existing in the database.
	 * The database will be set to blank, i.e. all tables contain no values.
	 * Warning: after applying this method, there's no way to retrieve back the database. Use with care!
	 * @author Bach Tran
	 * */
	@Transactional
	public void clearDatabase() {
		termRepository.deleteAll();
		meetingRepository.deleteAll();
		formRepository.deleteAll();
		courseRepository.deleteAll();
		coopTermRegistrationRepository.deleteAll();
		studentRepository.deleteAll();
		coopTermRegistrationRepository.deleteAll();
	}

	/*
	* COOPERATOR SERVICES
	* */
	/**
	 * Creates a new Cooperator object & persists it in the database.
	 * @param id an unique integer of the Cooperator.
	 * @return a Cooperator object created.
	 * */
	@Transactional
	public Cooperator createCooperator(Integer id) {
		if (!checkArg(id)) {
			throw new NullArgumentException();
		}
		Cooperator c = new Cooperator();
		c.setId(id);

		return cooperatorRepository.save(c);
	}
	/**
	 * Retrieves a Cooperator with the corresponding id from the database.
	 * @param id the unique id of the Cooperator to be queried.
	 * @return the Cooperator with the corresponding id.
	 * */
	@Transactional
	public Cooperator getCooperator(Integer id) {
		return cooperatorRepository.findByid(id);
	}
	/**
	 * Retrieves all Cooperator currently existing in the database.
	 * @author Saleh Bakhit
	 * @return a Set of all Cooperators existing in the database.
	 * */
	@Transactional
	public Set<Cooperator> getAllCooperators() {
		return toSet(cooperatorRepository.findAll());
	}

	/*
	* COOP TERM REGISTRATONS (CTR) SERVICES
	* */
	/**
	 * Creates a new coop term registration instance
	 * @param registrationID id of the CoopTermRegistration instance
	 * @param jobID          id of the job
	 * @param status         status of the internship
	 * @param grade          grade of the internship
	 * @param student        student associated with the internship
	 * @param term           the term of the internship
	 * @return CoopTermRegistration instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 * @deprecated this method has been moved to another class since refactoring.
	 * @author Saleh Bakhit
	 * */
	@Transactional
	public CoopTermRegistration createCoopTermRegistration(String registrationID, String jobID, TermStatus status,
			Grade grade, Student student, Term term) {
		if (!checkArg(registrationID) || !checkArg(jobID) || !checkArg(status) || !checkArg(student)
				|| !checkArg(term)) {
			throw new NullArgumentException();
		}

		CoopTermRegistration ctr = new CoopTermRegistration();
		ctr.setRegistrationID(registrationID);
		ctr.setTermStatus(status);
		ctr.setJobID(jobID);
		ctr.setGrade(grade);

		// check if student is already registered in that term
		Set<CoopTermRegistration> StudentCtrs = student.getCoopTermRegistration();
		if(StudentCtrs != null) {
			for(CoopTermRegistration ctrTemp : StudentCtrs) {
				if(ctrTemp.getTerm().equals(term)) {
					throw new IllegalAddException("Student is already registerd for the given term");
				}
			}
		}
		
		ctr.setStudent(student);
		ctr.setTerm(term);
		return coopTermRegistrationRepository.save(ctr);
	}
	/**
	 * Updates term status and grade of a specific CoopTermRegistration.
	 * @param ctr the CoopTermRegistration object to be updated.
	 * @param status the new term status.
	 * @param grade the new grade.
	 * @return an updated CoopTermRegistration.
	 * @author Saleh Bakhit.
	 * @deprecated this method has been broken down into 2 separated ones.
	 * */
	@Transactional
	public CoopTermRegistration updateCoopTermRegistration(CoopTermRegistration ctr, TermStatus status, Grade grade) {
		if (checkArg(status)) {
			ctr.setTermStatus(status);
		}
		if (checkArg(grade)) {
			ctr.setGrade(grade);
		}

		return coopTermRegistrationRepository.save(ctr);
	}
	/**
	 * Retrieves a specific CoopTermRegistration by its id.
	 * @param registrationID the unique id of this CoopTermRegistration.
	 * @return a CoopTermRegistration with corresponding id.
	 * @author Saleh Bakhit.
	 * */
	@Transactional
	public CoopTermRegistration getCoopTermRegistration(String registrationID) {
		return coopTermRegistrationRepository.findByRegistrationID(registrationID);
	}
	/**
	 * Retrieves all existing CoopTermRegistrations available in the database.
	 * @return a Set of all CoopTermRegistrations in the database.
	 * @author Saleh Bakhit.
	 */
	@Transactional
	public Set<CoopTermRegistration> getAllCoopTermRegistration() {
		return toSet(coopTermRegistrationRepository.findAll());
	}
	/**
	 * Gets all CoopTermRegistrations of a Student.
	 * @param studentID the student ID desired for the query.
	 * @return a Set of all CoopTermRegistrations related to this Student.
	 * @deprecated this method has been moved to another class due to refactoring.
	 * @see CoopTermRegistrationService
	 * */
	@Transactional
	public Set<CoopTermRegistration> getCoopTermRegistrationsByStudentID(String studentID) {
		Set<CoopTermRegistration> ret = new HashSet<CoopTermRegistration>();
		
		Set<CoopTermRegistration> ctrs = toSet(coopTermRegistrationRepository.findAll());
		for(CoopTermRegistration ctr : ctrs) {
			if(ctr.getStudent().getStudentID().equals(studentID))
				ret.add(ctr);
		}
		return ret;
	}
	/**
	 * Gets all CoopTermRegistration of a Term.
	 * @param termName a String contains the Term name.
	 * @return a Set of all CoopTermRegistrations attached to that term.
	 * @author Saleh Bakhit.
	 * @deprecated this method has been moved to another class due to refactoring.
	 * @see CoopTermRegistrationService
	 * */
	@Transactional
	public Set<CoopTermRegistration> getCoopTermRegistrationsByTermName(String termName) {
		Set<CoopTermRegistration> ret = new HashSet<CoopTermRegistration>();
		Set<CoopTermRegistration> ctrs = toSet(coopTermRegistrationRepository.findAll());
		for(CoopTermRegistration ctr : ctrs) {
			if(ctr.getTerm().getTermName().equals(termName))
				ret.add(ctr);
		}
		return ret;
	}
	/**
	 * Gets CoopTermRegistration instances based on termName and studentID
	 *
	 * @param termName termName name of a term
	 * @param studentID studentID id of a student
	 * @return Set of CoopTermRegistration instances
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see CoopTermRegistrationService
	 */
	@Transactional
	public Set<CoopTermRegistration> getCoopTermRegistrationsByTermNameAndStudentID(String termName, String studentID) {
		Set<CoopTermRegistration> ret = new HashSet<CoopTermRegistration>();
		Set<CoopTermRegistration> ctrs = toSet(coopTermRegistrationRepository.findAll());
		for(CoopTermRegistration ctr : ctrs) {
			if(ctr.getTerm().getTermName().equals(termName) && ctr.getStudent().getStudentID().equals(studentID) )
				ret.add(ctr);
		}
		return ret
				;
	}


	/*
	* COURSE SERVICES
	* */
	/**
	 * Creates a new course instance
	 *
	 * @param courseID   id of the Course instance
	 * @param term       term of offering
	 * @param courseName name of Course
	 * @param rank       usefulness rank of Course given by students
	 * @param c          Cooperator instance
	 * @return Course instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see CourseService
	 */
	@Transactional
	public Course createCourse(String courseID, String term, String courseName, Integer rank, Cooperator c) {
		if (!checkArg(courseID) || !checkArg(term) || !checkArg(courseName) || !checkArg(c)) {
			throw new NullArgumentException();
		}

		Course course = new Course();
		course.setCourseID(courseID);
		course.setTerm(term);
		course.setCourseName(courseName);
		course.setCourseRank(rank);

		course.setCooperator(c);
		return courseRepository.save(course);
	}
	/**
	 * Updates rank of Course
	 *
	 * @param course Course instance
	 * @param rank   new rank given
	 * @return updated Course instance
	 * @author Saleh Bakhit
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see CourseService
	 */
	@Transactional
	public Course updateCourseRank(Course course, Integer rank) {
		if(checkArg(rank))
			course.setCourseRank(rank);
		return courseRepository.save(course);
	}

	@Transactional
	public Course getCourse(String courseID, String term) {
		return courseRepository.findByCourseIDAndTerm(courseID, term);
	}

	@Transactional
	public Set<Course> getAllCourses() {
		return toSet(courseRepository.findAll());
	}
	// ---Course---

	/*
	* FORM SERVICES
	* */
	/**
	 * Creates a new Form instance
	 *
	 * @param formID   id of the Form
	 * @param name     name of the Form
	 * @param pdflink  link to the Form
	 * @param formtype Type of the Form
	 * @param ctr      CoopTermRegistration instance associated with Form
	 * @return Form instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see FormService
	 */
	@Transactional
	public Form createForm(String formID, String name, String pdflink, FormType formtype, CoopTermRegistration ctr) {
		if (!checkArg(name) || !checkArg(pdflink) || !checkArg(formtype) || !checkArg(ctr)) {
			throw new NullArgumentException();
		}

		Form form = new Form();
		form.setFormID(formID);
		form.setName(name);
		form.setPdfLink(pdflink);
		form.setFormType(formtype);
		form.setCoopTermRegistration(ctr);
    
    return formRepository.save(form);
	}
	/**
	 * Updates a Form with all the entities needed.
	 * @param form the Form object to be updated.
	 * @param formID the new formID.
	 * @param name the new name.
	 * @param pdflink the new pdf link.
	 * @param formtype the new type.
	 * @return an updated Form object.
	 * @author Saleh Bakhit.
	 * @deprecated it is better to use the methods in the FormService class.
	 * @see FormService
	 * */
	@Transactional
	Form updateForm(Form form, String formID, String name, String pdflink, FormType formtype) {
		if (!checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (checkArg(formID)) {
			form.setFormID(formID);
		}
		if (checkArg(name)) {
			form.setName(name);
		}
		if (checkArg(pdflink)) {
			form.setPdfLink(pdflink);
		}
		if (checkArg(formtype)) {
			form.setFormType(formtype);
		}

		return formRepository.save(form);
	}
	/**
	 * Gets Form instance based on formId.
	 * @param formId id of the Form
	 * @return Form instance
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see FormService
	 */
	@Transactional
	public Form getForm(String formId) {
		return formRepository.findByFormID(formId);
	}
	/**
	 * Gets employer evaluation Form instances.
	 * @return Set of employer evaluation Form instances.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see FormService
	 */
	@Transactional
	public Set<Form> getAllEmployerEvalForms() {
		Set<CoopTermRegistration> ctrs = this.getAllCoopTermRegistration();

		Set<Form> forms = new HashSet<Form>();
		Set<Form> employerForms = new HashSet<Form>();

		for (CoopTermRegistration ctr : ctrs) {
			forms = ctr.getForm();
			for (Form form : forms) {
				if (form.getFormType() == FormType.COOPEVALUATION) {
					employerForms.add(form);
				}
			}
		}
		return employerForms;
	}
	/**
	 * Gets student evaluation Form instances
	 * @return Set of student evaluation Form instances.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see FormService
	 */
	@Transactional
	public Set<Form> getAllStudentEvalForms() {
		Set<CoopTermRegistration> ctrs = this.getAllCoopTermRegistration();

		Set<Form> forms = new HashSet<Form>();
		Set<Form> studentForms = new HashSet<Form>();

		for (CoopTermRegistration ctr : ctrs) {
			forms = ctr.getForm();
			for (Form form : forms) {
				if (form.getFormType() == FormType.STUDENTEVALUATION) {
					studentForms.add(form);
				}
			}
		}
		return studentForms;
	}
	// ---Form---

	// ---Meeting---
	/*@Transactional
	public Meeting createMeeting(String meetingID, String location, String details, Date date, Time startTime,
			Time endTime) {
		// check for nulls
		if (!checkArg(meetingID) || !checkArg(location) || !checkArg(startTime) || !checkArg(endTime)) {
			throw new NullArgumentException();
		}

		// check for invalid time constraints
		if (endTime.compareTo(startTime) < 0) {
			throw new InvalidEndTimeException();
		}

		Meeting meeting = new Meeting();
		meeting.setMeetingID(meetingID);
		meeting.setLocation(location);
		meeting.setDate(date);
		meeting.setStartTime(startTime);
		meeting.setEndTime(endTime);

		if (checkArg(details)) {
			meeting.setDetails(details);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting addMeetingStudent(Meeting meeting, Student student) {
		if (!checkArg(student)) {
			throw new NullArgumentException();
		}
    
		meeting.addStudent(student);

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting updateMeeting(Meeting meeting, String location, String details, Date date, Time startTime,
			Time endTime, Student student) {
		if (checkArg(location)) {
			meeting.setLocation(location);
		}
		if (checkArg(details)) {
			meeting.setDetails(details);
		}
		if (checkArg(date)) {
			meeting.setDate(date);
		}
		if (checkArg(startTime)) {
			meeting.setStartTime(startTime);
		}
		if (checkArg(endTime)) {
			meeting.setEndTime(endTime);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting getMeeting(String meetingID) {
		return meetingRepository.findByMeetingID(meetingID);
	}

	@Transactional
	public Set<Student> getMeetingStudents(Meeting meeting) {
		return meeting.getStudent();
	}

	@Transactional
	public Set<Meeting> getAllMeetings() {
		return toSet(meetingRepository.findAll());
	}*/
	// ---Meeting---

	// ---Student---
	/**
	 * Creates a new Student instance
	 * @param studentID id of the student
	 * @param firstname first name of the student
	 * @param lastname  last name of the student
	 * @param c         Cooperator instance
	 * @return Student instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see StudentService
	 */
	@Transactional
	public Student createStudent(String studentID, String firstname, String lastname, Cooperator c) {
		if (!checkArg(studentID) || !checkArg(firstname) || !checkArg(lastname) || !checkArg(c)) {
			throw new NullArgumentException();
		}

		Student student = new Student();

		student.setStudentID(studentID);
		student.setFirstName(firstname);
		student.setLastName(lastname);
		student.setIsProblematic(false);
		student.setCooperator(c);
		
		return studentRepository.save(student);
	}

	@Transactional
	public Student addStudentMeeting(Student student, Meeting meeting) {
		if(!checkArg(meeting)) {
			throw new NullArgumentException();
		}
		student.addMeeting(meeting);
    
		return studentRepository.save(student);
	}

//	@Transactional
//	public Student addStudentMeeting(Student student, Meeting meeting) {
//		if (!checkArg(meeting)) {
//			throw new NullArgumentException();
//		}
//
//		student.addMeeting(meeting);
//		meeting.addStudent(student);
//
//		return studentRepository.save(student);
//	}
	/**
	 * Updates problematic status of Student
	 * @param student       Student instance
	 * @param isProblematic new status
	 * @return updated Student instance.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see StudentService
	 */
	@Transactional
	public Student updateStudentProblematicStatus(Student student, boolean isProblematic) {
		student.setIsProblematic(isProblematic);
		return studentRepository.save(student);
	}
	/**
	 * Gets all problematic Students.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @return Set of Student instances
	 * @see StudentService
	 */
	@Transactional
	public List<Student> getAllProblematicStudents() {
		return studentRepository.findByIsProblematic(true);
	}
	/**
	 * Gets a Student given the id.
	 * @param studentID the Student id to be queried.
	 * @return a Student object having the given id.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see StudentService
	 * */
	@Transactional
	public Student getStudent(String studentID) {
		return studentRepository.findByStudentID(studentID);
	}
	/**
	 * Gets all Student existing in the database.
	 * @return a Set of all Students available in the database.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see StudentService
	 * */
	@Transactional
	public Set<Student> getAllStudents() {
		return toSet(studentRepository.findAll());
	}
	/**
	 * Gets Student's grade based on a CoopTermRegistration.
	 * @param ctr the CoopTermRegistration object to be queried.
	 * @return a Grade object of of that CoopTermRegistration.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see StudentService
	 * */
	@Transactional
	public Grade getStudentGrade(CoopTermRegistration ctr) {
		return ctr.getGrade();
	}
	/**
	 * Gets a list of Students based on their id and status.
	 * @param studentID the unique id of the Student.
	 * @param isProblematic the boolean value checking the Student's status.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see StudentService
	 * */
	@Transactional
	public Set<Student> getStudentsByIDAndStatus(String studentID, boolean isProblematic) {
		return toSet(studentRepository.findByStudentIDAndIsProblematic(studentID, isProblematic));
	}

	// ---Student---

	/*
	* TERM SERVICES
	* */
	/**
	 * Creates a new Term instance
	 *
	 * @param termID                  id of the term
	 * @param termName                name of the term
	 * @param studentEvalFormDeadline student evaluation deadline
	 * @param coopEvalFormDeadline    coop evaluation deadline
	 * @return Term instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see TermService
	 */
	@Transactional
	public Term createTerm(String termID, String termName, Date studentEvalFormDeadline, Date coopEvalFormDeadline) {
		if (!checkArg(termID)) {
			throw new NullArgumentException();
		}

		Term term = new Term();
		term.setTermID(termID);
		term.setTermName(termName);
		term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		term.setCoopEvalFormDeadline(coopEvalFormDeadline);

		return termRepository.save(term);
	}
	//
	// @Transactional
	// public void addTermCtr(Term term, CoopTermRegistration ctr) {
	// if(!checkArg(ctr)) {
	// throw new NullArgumentException();
	// }
	//
	// Set<CoopTermRegistration> ctrs = term.getCoopTermRegistration();
	// try {
	// ctrs.add(ctr);
	// }
	// catch(Exception e) {
	// ctrs = new HashSet<>();
	// ctrs.add(ctr);
	// }
	// term.setCoopTermRegistration(ctrs);
	//
	//// return termRepository.save(term);
	// }
	/**
	 * Updates term with the new attributes.
	 * @param term the Term to be updated.
	 * @param termName the new name.
	 * @param studentEvalFormDeadline the new Deadline for Student's evaluation form.
	 * @param coopEvalFormDeadline the new Deadline for Cooperator's evaluation form.
	 * return a new Term object has just been updated and persited.
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to antoher class. Consider use separate methods instead of this one.
	 * @see TermService
	 * */
	@Transactional
	public Term updateTerm(Term term, String termName, Date studentEvalFormDeadline, Date coopEvalFormDeadline) {
		if (checkArg(termName)) {
			term.setTermName(termName);
		}
		if (checkArg(studentEvalFormDeadline)) {
			term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		}
		if (checkArg(coopEvalFormDeadline)) {
			term.setCoopEvalFormDeadline(coopEvalFormDeadline);
		}

		return termRepository.save(term);
	}
	/**
	 * Gets Term instance based on termID
	 *
	 * @param termID unique id of the term
	 * @return Term instance
	 * @author Saleh Bakhit.
	 * @deprecated this method was moved to another class due to refactoring.
	 * @see TermService
	 */
	@Transactional
	public Term getTerm(String termID) {
		return termRepository.findByTermID(termID);
	}

	// @Transactional
	// public Set<Term> getAllTerms() {
	// return toSet(termRepository.findAll());
	// }
	// ---Term---

	/*
	* HELPER METHODS
	* */
	/**
	 * Takes an Iterable object and return a Set.
	 * @param iterable the iterable of an object.
	 * @return a Set as a result of iteration.
	 * */
	private <T> Set<T> toSet(Iterable<T> iterable) {
		Set<T> res = new HashSet<T>();
		for (T t : iterable) {
			res.add(t);
		}
		return res;
	}
	/**
	 * Checks if the argument is meaningful or not.
	 * The argument is not meaningful if it is null, or if
	 * it is an empty String.
	 * @param arg the argument to be checked.
	 * @return true if the argument is meaningful, false otherwise.
	 * */
	private <T> boolean checkArg(T arg) {
		boolean legal = true;
		if (arg == null) {
			legal = false;
		} else if (arg instanceof String && ((String) arg).trim().length() == 0) {
			legal = false;
		}

		return legal;
	}

}