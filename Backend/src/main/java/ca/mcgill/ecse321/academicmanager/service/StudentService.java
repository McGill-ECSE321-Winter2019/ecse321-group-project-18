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

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	// ---CREATE---
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
	@Transactional
	public Student updateFirstName(Student student, String name) {
		if (Helper.checkArg(name))
			student.setFirstName(name);

		return studentRepository.save(student);
	}

	@Transactional
	public Student updateLastName(Student student, String name) {
		if (Helper.checkArg(name))
			student.setLastName(name);

		return studentRepository.save(student);
	}

	@Transactional
	public Student updateProblematicStatus(Student student, boolean isProblematic) {
		student.setIsProblematic(isProblematic);

		return studentRepository.save(student);
	}

	@Transactional
	public Student addMeeting(Student student, Meeting meeting) {
		if (!Helper.checkArg(meeting)) {
			throw new NullArgumentException();
		}
		student.addMeeting(meeting);

		return studentRepository.save(student);
	}

	// ---GET---
	@Transactional
	public Student get(String studentID) {
		return studentRepository.findByStudentID(studentID);
	}

	@Transactional
	public Set<Student> getAll() {
		return Helper.toSet(studentRepository.findAll());
	}

	@Transactional
	public List<Student> getAllProblematicStudents() {
		return studentRepository.findByIsProblematic(true);
	}

	@Transactional
	public Grade getStudentGrade(CoopTermRegistration ctr) {
		return ctr.getGrade();
	}

	@Transactional
	public Set<Student> getByIDAndStatus(String studentID, boolean isProblematic) {
		return Helper.toSet(studentRepository.findByStudentIDAndIsProblematic(studentID, isProblematic));
	}

	// ---DELETE---
	@Transactional
	public void delete(Student student) {
		studentRepository.delete(student);
	}

}
