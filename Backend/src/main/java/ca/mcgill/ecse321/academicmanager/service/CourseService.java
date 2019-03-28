package ca.mcgill.ecse321.academicmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.Course;

/**
 * course service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class CourseService {
	@Autowired
	CourseRepository courseRepository;

	// ---CREATE---
	/**
	 * Creates a new course instance
	 * 
	 * @param courseID   id of the Course instance
	 * @param term       term of offering
	 * @param courseName name of Course
	 * @param rank       usefulness rank of Course given by students
	 * @param c          Cooperator instance
	 * @return Course instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public Course create(String courseID, String term, String courseName, Integer rank, Cooperator c) {
		if (!Helper.checkArg(courseID) || !Helper.checkArg(term) || !Helper.checkArg(courseName)
				|| !Helper.checkArg(c)) {
			throw new NullArgumentException();
		}

		Course course = new Course();
		course.setCourseID(courseID);
		course.setTerm(term);
		course.setCourseName(courseName);
		course.setCourseRank(rank);

		course.setCooperator(c);
		return courseRepository.save(course);
	}

	// ---UPDATE---
	/**
	 * Updates rank of Course
	 * 
	 * @param course Course instance
	 * @param rank   new rank given
	 * @return updated Course instance
	 */
	@Transactional
	public Course updateRank(Course course, Integer rank) {
		if (Helper.checkArg(rank))
			course.setCourseRank(rank);
		return courseRepository.save(course);
	}

	/**
	 * Updates name of Course
	 * 
	 * @param course     Course instance
	 * @param courseName new Course name
	 * @return updated Course instance
	 */
	@Transactional
	public Course updateName(Course course, String courseName) {
		if (Helper.checkArg(courseName))
			course.setCourseName(courseName);
		return courseRepository.save(course);
	}

	// ---GET---
	/**
	 * Gets Course instance based on courseID and term
	 * 
	 * @param courseID
	 * @param term
	 * @return
	 */
	@Transactional
	public Course get(String courseID, String term) {
		return courseRepository.findByCourseIDAndTerm(courseID, term);
	}

	/**
	 * Gets all Course instances
	 * 
	 * @return Set of Course instances
	 */
	@Transactional
	public Set<Course> getAll() {
		return Helper.toSet(courseRepository.findAll());
	}

	// ---DELETE---
	/**
	 * Deletes a Course instance
	 * 
	 * @param course Course instance to be deleted
	 */
	@Transactional
	public void delete(Course course) {
		courseRepository.delete(course);
	}
}
