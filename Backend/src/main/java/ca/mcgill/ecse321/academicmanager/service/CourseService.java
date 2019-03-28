package ca.mcgill.ecse321.academicmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.Course;

@Service
public class CourseService {
	@Autowired
	CourseRepository courseRepository;

	// ---CREATE---
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
	@Transactional
	public Course updateRank(Course course, Integer rank) {
		if (Helper.checkArg(rank))
			course.setCourseRank(rank);
		return courseRepository.save(course);
	}

	@Transactional
	public Course updateName(Course course, String courseName) {
		if (Helper.checkArg(courseName))
			course.setCourseName(courseName);
		return courseRepository.save(course);
	}

	// ---GET---
	@Transactional
	public Course get(String courseID, String term) {
		return courseRepository.findByCourseIDAndTerm(courseID, term);
	}

	@Transactional
	public Set<Course> getAll() {
		return Helper.toSet(courseRepository.findAll());
	}

	// ---DELETE---
	@Transactional
	public void delete(Course course) {
		courseRepository.delete(course);
	}
}
