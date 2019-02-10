package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
/*
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
*/
import java.util.Set;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.academicmanager.dao.CooperatorRepository;
import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;
import ca.mcgill.ecse321.academicmanager.dao.TermRepository;

import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Course;
import ca.mcgill.ecse321.academicmanager.model.Form;
import ca.mcgill.ecse321.academicmanager.model.FormType;
import ca.mcgill.ecse321.academicmanager.model.Grade;
import ca.mcgill.ecse321.academicmanager.model.Meeting;
import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.model.Term;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAcademicManagerService {
	
	@Autowired
	private AcademicManagerService service;
	
	@Autowired
	private CooperatorRepository cooperatorRepository;
	@Autowired
	private CoopTermRegistrationRepository coopTermRegistrationRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private FormRepository formRepository;
	@Autowired
	private MeetingRepository meetingRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private TermRepository termRepository;
	
	Cooperator cooperator;
	
	@Before
	public void createCooperator() {
		service.createCooperator(1);
		Set<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		cooperator = allCooperators.iterator().next();
	}
	
	@After
	public void clearDatabase() {
		courseRepository.deleteAll();
		formRepository.deleteAll();
		termRepository.deleteAll();
		coopTermRegistrationRepository.deleteAll();
		meetingRepository.deleteAll();
		studentRepository.deleteAll();
		cooperatorRepository.deleteAll();
	}
	
	@Test
	public void testCreateCourse() {			
		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Introduction to Software Engineering";
		Integer courseRank = null;

		try {
			service.createCourse(courseID, term, courseName, courseRank, cooperator);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		Set<Course> allCourses = service.getAllCourses();

		assertEquals(1, allCourses.size());
		Course tmp = allCourses.iterator().next();
		assertEquals(courseID, tmp.getCourseID());
		assertEquals(term, tmp.getTerm());
	}
	
	@Test
	public void testUpdateCourseRank() {
		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Introduction to Software Engineering";
		Integer courseRank = 2;

		Course course = service.createCourse(courseID, term, courseName, courseRank, cooperator);
		assertEquals(course.getCourseRank(), courseRank);
		
		courseRank = 1;
		course = service.updateCourseRank(course, courseRank);
		
		assertEquals(courseRank, course.getCourseRank());
		assertEquals(1, service.getAllCourses().size());
	}
	
	@Test
	public void testCreateStudent() {	
		assertEquals(0, service.getAllStudents().size());

		String studentID = "260632353";
		String firstname = "Saleh";
		String lastname = "Bakhit";
		
		Grade grade = Grade.A;

		try {
			service.createStudent(studentID, firstname, lastname, grade, cooperator);
		} catch (IllegalArgumentException e) {
			fail();
		}

		Set<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		Student tmp = allStudents.iterator().next();
		assertEquals(studentID, tmp.getStudentID());
	}
	
	@Test
	public void viewAllStudents() {
		String studentID = "1";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		service.createStudent(studentID, firstname, lastname, grade, cooperator);
		
		studentID = "2";
		firstname = "2";
		lastname = "2";
		grade = Grade.A;
		
		service.createStudent(studentID, firstname, lastname, grade, cooperator);
		
		Set<Student> students = service.getAllStudents();
		
		assertTrue(students.size() == 2);
	}
	
	@Test
	public void testViewStudentGrade() {
		String studentID = "1";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		service.createStudent(studentID, firstname, lastname, grade, cooperator);
		Grade returnedGrade = service.getStudentGrade("1");
		
		assertEquals(returnedGrade, Grade.A);
	}
	
	@Test
	public void testCreateStudentNull() {
		assertEquals(0, service.getAllStudents().size());
		
		String studentID = null;
		String firstname = null;
		String lasttname = null;
		String error = null;
		
		Grade grade = null;

		try {
			service.createStudent(studentID, firstname, lasttname, grade, cooperator);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("one or more argument(s) is/are null/empty", error);

		// check no change in memory
		assertEquals(0, service.getAllStudents().size());
	}
	
	@Test
	public void testCreateForm() {
		String formID = "142142";
		String pdfLink = "1";
		String formName = "1";
		FormType formType = FormType.STUDENTEVALUATION;
		try{
			Form form = service.createForm(formID, formName, pdfLink, formType, null);
			assertEquals(1, formRepository.count());
			assertEquals("142142", form.getFormID());
		} catch (IllegalArgumentException e) {
			fail();
		}	
	}
	
	@Test
	public void testUpdateForm() {
		String formID = "142142";
		String pdfLink = "1";
		String formName = "1";
		FormType formType = FormType.STUDENTEVALUATION;
		
		Form form = service.createForm(formID, formName, pdfLink, formType, null);
		
		pdfLink = "2";
		formName = "2";
		formType = FormType.COOPEVALUATION;
		
		form = service.updateForm(form, null, formName, pdfLink, formType, null);
		
		assertEquals(1, formRepository.count());
		assertEquals("142142", form.getFormID());
		assertEquals("2", form.getPdfLink());
		assertEquals("2", form.getName());
		assertEquals(FormType.COOPEVALUATION, form.getFormType());
	}
	
	
	/*@Test
	public void testViewEmployerEvalForms() {
		
		String studentID = "142142";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		Student tmpStudent = service.createStudent(studentID, firstname, lastname, grade, cooperator);
		
		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		
		CoopTermRegistration tmpCTR = service.createCoopTermRegistration(registrationID, jobID, status, tmpStudent);
		
		String formID = "142142";
		String pdfLink = "1";
		String formName = "1";
		FormType formType = FormType.STUDENTEVALUATION;

		Form form = service.createForm(formID, formName, pdfLink, formType, tmpCTR);	
		assertEquals(1, formRepository.count());
		assertEquals("142142", form.getFormID());
		
		
		Set<Form> forms = tmpCTR.getForm();
		
		for(Form f : forms) {
			assertEquals(FormType.STUDENTEVALUATION, f.getFormType());
		}
	}*/
	
	@Test
	public void testViewProblematicStudents() {
		String studentID = "142142";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		Student student = service.createStudent(studentID, firstname, lastname, grade, cooperator);
		service.updateStudentProblematicStatus(student, true);
		
		List<Student> students = service.getAllProblematicStudents();
		
		for(Student s : students) {
			assertEquals(true, s.isIsProblematic());
		}
	}
	
	@Test
	public void testAdjudicateTermStatus() {
		
		String studentID = "142142";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		Student tmpStudent = service.createStudent(studentID, firstname, lastname, grade, cooperator);
		
		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		
		CoopTermRegistration tmpCTR = service.createCoopTermRegistration(registrationID, jobID, status, tmpStudent);
		assertEquals(TermStatus.FAILED, tmpCTR.getTermStatus());
		service.updateCoopTermRegistration(tmpCTR, TermStatus.FINISHED, null, tmpStudent, null);
		assertEquals(TermStatus.FINISHED, tmpCTR.getTermStatus());
	}
	
	/**
	 * Test time constraint of the Meeting object.
	 * @author Bach Tran
	 * @since 2019-02-10
	 */
	@Test
	public void testCreateMeetingStartTimeAfterEndTime()
	{		
		String error = null;
		assertEquals(0, service.getAllMeetings().size());
		// list of test instances
		String meetingID = "123456";
		String location = "sample location";
		String details = "sample details";
		Date date = Date.valueOf("2019-01-01");
		Time startTime = Time.valueOf("18:00:00");
		Time endTime = Time.valueOf("16:00:00");
		Set<Student> students = service.getAllStudents();
		try {
			service.createMeeting(meetingID, location, details, date, startTime, endTime, students);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("endTime need to happen after startTime.", error);
		
		// check no change in memory
		assertEquals(0, service.getAllStudents().size());
	}
	
	@Test
	public void testCreateMeeting()
	{		
		String error = null;
		assertEquals(0, service.getAllMeetings().size());
		// list of test instances
		String meetingID = "123456";
		String location = "sample location";
		String details = "sample details";
		Date date = Date.valueOf("2019-01-01");
		Time startTime = Time.valueOf("16:00:00");
		Time endTime = Time.valueOf("18:00:00");
		Set<Student> students = service.getAllStudents();
		try {
			Meeting meeting = service.createMeeting(meetingID, location, details, date, startTime, endTime, students);
			assertEquals(meeting.getMeetingID(), meetingID);
			assertEquals(meeting.getLocation(), location);
			assertEquals(meeting.getDetails(), details);
			assertEquals(meeting.getDate(), date);
			assertEquals(meeting.getStartTime(), startTime);
			assertEquals(meeting.getEndTime(), endTime);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
	}
	
	/**
	 * Test case: a null Meeting object
	 * @author Bach Tran
	 * @since 2019-02-10
	 */
	@Test
	public void testCreateMeetingNull()
	{
		String error = null;
		assertEquals(0, service.getAllMeetings().size());
		// list of test instances
		String meetingID = null;
		String location = null;
		String details = null;
		Date date = Date.valueOf("2019-01-01");
		Time startTime = Time.valueOf("16:00:00");
		Time endTime = Time.valueOf("18:00:00");
		Set<Student> students = service.getAllStudents();
		try {
			service.createMeeting(meetingID, location, details, date, startTime, endTime, students);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}
		
		// check error
		assertEquals("one or more argument(s) is/are null/empty", error);
		
		// check no change in memory
		assertEquals(0, service.getAllStudents().size());
	}
	
	@Test
	public void testUpdateMeeting() {
		String meetingID = "123456";
		String location = "sample location";
		String details = "sample details";
		Date date = Date.valueOf("2019-01-01");
		Time startTime = Time.valueOf("16:00:00");
		Time endTime = Time.valueOf("18:00:00");
		Set<Student> students = service.getAllStudents();
		
		Meeting meeting = service.createMeeting(meetingID, location, details, date, startTime, endTime, students);
		
		location = "new location";
		details = "new deatils";
		date = Date.valueOf("2019-01-02");
		startTime = Time.valueOf("15:00:00");
		endTime = Time.valueOf("17:00:00");
		
		meeting = service.updateMeeting(meeting, location, details, date, startTime, endTime, null);
		
		assertEquals(service.getAllMeetings().size(), 1);
		assertEquals(meeting.getLocation(), location);
		assertEquals(meeting.getDetails(), details);
		assertEquals(meeting.getDate(), date);
		assertEquals(meeting.getStartTime(), startTime);
		assertEquals(meeting.getEndTime(), endTime);
	}
	
	@Test
	public void testCreateCoopTermRegistration() {

		String studentID = "142142";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		Student tmpStudent = service.createStudent(studentID, firstname, lastname, grade, cooperator);
		
		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		
		try {
			CoopTermRegistration tmpCTR = service.createCoopTermRegistration(registrationID, jobID, status, tmpStudent);
			assertEquals(tmpCTR.getRegistrationID(), registrationID);
			assertEquals(tmpCTR.getJobID(), jobID);
			assertEquals(tmpCTR.getTermStatus(), status);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test
	public void testUpdateCoopTermRegistrationsStatus() {

		String studentID = "142142";
		String firstname = "1";
		String lastname = "1";
		Grade grade = Grade.A;
		
		Student tmpStudent = service.createStudent(studentID, firstname, lastname, grade, cooperator);
		
		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		
		CoopTermRegistration tmpCTR = service.createCoopTermRegistration(registrationID, jobID, status, tmpStudent);
		
		tmpCTR = service.updateCoopTermRegistration(tmpCTR, TermStatus.FINISHED, null, null, null);
		
		assertEquals(tmpCTR.getTermStatus(), TermStatus.FINISHED);
	}
	
	@Test
	public void testCreateTerm() {
		Term term;
		
		Set<CoopTermRegistration> ctrs = new HashSet<CoopTermRegistration>();
		
		String termID = "1";
		String termName = "Fall2019";
		Date studentEvalFormDeadline = Date.valueOf("2015-06-01");
		Date coopEvalFormDeadline = Date.valueOf("2015-06-01");
		try {
			term = service.createTerm(termID, termName, studentEvalFormDeadline, coopEvalFormDeadline, ctrs);
			assertEquals(term.getTermID(), termID);
			assertEquals(term.getTermName(), termName);
			assertEquals(term.getStudentEvalFormDeadline(), studentEvalFormDeadline);
			assertEquals(term.getCoopEvalFormDeadline(), coopEvalFormDeadline);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}
	
	@Test
	public void testUpdateTerm() {
		Term term;
		
		Set<CoopTermRegistration> ctrs = new HashSet<CoopTermRegistration>();
		
		String termID = "1";
		String termName = "Winter2019";
		Date studentEvalFormDeadline = Date.valueOf("2015-06-01");
		Date coopEvalFormDeadline = Date.valueOf("2015-06-01");
		
		term = service.createTerm(termID, termName, studentEvalFormDeadline, coopEvalFormDeadline, ctrs);
		
		studentEvalFormDeadline = Date.valueOf("2017-06-01");
		coopEvalFormDeadline = Date.valueOf("2017-06-01");
		
		term = service.updateTerm(term, studentEvalFormDeadline, coopEvalFormDeadline, null);
		
		assertEquals(term.getCoopEvalFormDeadline(), coopEvalFormDeadline);
		assertEquals(term.getStudentEvalFormDeadline(), studentEvalFormDeadline);
	}
}
