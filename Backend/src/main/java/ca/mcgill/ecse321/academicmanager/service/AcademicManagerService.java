package ca.mcgill.ecse321.academicmanager.service;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CooperatorRepository;
import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;
import ca.mcgill.ecse321.academicmanager.dao.TermRepository;

import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Course;
import ca.mcgill.ecse321.academicmanager.model.Form;
//import ca.mcgill.ecse321.academicmanager.model.FormType;
import ca.mcgill.ecse321.academicmanager.model.Meeting;
import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.model.Term;
//import ca.mcgill.ecse321.academicmanager.model.TermStatus;

@Service
public class AcademicManagerService {
	
	@Autowired
	CooperatorRepository cooperatorRepository;
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	FormRepository formRepository;
	@Autowired
	MeetingRepository meetingRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	TermRepository termRepository;
	
	@Transactional
	public Cooperator createCooperator(Integer id) {
		Cooperator c = new Cooperator();
		c.setId(id);
		
		return cooperatorRepository.save(c);
	}
	
	@Transactional
	public List<Cooperator> getAllCooperators() {
		return toList(cooperatorRepository.findAll());
	}
	
	@Transactional
	public Cooperator getCooperator(Integer id) {
		return cooperatorRepository.findByid(id);
	}
	
	@Transactional
	public Student createStudent(String studentID, String firstname, String lastname, Cooperator c) {
		if(!checkArg(studentID) || !checkArg(firstname) || !checkArg(lastname) || !checkArg(c)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		Student student = new Student();
		
		student.setStudentID(studentID);
		student.setFirstName(firstname);
		student.setLastName(lastname);
		
		student.setCooperator(c);
		
		return studentRepository.save(student);
	}
	public Student updateStudent(Student student, String firstname, String lastname,
			CoopTermRegistration coopTermRegistration, Meeting meeting) {
		List<Student> students = getAllStudents();	
		if(!students.contains(student)) {
			throw new IllegalAddException("student not present");
		}
		
		if(checkArg(firstname)) {
			student.setFirstName(firstname);
		}
		if(checkArg(lastname)) {
			student.setLastName(lastname);
		}
//		if(checkArg(coopTermRegistration)) {
//			student.setCoopTermRegistration(coopTermRegistration);
//		}
//		if(checkArg(meeting)) {
//			List<Meeting> meetings = getAllMeetings();
//			meetings.add(meeting);
//			student.setMeeting(meetings);
//		}
		
		return studentRepository.save(student);
	}
	
	@Transactional
	public Student getStudent(String studentID) {
		return studentRepository.findByStudentID(studentID);
	}

	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	public Course createCourse(String courseID, String courseName, String term, Cooperator c) {
		Course course = new Course();
		course.setCourseID(courseID);
		course.setCourseName(courseName);
		course.setTerm(term);
		
		course.setCooperator(c);
		
		return courseRepository.save(course);
	}
	
	public Course getCourse(String courseID, String courseName) {
		return courseRepository.findByCourseIDAndTerm(courseID, courseName);
	}
	
	public List<Course> getAllCourses() {
		return toList(courseRepository.findAll());
	}
	
	public List<Meeting> getAllMeetings() {
		return toList(meetingRepository.findAll());
	}
	
	//---HELPER METHODS---
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
	
	private <T> boolean checkArg( T arg ) {
		boolean legal = true;
		if(arg == null) {
			legal = false;
		} else if(arg instanceof String && ((String) arg).trim().length() == 0) {
			legal = false;
		}
		
		return legal;
	}
	
}
