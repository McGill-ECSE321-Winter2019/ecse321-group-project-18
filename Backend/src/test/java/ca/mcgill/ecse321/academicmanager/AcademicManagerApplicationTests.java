package ca.mcgill.ecse321.academicmanager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.academicmanager.dao.*;	

import ca.mcgill.ecse321.academicmanager.service.*;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import ca.mcgill.ecse321.academicmanager.controller.AcademicManagerRestController;
import ca.mcgill.ecse321.academicmanager.model.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest



public class AcademicManagerApplicationTests {

	/********** STUDENT TEST *********/
	@Mock
		private StudentRepository studentDao;
		@Mock
		private TermRepository termDao;
		@Mock
		private CoopTermRegistrationRepository coopTermRegistrationDao;
		@Mock
		private AcademicManagerService serviceMock;
		@InjectMocks
		private AcademicManagerRestController controller;
		@InjectMocks
		private AcademicManagerService service;

		private Student student;
		private static final String STUDENT_KEY = "226333211";
		private static final String NONEXISTING_STUDENT_KEY = "NotaID";
		
		@Before
		public void setStudentMockOutput() {
		  when(studentDao.findById(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
		    if(invocation.getArgument(0).equals(STUDENT_KEY)) {
		      Student student = new Student();
		      student.setStudentID(STUDENT_KEY);
		      return student;
		    } else {
		      return null;
		    }
		  });
		}
		
		@Before
		public void setupStudentMock() {
			student = mock(Student.class);
		}

		@Test
		public void testMockStudentCreation() {
			assertNotNull(student);
		}

		@Test
		public void testStudentQueryFound() {
			assertEquals(STUDENT_KEY, service.getStudent(STUDENT_KEY).getStudentID());
		}
		
	
	/**************** CoopTermRegistration TEST ***************/
	@Mock	
		private CoopTermRegistration coopTermRegistration;
		private static final String COOPTERMREGISTRATION_KEY = "Internship001";
		private static final String NONEXISTING_COOPREGISTRATION_KEY = "NotInternship";
		
		@Before
		public void setCoopTermRegistrationMockOutput() {
		when(coopTermRegistrationDao.findByRegistrationID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
		    if(invocation.getArgument(0).equals(COOPTERMREGISTRATION_KEY)) {
		      CoopTermRegistration coopTermRegistration = new CoopTermRegistration();
		      coopTermRegistration.setRegistrationID(COOPTERMREGISTRATION_KEY);
		      return coopTermRegistration;
		    } else {
		      return null;
		    }
		  });
		}
		
		@Before
		public void setupCoopTermRegistrationMock() {
			coopTermRegistration = mock(CoopTermRegistration.class);
		}

		@Test
		public void testMockCoopTermRegistrationCreation() {
			assertNotNull(coopTermRegistration);
		}

		@Test
		public void testCoopTermRegistrationQueryFound() {
			assertEquals(COOPTERMREGISTRATION_KEY, service.getCoopTermRegistration(COOPTERMREGISTRATION_KEY).getRegistrationID());
		}

	@Test
	public void contextLoads() {
	}


	
}
