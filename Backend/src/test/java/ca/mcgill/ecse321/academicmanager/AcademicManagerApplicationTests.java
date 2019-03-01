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
	private AcademicManagerService serviceMock;

	@InjectMocks
	private AcademicManagerRestController controller;
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
		
		when(cooperatorDao.findByid(anyInt())).thenAnswer((InvocationOnMock invocation) -> {
			if (invocation.getArgument(0).equals(COOPERATOR_KEY)) {
				Cooperator cooperator = new Cooperator();
				cooperator.setId(COOPERATOR_KEY);
				return cooperator;
			} else {
				return null;
			}
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

}
