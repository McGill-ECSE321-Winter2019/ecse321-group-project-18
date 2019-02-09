package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.Student;

public interface StudentRepository extends CrudRepository<Student, String> {
	Student findByStudentID(String studentID);
}
