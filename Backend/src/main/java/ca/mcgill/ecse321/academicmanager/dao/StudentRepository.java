package ca.mcgill.ecse321.academicmanager.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {
	Student findByStudentID(Integer studentID);
	
	List<Student> findByIsProblematic(boolean isProblematic);
}
