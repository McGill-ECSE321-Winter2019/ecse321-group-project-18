package ca.mcgill.ecse321.academicmanager.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.Grade;
import ca.mcgill.ecse321.academicmanager.model.Meeting;
import ca.mcgill.ecse321.academicmanager.model.Student;

/**
 * student service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	// ---CREATE---
	/**
	 * Creates a new Student instance
	 * 
	 * @param studentID id of the student
	 * @param firstname first name of the student
	 * @param lastname  last name of the student
	 * @param c         Cooperator instance
	 * @return Student instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public Student create(String studentID, String firstname, String lastname, Cooperator c) {
		if (!Helper.checkArg(studentID) || !Helper.checkArg(firstname) || !Helper.checkArg(lastname)
				|| !Helper.checkArg(c)) {
			throw new NullArgumentException();
		}

		Student student = new Student();

		student.setStudentID(studentID);
		student.setFirstName(firstname);
		student.setLastName(lastname);
		student.setIsProblematic(false);
		student.setCooperator(c);

		return studentRepository.save(student);
	}

	// ---UPDATE---
	/**
	 * Updates first name of Student
	 * 
	 * @param student Student instance
	 * @param name    new first name
	 * @return updated Student instance
	 */
	@Transactional
	public Student updateFirstName(Student student, String name) {
		if (Helper.checkArg(name))
			student.setFirstName(name);

		return studentRepository.save(student);
	}

	/**
	 * Updates last name of Student
	 * 
	 * @param student Student instance
	 * @param name    new last name
	 * @return updated Student instance
	 */
	@Transactional
	public Student updateLastName(Student student, String name) {
		if (Helper.checkArg(name))
			student.setLastName(name);

		return studentRepository.save(student);
	}

	/**
	 * Updates problematic status of Student
	 * 
	 * @param student       Student instance
	 * @param isProblematic new status
	 * @return updated Student instance
	 */
	@Transactional
	public Student updateProblematicStatus(Student student, boolean isProblematic) {
		student.setIsProblematic(isProblematic);

		return studentRepository.save(student);
	}

	/**
	 * Adds a meeting to Student instance
	 * 
	 * @param student Student instance
	 * @param meeting Meeting instance
	 * @return updated Student instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public Student addMeeting(Student student, Meeting meeting) {
		if (!Helper.checkArg(meeting)) {
			throw new NullArgumentException();
		}
		student.addMeeting(meeting);

		return studentRepository.save(student);
	}

	// ---GET---
	/**
	 * Gets Student based on studentID
	 * 
	 * @param studentID id of the student
	 * @return Student instance
	 */
	@Transactional
	public Student get(String studentID) {
		return studentRepository.findByStudentID(studentID);
	}

	/**
	 * Gets all Student instances
	 * 
	 * @return Set of Student instances
	 */
	@Transactional
	public Set<Student> getAll() {
		return Helper.toSet(studentRepository.findAll());
	}

	/**
	 * Gets Student instances with problematic status true
	 * 
	 * @return Set of Student instances
	 */
	@Transactional
	public List<Student> getAllProblematicStudents() {
		return studentRepository.findByIsProblematic(true);
	}

	/**
	 * Gets grade of student
	 * 
	 * @param ctr CoopTermRegistration instance
	 * @return Grade of student
	 */
	@Transactional
	public Grade getStudentGrade(CoopTermRegistration ctr) {
		return ctr.getGrade();
	}

	/**
	 * Gets Student instances based on studentID and isProblematic
	 * 
	 * @param studentID     id of the student
	 * @param isProblematic new status
	 * @return Set of Student instances
	 */
	@Transactional
	public Set<Student> getByIDAndStatus(String studentID, boolean isProblematic) {
		return Helper.toSet(studentRepository.findByStudentIDAndIsProblematic(studentID, isProblematic));
	}

	// ---DELETE---
	/**
	 * Deletes a Student instance
	 * 
	 * @param student Student instance to be deleted
	 */
	@Transactional
	public void delete(Student student) {
		studentRepository.delete(student);
	}
}
