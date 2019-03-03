package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.Course;

public interface CourseRepository extends CrudRepository<Course, String> {
	Course findByCourseIDAndTerm(String CourseID, String term);
}
