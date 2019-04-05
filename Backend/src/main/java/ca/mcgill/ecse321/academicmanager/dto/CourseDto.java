package ca.mcgill.ecse321.academicmanager.dto;

import ca.mcgill.ecse321.academicmanager.model.Course;
/**
 * The CourseDto is the DTO (Data Transmission Object) of the Course entity.
 * It wraps the Course entity for to facilitate RESTful service calls.
 * The CourseDto is Comparable by its courseRank.
 * @author Bach Tran
 * @since 2019-02-19
 */
public class CourseDto implements Comparable <CourseDto> {
	
	private String courseID;
	private String term;
	private String courseName;
	private int courseRank;
	
	/**
	 * Empty constructor.
	 * @return a default CourseDto object, all of its attributes will be set to nulls.
	 */
	public CourseDto() { }
	/**
	 * Creates a new CourseDto object with all attributes.
	 * @throws IllegalArgumentException if courseID or term == null or courseID or term is an empty String.
	 * @param courseID (primary key) identifies the course.
	 * @param term (primary key) the term that this course belongs to.
	 * @param courseName the name of the course.
	 * @param courseRank represents the usefulness of the course.
	 * */
	public CourseDto(String courseID, String term, String courseName, int courseRank) {
		super();
		this.courseID = courseID;
		this.term = term;
		this.courseName = courseName;
		this.courseRank = courseRank;
	}
	/**
	 * Wraps a new CourseDto object directly from a Course object.
	 * Equates the attributes of CourseDto & Course.
	 * Attributes will be copied: courseID, term, courseName, courseRank.
	 * @throws IllegalArgumentException if course == null.
	 * @param course a Course object
	 * @return a new CourseDto object wrapping the Course object.
	 * */
//	public CourseDto(Course course) {
//		super();
//		if (course == null) throw new IllegalArgumentException("The given course parameter is null.");
//		this.courseID = course.getCourseID();
//		this.term = course.getTerm();
//		this.courseName = course.getCourseName();
//		this.courseRank = course.getCourseRank();
//	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseRank() {
		return courseRank;
	}

	public void setCourseRank(int courseRank) {
		this.courseRank = courseRank;
	}

	@Override
	public int compareTo(CourseDto other) {
		return this.courseRank - other.courseRank;
	}
}