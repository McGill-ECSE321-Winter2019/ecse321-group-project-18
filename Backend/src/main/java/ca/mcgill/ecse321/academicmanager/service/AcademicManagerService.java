package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CoopPositionRepository;
import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;

import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.CoopPosition;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Course;
import ca.mcgill.ecse321.academicmanager.model.Form;
import ca.mcgill.ecse321.academicmanager.model.FormType;
import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;

@Service
public class AcademicManagerService {
	
	@Autowired
	CoopPositionRepository coopPositionRepository;
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	FormRepository formRepository;
	@Autowired
	StudentRepository studentRepository;
	
	@Transactional
	public Student createStudent(Integer studentID, String firstname, String lastname) {
		Student s = new Student();
		s.setStudentID(studentID);
		s.setFirstName(firstname);
		s.setLastName(lastname);
		
		studentRepository.save(s);
		return s;
	}
	
	@Transactional
	public Student getStudent(Integer studentID) {
		Student s = studentRepository.findByStudentID(studentID);
		return s;
	}

	@Transactional
	public List<Student> getAllStudents() {
		return toList(studentRepository.findAll());
	}
	
	private <T> List<T> toList(Iterable<T> iterable){
		List<T> resultList = new ArrayList<T>();
		for (T t : iterable) {
			resultList.add(t);
		}
		return resultList;
	}
}
