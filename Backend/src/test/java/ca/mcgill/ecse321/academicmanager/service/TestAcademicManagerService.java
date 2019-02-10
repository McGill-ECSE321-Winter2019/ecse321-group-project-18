package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
/*
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
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
	
	@Before
	public void createCooperator() {
		service.createCooperator(1);
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
		Set<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		Cooperator cooperator = allCooperators.iterator().next();
		
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
	public void testCreateStudent() {
		Set<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		Cooperator cooperator = allCooperators.iterator().next();
		
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
	public void testCreateStudentNull() {
		Set<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		Cooperator cooperator = allCooperators.iterator().next();
		
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
	/**
	 * Test time constraint of the Meeting object.
	 * @author Bach Tran
	 * @since 2019-02-10
	 */
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
		Time startTime = Time.valueOf("18:00:00");
		Time endTime = Time.valueOf("16:00:00");
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
}