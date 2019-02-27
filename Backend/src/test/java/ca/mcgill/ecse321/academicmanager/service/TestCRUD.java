package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
/*
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
*/
import java.util.Set;
import java.util.HashSet;
import java.util.List;

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
		
		Set<Student> cooperatorStudents = cooperator.getStudent();
		assertTrue(cooperatorStudents.contains(student1));
		assertTrue(cooperatorStudents.contains(student2));
		
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
		
		Set<Course> cooperatorCourses = cooperator.getCourse();
		assertTrue(cooperatorCourses.contains(course1));
		assertTrue(cooperatorCourses.contains(course2));
		
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
		
		assertEquals(ctr, student.getCoopTermRegistration());
		
		Set<CoopTermRegistration> termCtrs = term.getCoopTermRegistration();
		assertTrue(termCtrs.contains(ctr));
		
	}
	
}
