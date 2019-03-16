package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.academicmanager.dao.*;
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
		coopTermRegistrationRepository.deleteAll();
		termRepository.deleteAll();
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
		service.createStudent(studentID, firstname, lastname, cooperator);
		
		Student student1Test = service.getStudent(studentID);
		assertEquals(cooperator, student1Test.getCooperator());
		
		studentID = "260632350";
		firstname = "testFirst";
		lastname = "testLast";
		service.createStudent(studentID, firstname, lastname, cooperator);
		
		Student student2Test = service.getStudent(studentID);
		assertEquals(cooperator, student2Test.getCooperator());
		
		
		Cooperator cooperatorTest = service.getCooperator(1);
		Set<Student> cooperatorStudentsDb = cooperatorTest.getStudent();
		assertTrue(cooperatorStudentsDb.contains(student1Test));
		assertTrue(cooperatorStudentsDb.contains(student2Test));
		
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
		service.createCourse(courseID, term, courseName, null, cooperator);
		
		Course course1Test = service.getCourse(courseID, term);
		assertEquals(cooperator, course1Test.getCooperator());
		
		courseID = "ECSE425";
		term = "Winter2019";
		courseName = "Computer Architecture";
		service.createCourse(courseID, term, courseName, null, cooperator);
		
		Course course2Test = service.getCourse(courseID, term);
		assertEquals(cooperator, course1Test.getCooperator());
		
		Cooperator cooperatorTest = service.getCooperator(1);
		Set<Course> cooperatorCoursesDb = cooperatorTest.getCourse();
		assertTrue(cooperatorCoursesDb.contains(course1Test));
		assertTrue(cooperatorCoursesDb.contains(course2Test));
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
		
		Term termTest = service.getTerm("Winter2019");
		Student studentTest = service.getStudent("142142");
		CoopTermRegistration ctrTest = service.getCoopTermRegistration(registrationID);
		assertEquals(termTest, ctrTest.getTerm());
		assertEquals(studentTest, ctrTest.getStudent());
		
		assertTrue(studentTest.getCoopTermRegistration().contains(ctrTest));
		assertTrue(termTest.getCoopTermRegistration().contains(ctrTest));
		
		String formID = "0";
		String name = "testForm";
		String pdflink = "798fakhj";
		service.createForm(formID, name, pdflink, FormType.COOPEVALUATION, ctr);
		
		Form formTest = service.getForm(formID);
		ctrTest = service.getCoopTermRegistration(registrationID);
		assertEquals(ctrTest, formTest.getCoopTermRegistration());
		assertTrue(ctrTest.getForm().contains(formTest));
		
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
		Student student = service.createStudent(studentID, firstname, lastname, cooperator);
		
		java.util.Date utilDate = new java.util.Date();
		
		String meetingID = "0";
		String location = "ENGTR 3090";
		Date date = new Date(utilDate.getTime());
		Time startTime = new Time(503000);
		Time endTime = new Time(503010);
		Meeting meeting = service.createMeeting(meetingID, location, null, date, startTime, endTime);
		service.addMeetingStudent(meeting, student);
		
		Meeting meetingTest = service.getMeeting(meetingID);
		Student studentTest = service.getStudent(studentID);
		Set<Meeting> studentMeetings= studentTest.getMeeting();
		Set<Student> meetingStudents = meetingTest.getStudent();
		
		assertTrue(studentMeetings.contains(meetingTest));
		assertTrue(meetingStudents.contains(studentTest));
		
	}*/
	
	@Test
	public void testAddCtrsToTerm() {
		Student s1 = service.createStudent("1", "s1First", "s1Last", cooperator);
		
		Term t1 = service.createTerm("1", "1-Term", null, null);
		Term t2 = service.createTerm("2", "2-Term", null, null);
		Term t3 = service.createTerm("3", "3-Term", null, null);
		
		service.createCoopTermRegistration("1", "1", TermStatus.ONGOING, null, s1, t1);
		service.createCoopTermRegistration("2", "2", TermStatus.ONGOING, null, s1, t2);
		service.createCoopTermRegistration("3", "3", TermStatus.ONGOING, null, s1, t3);
		
		Student s1Test = service.getStudent("1");
		Term t1Test = service.getTerm("1");
		Term t2Test = service.getTerm("2");
		Term t3Test = service.getTerm("3");
		CoopTermRegistration ctr1Test = service.getCoopTermRegistration("1");
		CoopTermRegistration ctr2Test = service.getCoopTermRegistration("2");
		CoopTermRegistration ctr3Test = service.getCoopTermRegistration("3");
		Set<CoopTermRegistration> studentCtrs = s1Test.getCoopTermRegistration();
		
		assertEquals(3, studentCtrs.size());
		
		assertTrue(studentCtrs.contains(ctr1Test));
		assertEquals(s1Test, ctr1Test.getStudent());
		assertTrue(t1Test.getCoopTermRegistration().contains(ctr1Test));
		assertEquals(t1Test, ctr1Test.getTerm());

		assertTrue(studentCtrs.contains(ctr2Test));
		assertEquals(s1Test, ctr2Test.getStudent());
		assertTrue(t2Test.getCoopTermRegistration().contains(ctr2Test));
		assertEquals(t2Test, ctr2Test.getTerm());

		assertTrue(studentCtrs.contains(ctr3Test));
		assertEquals(s1Test, ctr3Test.getStudent());
		assertTrue(t3Test.getCoopTermRegistration().contains(ctr3Test));
		assertEquals(t3Test, ctr3Test.getTerm());
	}
	
}
