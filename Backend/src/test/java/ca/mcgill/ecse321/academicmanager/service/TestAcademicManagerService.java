package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

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
//import ca.mcgill.ecse321.academicmanager.model.FormType;
import ca.mcgill.ecse321.academicmanager.model.Meeting;
import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.model.Term;
//import ca.mcgill.ecse321.academicmanager.model.TermStatus;

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
		studentRepository.deleteAll();
		cooperatorRepository.deleteAll();
	}
	
	@Test
	public void testCreateCourse() {	
		List<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		Cooperator cooperator = allCooperators.get(0);
		
		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String courseName = "Introduction to Software Engineering";
		String term = "Winter2019";

		try {
			service.createCourse(courseID, courseName, term, cooperator);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Course> allCourses = service.getAllCourses();

		assertEquals(1, allCourses.size());
		assertEquals(courseID, allCourses.get(0).getCourseID());
		assertEquals(term, allCourses.get(0).getTerm());
	}
	
	@Test
	public void testCreateStudent() {
		List<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		Cooperator cooperator = allCooperators.get(0);
		
		assertEquals(0, service.getAllStudents().size());

		String firstname = "Saleh";
		String lastname = "Bakhit";
		String studentID = "260632353";

		try {
			service.createStudent(studentID, firstname, lastname, cooperator);
		} catch (IllegalArgumentException e) {
			// Check that no error occurred
			fail();
		}

		List<Student> allStudents = service.getAllStudents();

		assertEquals(1, allStudents.size());
		assertEquals(studentID, allStudents.get(0).getStudentID());
	}
	
	@Test
	public void testCreateStudentNull() {
		List<Cooperator> allCooperators = service.getAllCooperators();
		assertEquals(1, allCooperators.size());
		Cooperator cooperator = allCooperators.get(0);
		
		assertEquals(0, service.getAllStudents().size());

		String firstname = null;
		String lasttname = null;
		String studentID = null;
		String error = null;

		try {
			service.createStudent(studentID, firstname, lasttname, cooperator);
		} catch (IllegalArgumentException e) {
			error = e.getMessage();
		}

		// check error
		assertEquals("one or more argument(s) is/are null/empty", error);

		// check no change in memory
		assertEquals(0, service.getAllStudents().size());
	}
	
}
