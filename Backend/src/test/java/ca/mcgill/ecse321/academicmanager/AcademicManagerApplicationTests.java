package ca.mcgill.ecse321.academicmanager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.academicmanager.dao.*;

import ca.mcgill.ecse321.academicmanager.service.*;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import ca.mcgill.ecse321.academicmanager.controller.AcademicManagerRestController;
import ca.mcgill.ecse321.academicmanager.model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest

public class AcademicManagerApplicationTests {

	/********** STUDENT TEST *********/
	@Mock
	private CooperatorRepository cooperatorDao;
	@Mock
	private StudentRepository studentDao;
	@Mock
	private TermRepository termDao;
	@Mock
	private FormRepository formDao;
	@Mock
	private CoopTermRegistrationRepository coopTermRegistrationDao;
	@Mock
	private CourseRepository courseDao;

	@InjectMocks
	private AcademicManagerService service;

	private static final Integer COOPERATOR_KEY = 1;
	private static final Integer NONEXISTING_COOPERATOR_KEY = 2;

	private static final String STUDENT_KEY = "226333211";
	private static final String PROBLEMATICSTUDENT_KEY = "12321442";
	private static final String NONEXISTING_STUDENT_KEY = "NotaID";

	private static final String COOPTERMREGISTRATION_KEY = "Internship001";
	private static final String NONEXISTING_COOPREGISTRATION_KEY = "NotInternship";

	private static final String STUDENTFORM_KEY = "123";
	private static final String STUDENTFORMNAME_KEY = "STUDENTFORM";
	private static final String STUDENTFORMLINK_KEY = "www.pdflink.com";
	private static final FormType STUDENTFORMTYPE_KEY = FormType.STUDENTEVALUATION;

	private static final String EMPLOYERFORM_KEY = "1234";
	private static final String EMPLOYERFORMNAME_KEY = "EMPLOYERFORM";
	private static final String EMPLOYERFORMLINK_KEY = "www.pdflink2.com";
	private static final FormType EMPLOYERFORMTYPE_KEY = FormType.COOPEVALUATION;

	private static final String COURSE_KEY1 = "Course001";
	private static final String COURSE_TERM1 = "Winter2019";
	private static final String COURSE_KEY2 = "Course002";
	private static final String COURSE_TERM2 = "Fall2019";
	private static final String NONEXISTING_COURSE_KEY = "NotACourse";
	private static final String NONEXISTING_COURSE_TERM = "NotATerm";

