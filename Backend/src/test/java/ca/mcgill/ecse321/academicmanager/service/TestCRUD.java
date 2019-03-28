package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ca.mcgill.ecse321.academicmanager.dao.*;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCRUD {

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
		cooperatorService.create(1);
		Set<Cooperator> allCooperators = cooperatorService.getAll();
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
		assertNull(cooperatorService.create(null));
	}

	@Test
	public void testCreateStudent() {
		String studentID = "260632353";
		String firstname = "Saleh";
		String lastname = "Bakhit";
		studentService.create(studentID, firstname, lastname, cooperator);

		Student student1Test = studentService.get(studentID);
		assertEquals(cooperator, student1Test.getCooperator());

		studentID = "260632350";
		firstname = "testFirst";
		lastname = "testLast";
		studentService.create(studentID, firstname, lastname, cooperator);

		Student student2Test = studentService.get(studentID);
		assertEquals(cooperator, student2Test.getCooperator());

		Cooperator cooperatorTest = cooperatorService.get(1);
		Set<Student> cooperatorStudentsDb = cooperatorTest.getStudent();
		assertTrue(cooperatorStudentsDb.contains(student1Test));
		assertTrue(cooperatorStudentsDb.contains(student2Test));

	}

	@Test(expected = NullArgumentException.class)
	public void testCreateStudentNull() {
		Student student1 = studentService.create(null, null, null, null);
		assertNull(student1);
	}

	@Test
	public void testCreateCourse() {
		String courseID = "ECSE321";
		String term = "Winter2019";
		String courseName = "Software Engineering";
		courseService.create(courseID, term, courseName, null, cooperator);

		Course course1Test = courseService.get(courseID, term);
		assertEquals(cooperator, course1Test.getCooperator());

		courseID = "ECSE425";
		term = "Winter2019";
		courseName = "Computer Architecture";
		courseService.create(courseID, term, courseName, null, cooperator);

		Course course2Test = courseService.get(courseID, term);
		assertEquals(cooperator, course1Test.getCooperator());

		Cooperator cooperatorTest = cooperatorService.get(1);
		Set<Course> cooperatorCoursesDb = cooperatorTest.getCourse();
		assertTrue(cooperatorCoursesDb.contains(course1Test));
		assertTrue(cooperatorCoursesDb.contains(course2Test));

		courseService.delete(course2Test);
		cooperatorTest = cooperatorService.get(1);
		assertTrue(!cooperatorTest.getCourse().contains(course2Test));
	}

	@Test
	public void testCreatecoopTermRegistration() {
		Student student = studentService.create("142142", "saleh", "bakhit", cooperator);
		Term term = termService.create("Winter2019", "Winter 2019", null, null);

		String registrationID = "1214214";
		String jobID = "1512521";
		TermStatus status = TermStatus.FAILED;
		Grade grade = Grade.NotGraded;
		CoopTermRegistration ctr = coopTermRegistrationService.create(registrationID, jobID, status, grade, student,
				term);

		Term termTest = termService.get("Winter2019");
		Student studentTest = studentService.get("142142");
		CoopTermRegistration ctrTest = coopTermRegistrationService.get(registrationID);
		assertEquals(termTest, ctrTest.getTerm());
		assertEquals(studentTest, ctrTest.getStudent());

		assertTrue(studentTest.getCoopTermRegistration().contains(ctrTest));
		assertTrue(termTest.getCoopTermRegistration().contains(ctrTest));

		String formID = "0";
		String name = "testForm";
		String pdflink = "798fakhj";
		formService.create(formID, name, pdflink, FormType.COOPEVALUATION, ctr);

		Form formTest = formService.get(formID);
		ctrTest = coopTermRegistrationService.get(registrationID);
		assertEquals(ctrTest, formTest.getCoopTermRegistration());
		assertTrue(ctrTest.getForm().contains(formTest));
		assertEquals(ctrTest.getForm().iterator().next().getPdfLink(), pdflink);

		formService.delete(formTest);
		ctrTest = coopTermRegistrationService.get(registrationID);
		assertTrue(!ctrTest.getForm().contains(formTest));
	}

	@Test(expected = NullArgumentException.class)
	public void testCreateCTRNull() {
		assertNull(coopTermRegistrationService.create(null, null, null, null, null, null));
	}

	/*
	 * @Test public void testSettingMeeting() { assertEquals(0,
	 * service.getAllStudents().size()); assertEquals(0,
	 * service.getAllMeetings().size());
	 * 
	 * String studentID = "260632355"; String firstname = "saleh"; String lastname =
	 * "bakhit"; Student student = service.createStudent(studentID, firstname,
	 * lastname, cooperator);
	 * 
	 * java.util.Date utilDate = new java.util.Date();
	 * 
	 * String meetingID = "0"; String location = "ENGTR 3090"; Date date = new
	 * Date(utilDate.getTime()); Time startTime = new Time(503000); Time endTime =
	 * new Time(503010); Meeting meeting = service.createMeeting(meetingID,
	 * location, null, date, startTime, endTime); service.addMeetingStudent(meeting,
	 * student);
	 * 
	 * Meeting meetingTest = service.getMeeting(meetingID); Student studentTest =
	 * service.getStudent(studentID); Set<Meeting> studentMeetings=
	 * studentTest.getMeeting(); Set<Student> meetingStudents =
	 * meetingTest.getStudent();
	 * 
	 * assertTrue(studentMeetings.contains(meetingTest));
	 * assertTrue(meetingStudents.contains(studentTest));
	 * 
	 * }
	 */

	@Test
	public void testAddCtrsToTerm() {
		Student s1 = studentService.create("1", "s1First", "s1Last", cooperator);

		Term t1 = termService.create("1", "1-Term", null, null);
		Term t2 = termService.create("2", "2-Term", null, null);
		Term t3 = termService.create("3", "3-Term", null, null);

		coopTermRegistrationService.create("1", "1", TermStatus.ONGOING, null, s1, t1);
		coopTermRegistrationService.create("2", "2", TermStatus.ONGOING, null, s1, t2);
		coopTermRegistrationService.create("3", "3", TermStatus.ONGOING, null, s1, t3);

		Student s1Test = studentService.get("1");
		Term t1Test = termService.get("1");
		Term t2Test = termService.get("2");
		Term t3Test = termService.get("3");
		CoopTermRegistration ctr1Test = coopTermRegistrationService.get("1");
		CoopTermRegistration ctr2Test = coopTermRegistrationService.get("2");
		CoopTermRegistration ctr3Test = coopTermRegistrationService.get("3");
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
