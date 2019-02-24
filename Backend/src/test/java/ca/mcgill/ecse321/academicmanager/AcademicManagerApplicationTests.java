package ca.mcgill.ecse321.academicmanager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.academicmanager.dao.CooperatorRepository;
import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;
import ca.mcgill.ecse321.academicmanager.dao.TermRepository;

import ca.mcgill.ecse321.academicmanager.service.*;

import org.mockito.invocation.InvocationOnMock;
import org.springframework.test.context.junit4.SpringRunner;
import ca.mcgill.ecse321.academicmanager.controller.AcademicManagerRestController;
import ca.mcgill.ecse321.academicmanager.model.Student;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest



public class AcademicManagerApplicationTests {

	@Mock
		private StudentRepository studentDao;
		@Mock
			private TermRepository termDao;
		@Mock
		private AcademicManagerService serviceMock;
		@InjectMocks
		private AcademicManagerRestController controller;
		@InjectMocks
		private AcademicManagerService service;

		private Student student;
		private static final String STUDENT_KEY = "226333211";
		private static final String NONEXISTING_KEY = "NotaID";
		
		
		@Before
		public void setMockOutput() {
		  when(studentDao.findByStudentID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
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
		public void setupMock() {
			student = mock(Student.class);
		}

		@Test
		public void testMockStudentCreation() {
			assertNotNull(student);
		}

		@Test
		public void testParticipantQueryFound() {
			Student mystudent=service.getStudent(STUDENT_KEY);
		    assertEquals(STUDENT_KEY, mystudent.getStudentID());
		}
		
	
	
	@Test
	public void contextLoads() {
	}


	
}