	@Before
	public void setMockOutput() {
		when(studentDao.findByStudentID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(STUDENT_KEY)) {
				Student student = new Student();
				student.setStudentID(STUDENT_KEY);
				return student;
			} else {
				return null;
			}
		});

		when(studentDao.findByIsProblematic(anyBoolean())).thenAnswer((InvocationOnMock invocation) -> {
			if ((boolean) invocation.getArgument(0)) {
				List<Student> problematicStudents = new ArrayList<Student>();
				Student problematicStudent = new Student();
				problematicStudent.setStudentID(PROBLEMATICSTUDENT_KEY);
				problematicStudents.add(problematicStudent);
				return problematicStudents;
			} else {
				return null;
			}
		});

		when(studentDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			{
				Set<Student> students = new HashSet<Student>();
				Student student1 = new Student();
				Student student2 = new Student();
				student1.setStudentID(STUDENT_KEY);
				student2.setStudentID(PROBLEMATICSTUDENT_KEY);
				students.add(student1);
				students.add(student2);
				return students;
			}
		});

		when(cooperatorDao.findByid(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(COOPERATOR_KEY)) {
				Cooperator cooperator = new Cooperator();
				cooperator.setId(COOPERATOR_KEY);
				return cooperator;
			} else {
				return null;
			}
		});

		when(coopTermRegistrationDao.findByRegistrationID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(COOPTERMREGISTRATION_KEY)) {
				CoopTermRegistration coopTermRegistration = new CoopTermRegistration();
				coopTermRegistration.setRegistrationID(COOPTERMREGISTRATION_KEY);
				return coopTermRegistration;
			} else {
				return null;
			}
		});

		when(coopTermRegistrationDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			CoopTermRegistration ctr = new CoopTermRegistration();
			Form employerform = new Form();
			Form studentform = new Form();

			employerform.setFormID(EMPLOYERFORM_KEY);
			employerform.setName(EMPLOYERFORMNAME_KEY);
			employerform.setPdfLink(EMPLOYERFORMLINK_KEY);
			employerform.setFormType(EMPLOYERFORMTYPE_KEY);

			studentform.setFormID(STUDENTFORM_KEY);
			studentform.setName(STUDENTFORMNAME_KEY);
			studentform.setPdfLink(STUDENTFORMLINK_KEY);
			studentform.setFormType(STUDENTFORMTYPE_KEY);

			ctr.addForm(studentform);
			ctr.addForm(employerform);

			Set<CoopTermRegistration> allCTRs = new HashSet<CoopTermRegistration>();
			allCTRs.add(ctr);

			return allCTRs;
		});
		
		when(courseDao.findByCourseIDAndTerm(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(COURSE_KEY1) && invocation.getArgument(1).equals(COURSE_TERM1)) {
				Course course = new Course();
				course.setCourseID(COURSE_KEY1);
				course.setTerm(COURSE_TERM1);
				return course;
			} else {
				return null;
			}
		});

		when(courseDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			Course course1 = new Course();
			course1.setCourseID(COURSE_KEY1);
			course1.setTerm(COURSE_TERM1);

			Course course2 = new Course();
			course2.setCourseID(COURSE_KEY2);
			course2.setTerm(COURSE_TERM2);

			Set<Course> courses = new HashSet<Course>();

			courses.add(course1);
			courses.add(course2);

			return courses;
		});
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllProblematicStudents() {
		List<Student> problematicStudents = service.getAllProblematicStudents();
		assertEquals(problematicStudents.size(), 1);
		assertEquals(problematicStudents.iterator().next().getStudentID(), PROBLEMATICSTUDENT_KEY);
	}

	@Test
	public void testGetAllStudents() {
		Set<Student> students = service.getAllStudents();
		assertEquals(students.size(), 2);
	}

	@Test
	public void testGetStudentByIdExistingId() {
		Student student = service.getStudent(STUDENT_KEY);
		assertEquals(student.getStudentID(), STUDENT_KEY);
	}

	@Test
	public void testGetStudentByIdNonexistingId() {
		Student student = service.getStudent(NONEXISTING_STUDENT_KEY);
		assertNull(student);
	}

	@Test
	public void testGetCooperatorByIdExistingId() {
		Cooperator cooperator = service.getCooperator(COOPERATOR_KEY);
		assertEquals(cooperator.getId(), COOPERATOR_KEY);
	}

	@Test
	public void testGetCooperatorByIdNonexistingId() {
		Cooperator cooperator = service.getCooperator(NONEXISTING_COOPERATOR_KEY);
		assertNull(cooperator);
	}

	@Test
	public void testGetAllStudentForms() {
		Set<Form> forms = service.getAllStudentEvalForms();
		assertEquals(forms.size(), 1);
	}

	@Test
	public void testGetAllEmployerForms() {
		Set<Form> forms = service.getAllEmployerEvalForms();
		assertEquals(forms.size(), 1);
	}

	@Test
	public void testGetCoopTermRegistrationByExistingID() {
		assertEquals(COOPTERMREGISTRATION_KEY,
				service.getCoopTermRegistration(COOPTERMREGISTRATION_KEY).getRegistrationID());
	}

	@Test(expected = NullPointerException.class)
	public void testGetCoopTermRegistrationByInvalidID() {
		assertNull(service.getCoopTermRegistration(NONEXISTING_COOPREGISTRATION_KEY).getRegistrationID());
		assertNull(service.getCoopTermRegistration(null).getRegistrationID());
	}
	
	@Test
	public void testGetAllCoopTermRegistration() {
		Set<CoopTermRegistration> CTRs = new HashSet<CoopTermRegistration>();
		CTRs = service.getAllCoopTermRegistration();
		assertEquals(CTRs.size(), 1);
	}

	@Test
	public void testGetCourseByExistingIDAndTerm() {
		assertEquals(COURSE_KEY1, service.getCourse(COURSE_KEY1, COURSE_TERM1).getCourseID());
		assertEquals(COURSE_TERM1, service.getCourse(COURSE_KEY1, COURSE_TERM1).getTerm());
	}

	@Test(expected = NullPointerException.class)
	public void testGetCourseByNonexistingIDAndTerm() {
		assertNull(service.getCourse(NONEXISTING_COURSE_KEY, NONEXISTING_COURSE_TERM).getCourseID());
		assertNull(service.getCourse(NONEXISTING_COURSE_KEY, NONEXISTING_COURSE_TERM).getTerm());
		assertNull(service.getCourse(null, null).getCourseID());
		assertNull(service.getCourse(null, null).getTerm());
	}
	
	@Test
	public void testGetAllCourses() {
		Set<Course> courses = new HashSet<Course>();
		courses = service.getAllCourses();
		assertEquals(courses.size(), 2);
	}
}
