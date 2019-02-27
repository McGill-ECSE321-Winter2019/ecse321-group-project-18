package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.academicmanager.dao.*;

import ca.mcgill.ecse321.academicmanager.exceptions.*;

import ca.mcgill.ecse321.academicmanager.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OtherServiceTests {
	
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
	public void testViewStudentGrade() {
		Student student = service.createStudent("142142", "saleh", "bakhit", cooperator);
		Term term = service.createTerm("Winter2019", "Winter 2019", null, null);
		
		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		Grade grade = Grade.NotGraded;
		
		CoopTermRegistration ctr = null;
		try {
			ctr = service.createCoopTermRegistration(registrationID, jobID, status, grade, student, term);
		} catch(Exception e) {
			System.out.println(e.getMessage());
			fail();
		}
		assertTrue(student.getCoopTermRegistration().getRegistrationID() == ctr.getRegistrationID());
		
		assertEquals(1, term.getCoopTermRegistration().size());
		assertTrue(term.getCoopTermRegistration().iterator().next().getRegistrationID() == ctr.getRegistrationID());
		
		assertEquals(grade, service.getStudentGrade(ctr));
	}
	
	
	@Test
	public void testCreateCourses() {
		assertEquals(0, service.getAllCourses().size());

		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Introduction to Software Engineering";
		Integer courseRank = null;

		try {
			service.createCourse(courseID, term, courseName, courseRank, cooperator);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}
		
		courseID = "ECSE425";
		term = "Winter2019";
		courseName = "Computer Architecture";
		courseRank = null;
		try {
			service.createCourse(courseID, term, courseName, courseRank, cooperator);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}

		Set<Course> allCourses = service.getAllCourses();
		Set<Course> cooperatorCourses = service.getCooperatorCourses(cooperator);
		assertEquals(2, allCourses.size());
		assertEquals(2, cooperatorCourses.size());
		
		Iterator<Course> itr1 = allCourses.iterator();
		Iterator<Course> itr2 = cooperatorCourses.iterator();
		while(itr1.hasNext()) {
			assertEquals(itr1.next(), itr2.next());
		}
		
	}
	
	@Test
	public void testCreateStudents() {
		assertEquals(0, service.getAllStudents().size());

		String studentID = "260632355";
		String firstname = "saleh";
		String lastname = "bakhit";

		try {
			service.createStudent(studentID, firstname, lastname, cooperator);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}
		
		studentID = "260632356";
		firstname = "testFirst";
		lastname = "testLast";
		try {
			service.createStudent(studentID, firstname, lastname, cooperator);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}

		Set<Student> allStudents = service.getAllStudents();
		Set<Student> cooperatorStudents = service.getCooperatorStudents(cooperator);
		assertEquals(2, allStudents.size());
		assertEquals(2, cooperatorStudents.size());
		
		Iterator<Student> itr1 = allStudents.iterator();
		Iterator<Student> itr2 = cooperatorStudents.iterator();
		while(itr1.hasNext()) {
			assertEquals(itr1.next(), itr2.next());
		}
		
	}
	
	
	@Test
	public void testSettingMeeting() {
		assertEquals(0, service.getAllStudents().size());
		assertEquals(0, service.getAllMeetings().size());
		
		String studentID = "260632355";
		String firstname = "saleh";
		String lastname = "bakhit";
		
		Student student = null;
		try {
			student = service.createStudent(studentID, firstname, lastname, cooperator);
		} catch (Exception e) {
			// Check that no error occurred
			fail();
		}
		
		java.util.Date utilDate = new java.util.Date();
		
		String meetingID = "0";
		String location = "ENGTR 3090";
		Date date = new Date(utilDate.getTime());
		Time startTime = new Time(503000);
		Time endTime = new Time(503010);
		
		Meeting meeting = null;
		try {
			meeting = service.createMeeting(meetingID, location, null, date, startTime, endTime);
		}
		catch(Exception e) {
			// Check that no error occurred
			fail();	
		}
		
		meeting = service.addMeetingStudent(meeting, student);
		
		String MeetingId1 = student.getMeeting().iterator().next().getMeetingID();
		String MeetingId2 = meeting.getStudent().iterator().next().getMeeting().iterator().next().getMeetingID();
		assertEquals(MeetingId1, MeetingId2);
	}
//	
//	@Test
//	public void testAddFromToCtr() {
//		
//	}
//	
//	@Test
//	public void testaddCtrsToTerm() {
//		
//	}
	
}
