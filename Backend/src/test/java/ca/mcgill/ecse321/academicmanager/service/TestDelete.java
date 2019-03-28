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
import ca.mcgill.ecse321.academicmanager.model.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDelete {

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
	public void inventory() {
		cooperatorService.create(1);
		cooperator = cooperatorService.get(1);

		courseService.create("ECSE321", "Winter2019", "Introduction to Software Engineering", null, cooperator);
		courseService.create("ECSE425", "Winter2019", "Computer Architecture", null, cooperator);

		studentService.create("260632353", "Saleh", "Bakhit", cooperator);
		studentService.create("260632350", "First", "Last", cooperator);

		termService.create("W19", "Winter2019", null, null);
		termService.create("W18", "Winter2018", null, null);

		coopTermRegistrationService.create("0", "123", TermStatus.ONGOING, null, studentService.get("260632353"),
				termService.get("W19"));
		coopTermRegistrationService.create("1", "123", TermStatus.ONGOING, null, studentService.get("260632350"),
				termService.get("W19"));
		coopTermRegistrationService.create("2", "456", TermStatus.ONGOING, null, studentService.get("260632353"),
				termService.get("W18"));

		formService.create("0", "test form 0", "test0.com", FormType.COOPEVALUATION,
				coopTermRegistrationService.get("0"));
		formService.create("1", "test form 1", "test1.com", FormType.STUDENTEVALUATION,
				coopTermRegistrationService.get("0"));
		formService.create("2", "test form 2", "test2.com", FormType.STUDENTEVALUATION,
				coopTermRegistrationService.get("1"));
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

	@Test
	public void testDeleteCourse() {
		Course course = courseService.get("ECSE321", "Winter2019");
		courseService.delete(course);

		assertNull(courseService.get("ECSE321", "Winter2019"));

		assertTrue(!cooperatorService.get(course.getCooperator().getId()).getCourse().contains(course));
	}

	@Test
	public void testDeleteStudent() {
		Student student = studentService.get("260632353");
		studentService.delete(student);

		assertNull(studentService.get("260632353"));

		assertTrue(!cooperatorService.get(student.getCooperator().getId()).getStudent().contains(student));

		Set<CoopTermRegistration> studentInternships = student.getCoopTermRegistration();
		for (CoopTermRegistration internship : studentInternships) {
			assertNull(coopTermRegistrationService.get(internship.getRegistrationID()));
		}

		Set<Meeting> studentMeetings = student.getMeeting();
		for (Meeting meeting : studentMeetings) {
			assertTrue(!meetingService.getStudents(meeting).contains(student));
		}
	}

	@Test
	public void testDeleteInternship() {
		CoopTermRegistration ctr = coopTermRegistrationService.get("0");
		coopTermRegistrationService.delete(ctr);

		assertNull(coopTermRegistrationService.get("0"));

		Student student = studentService.get(ctr.getStudent().getStudentID());
		assertTrue(!student.getCoopTermRegistration().contains(ctr));

		Term term = termService.get(ctr.getTerm().getTermID());
		assertTrue(!term.getCoopTermRegistration().contains(ctr));

		Set<Form> internshipForms = ctr.getForm();
		for (Form form : internshipForms) {
			assertNull(formService.get(form.getFormID()));
		}
	}

	@Test
	public void testDeleteTerm() {
		Term term = termService.get("W19");
		termService.delete(term);

		assertNull(termService.get("W19"));

		Set<CoopTermRegistration> termInternships = term.getCoopTermRegistration();
		for (CoopTermRegistration internship : termInternships) {
			assertNull(coopTermRegistrationService.get(internship.getRegistrationID()));
		}
	}

	@Test
	public void testDeleteForm() {
		Form form = formService.get("0");
		formService.delete(form);

		assertNull(formService.get("0"));

		CoopTermRegistration ctr = coopTermRegistrationService.get(form.getCoopTermRegistration().getRegistrationID());
		assertTrue(!ctr.getForm().contains(form));
	}

	@Test
	public void testDeleteCooperator() {
		Cooperator cooperator = cooperatorService.get(1);
		cooperatorService.delete(cooperator);

		assertNull(cooperatorService.get(1));

		Set<Course> cooperatorCourses = cooperator.getCourse();
		for (Course course : cooperatorCourses) {
			assertNull(courseService.get(course.getCourseID(), course.getTerm()));
		}

		Set<Student> cooperatorStudents = cooperator.getStudent();
		for (Student student : cooperatorStudents) {
			assertNull(studentService.get(student.getStudentID()));
		}
	}

}
