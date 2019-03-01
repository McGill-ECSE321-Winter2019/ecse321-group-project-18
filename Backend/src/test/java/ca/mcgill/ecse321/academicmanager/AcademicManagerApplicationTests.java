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
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest



public class AcademicManagerApplicationTests {

	@Mock 
		private CooperatorRepository cooperatorDao;
		@Mock
		private StudentRepository studentDao;
		@Mock
		private TermRepository termDao;
		@Mock
		private CourseRepository courseDao;
		@Mock
		private CoopTermRegistrationRepository coopTermRegistrationDao;
		@Mock
		private AcademicManagerService serviceMock;
		@InjectMocks
		private AcademicManagerRestController controller;
		@InjectMocks
		private AcademicManagerService service;


		/********** STUDENT TEST *********/
	
		private Student student;
		private static final String STUDENT_KEY = "226333211";
		private static final String NONEXISTING_STUDENT_KEY = "NotaID";
		
		@Before
		public void setStudentMockOutput() {
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
		private static final String COOPTERMREGISTRATION_KEY2 = "Internship002";
		private static final String NONEXISTING_COOPREGISTRATION_KEY = "NotInternship";
		
		@Before
		public void setCoopTermRegistrationMockOutput() {
		when(coopTermRegistrationDao.findByRegistrationID(anyString())).thenAnswer( (InvocationOnMock invocation) -> {
		    if(invocation.getArgument(0).equals(COOPTERMREGISTRATION_KEY)) {
		      CoopTermRegistration coopTermRegistration = new CoopTermRegistration();
		      coopTermRegistration.setRegistrationID(COOPTERMREGISTRATION_KEY);
		      return coopTermRegistration;
		    } 
		    else if(invocation.getArgument(0).equals(COOPTERMREGISTRATION_KEY2)) {
		      CoopTermRegistration coopTermRegistration = new CoopTermRegistration();
		      coopTermRegistration.setRegistrationID(COOPTERMREGISTRATION_KEY2);
		      return coopTermRegistration;
		    } 
		    else {
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
		
		@Test(expected=NullPointerException.class)
		public void testCoopTermRegistrationInvalidQuery() {
			assertNull(service.getCoopTermRegistration(NONEXISTING_COOPREGISTRATION_KEY).getRegistrationID());
			assertNull(service.getCoopTermRegistration(null).getRegistrationID());
		}
		
		@Test
		public void testUpdateCoopTermRegistrationValidInput() {
			coopTermRegistration.setRegistrationID(COOPTERMREGISTRATION_KEY2);
			assertEquals(COOPTERMREGISTRATION_KEY2, service.getCoopTermRegistration(COOPTERMREGISTRATION_KEY2).getRegistrationID());
		}
		
		@Test(expected=NullPointerException.class)
		public void testUpdateCoopTermRegistrationInvalidInput() {
			coopTermRegistration.setRegistrationID(NONEXISTING_COOPREGISTRATION_KEY);
			assertNull(service.getCoopTermRegistration(NONEXISTING_COOPREGISTRATION_KEY).getRegistrationID());
		}

		
		/******************* COOPERATOR TEST ****************/
		@Mock	
			private Cooperator cooperator;
			private static final Integer COOPERATOR_KEY = 1;
			private static final Integer COOPERATOR_KEY2 = 2;
			private static final Integer NONEXISTING_COOPERATOR_KEY = null;
			
			@Before
			public void setCooperatorMockOutput() {
			when(cooperatorDao.findByid(anyInt())).thenAnswer( (InvocationOnMock invocation) -> {
			    if(invocation.getArgument(0).equals(COOPERATOR_KEY)) {
			      Cooperator cooperator = new Cooperator();
			      cooperator.setId(COOPERATOR_KEY);
			      return cooperator;
			    } 
			    else if(invocation.getArgument(0).equals(COOPERATOR_KEY2)) {
			      Cooperator cooperator = new Cooperator();
				  cooperator.setId(COOPERATOR_KEY2);
				  return cooperator;
			    } 
			    else {
			      return null;
			    }
			  });
			}
			
			@Before
			public void setupCooperatorMock() {
				cooperator = mock(Cooperator.class);
			}

			@Test
			public void testMockCooperatorCreation() {
				assertNotNull(cooperator);
			}

			@Test
			public void testCooperatorQueryFound() {
				assertEquals(COOPERATOR_KEY, service.getCooperator(COOPERATOR_KEY).getId());
			}
			
			@Test(expected=NullPointerException.class)
			public void testCooperatorInvalidQuery() {
				assertNull(service.getCooperator(NONEXISTING_COOPERATOR_KEY).getId());
			}
			
			@Test
			public void testUpdateCooperatorValidInput() {
				cooperator.setId(COOPERATOR_KEY2);
				assertEquals(COOPERATOR_KEY2, service.getCooperator(COOPERATOR_KEY2).getId());
			}
			
			@Test(expected=NullPointerException.class)
			public void testUpdateCooperatorInvalidInput() {
				cooperator.setId(NONEXISTING_COOPERATOR_KEY);
				assertNull(service.getCooperator(NONEXISTING_COOPERATOR_KEY).getId());
			}

			/******************* COURSE TEST ****************/
		@Mock	
			private Course course;
			private static final String COURSE_KEY1 = "Course001";
			private static final String COURSE_TERM1 = "Winter2019";
			private static final String COURSE_KEY2 = "Course002";
			private static final String COURSE_TERM2 = "Fall2019";
			private static final String NONEXISTING_COURSE_KEY = "NotACourse";
			private static final String NONEXISTING_COURSE_TERM = "NotATerm";
			
			@Before
			public void setCourseMockOutput() {
			when(courseDao.findByCourseIDAndTerm(anyString(), anyString())).thenAnswer( (InvocationOnMock invocation) -> {
			    if(invocation.getArgument(0).equals(COURSE_KEY1) && 
			    	invocation.getArgument(1).equals(COURSE_TERM1)) {
			      Course course = new Course();
			      course.setCourseID(COURSE_KEY1);
			      course.setTerm(COURSE_TERM1);
			      return course;
			    } 
			    if(invocation.getArgument(0).equals(COURSE_KEY2) && 
				    invocation.getArgument(1).equals(COURSE_TERM2)) {
			      Course course = new Course();
			      course.setCourseID(COURSE_KEY2);
			      course.setTerm(COURSE_TERM2);
			      return course;
				}
			    else {
			      return null;
			    }
			  });
			}
			
			@Before
			public void setupCourseMock() {
				course = mock(Course.class);
			}

			@Test
			public void testMockCourseCreation() {
				assertNotNull(course);
			}

			@Test
			public void testCourseQueryFound() {
				assertEquals(COURSE_KEY1, service.getCourse(COURSE_KEY1, COURSE_TERM1).getCourseID());
				assertEquals(COURSE_TERM1, service.getCourse(COURSE_KEY1, COURSE_TERM1).getTerm());
			}
			
			@Test(expected=NullPointerException.class)
			public void testCourseInvalidQuery() {
				assertNull(service.getCourse(NONEXISTING_COURSE_KEY, NONEXISTING_COURSE_TERM).getCourseID());
				assertNull(service.getCourse(NONEXISTING_COURSE_KEY, NONEXISTING_COURSE_TERM).getTerm());
				assertNull(service.getCourse(null, null).getCourseID());
				assertNull(service.getCourse(null, null).getTerm());
			}
			
			@Test
			public void testUpdateCourseValidInput() {
				course.setCourseID(COURSE_KEY2);
				course.setTerm(COURSE_TERM2);
				assertEquals(COURSE_KEY2, service.getCourse(COURSE_KEY2, COURSE_TERM2).getCourseID());
				assertEquals(COURSE_TERM2, service.getCourse(COURSE_KEY2, COURSE_TERM2).getTerm());
			}
			
			@Test(expected=NullPointerException.class)
			public void testUpdateCourseInvalidInput() {
				course.setCourseID(NONEXISTING_COURSE_KEY);
				course.setTerm(NONEXISTING_COURSE_TERM);
				assertNull(service.getCourse(NONEXISTING_COURSE_KEY, NONEXISTING_COURSE_TERM).getCourseID());
				assertNull(service.getCourse(NONEXISTING_COURSE_KEY, NONEXISTING_COURSE_TERM).getTerm());
			}

	@Test
	public void contextLoads() {
	}


	
}
