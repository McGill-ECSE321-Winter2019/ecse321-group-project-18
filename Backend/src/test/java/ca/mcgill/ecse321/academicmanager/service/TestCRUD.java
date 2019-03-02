package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
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
public class TestCRUD {
	
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
	
	@Test(expected = NullArgumentException.class)
	public void testCreateCooperatorNull() {
		assertNull(service.createCooperator(null));
	}
	
	@Test
	public void testCreateStudent() {
		String studentID = "260632353";
		String firstname = "Saleh";
		String lastname = "Bakhit";
		Student student1 = service.createStudent(studentID, firstname, lastname, cooperator);
		assertEquals(cooperator, student1.getCooperator());
		
		studentID = "260632350";
		firstname = "testFirst";
		lastname = "testLast";
		Student student2 = service.createStudent(studentID, firstname, lastname, cooperator);
		assertEquals(cooperator, student2.getCooperator());
		
//		Set<Student> cooperatorStudents = cooperator.getStudent();
//		assertTrue(cooperatorStudents.contains(student1));
//		assertTrue(cooperatorStudents.contains(student2));
		
	}
	
	@Test(expected = NullArgumentException.class)
	public void testCreateStudentNull() {
		Student student1 = service.createStudent(null, null, null, null);
		assertNull(student1);
	}
	
	@Test
	public void testCreateCourse() {
		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Software Engineering";
		Course course1 = service.createCourse(courseID, term, courseName, null, cooperator);
		assertEquals(cooperator, course1.getCooperator());
		
		courseID = "ECSE425";
		term = "Winter2019";
		courseName = "Computer Architecture";
		Course course2 = service.createCourse(courseID, term, courseName, null, cooperator);
		assertEquals(cooperator, course2.getCooperator());
		
//		Set<Course> cooperatorCourses = cooperator.getCourse();
//		assertTrue(cooperatorCourses.contains(course1));
//		assertTrue(cooperatorCourses.contains(course2));
	}
	
	@Test(expected = NullArgumentException.class)
	public void testCreateCourseNull() {
		service.createCourse(null, null, null, null, null);
	}
	
	@Test
	public void testCreatecoopTermRegistration() {
		Student student = service.createStudent("142142", "saleh", "bakhit", cooperator);
		Term term = service.createTerm("Winter2019", "Winter 2019", null, null);
		
		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		Grade grade = Grade.NotGraded;
		CoopTermRegistration ctr = service.createCoopTermRegistration(registrationID, jobID, status, grade, student, term);
		
		assertEquals(term, ctr.getTerm());
		assertEquals(student, ctr.getStudent());
		
		Set<CoopTermRegistration> studentCtrs = student.getCoopTermRegistration();
		//assertTrue(studentCtrs.contains(ctr));
		
		Set<CoopTermRegistration> termCtrs = term.getCoopTermRegistration();
		//assertTrue(termCtrs.contains(ctr));
		
		String formID = "0";
		String name = "testForm";
		String pdflink = "798fakhj";
		Form form = service.createForm(formID, name, pdflink, FormType.COOPEVALUATION, ctr);
		
		assertEquals(ctr, form.getCoopTermRegistration());
		assertTrue(ctr.getForm().contains(form));
		
	}
	
	@Test(expected = NullArgumentException.class)
	public void testCreateCTRNull() {
		assertNull(service.createCoopTermRegistration(null, null, null, null, null, null));
	}
	
	/*@Test
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
		
		Set<Meeting> studentMeetings= student.getMeeting();
		Set<Student> meetingStudents = meeting.getStudent();
		
		assertTrue(studentMeetings.contains(meeting));
		assertTrue(meetingStudents.contains(student));
		
	}*/
	
	@Test
	public void testAddCtrsToTerm() {
		Student s1 = service.createStudent("1", "s1First", "s1Last", cooperator);
		
		Term t1 = service.createTerm("1", "1-Term", null, null);
		Term t2 = service.createTerm("2", "2-Term", null, null);
		Term t3 = service.createTerm("3", "3-Term", null, null);
		
		CoopTermRegistration ctr1 = service.createCoopTermRegistration("1", "1", TermStatus.ONGOING, null, s1, t1);
		CoopTermRegistration ctr2 = service.createCoopTermRegistration("2", "2", TermStatus.ONGOING, null, s1, t2);
		CoopTermRegistration ctr3 = service.createCoopTermRegistration("3", "3", TermStatus.ONGOING, null, s1, t3);
		
		Set<CoopTermRegistration> studentCtrs = s1.getCoopTermRegistration();
		
		//assertEquals(3, studentCtrs.size());
		
		//assertTrue(studentCtrs.contains(ctr1));
		assertEquals(s1, ctr1.getStudent());
		//assertTrue(t1.getCoopTermRegistration().contains(ctr1));
		assertEquals(t1, ctr1.getTerm());

		//assertTrue(studentCtrs.contains(ctr2));
		assertEquals(s1, ctr2.getStudent());
		//assertTrue(t2.getCoopTermRegistration().contains(ctr2));
		assertEquals(t2, ctr2.getTerm());

		//assertTrue(studentCtrs.contains(ctr3));
		assertEquals(s1, ctr3.getStudent());
		//assertTrue(t3.getCoopTermRegistration().contains(ctr3));
		assertEquals(t3, ctr3.getTerm());
	}
	
}
