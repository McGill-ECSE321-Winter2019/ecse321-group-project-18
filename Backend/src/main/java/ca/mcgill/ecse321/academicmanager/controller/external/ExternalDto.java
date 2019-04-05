package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.dto.*;
import ca.mcgill.ecse321.academicmanager.model.Course;

import java.sql.Date;

/**
 * An intermediate class capturing the JSON object on the Student team.
 * This class is in charge of making the data in the Student team and the AcademicManger team compatible.
 * @author Bach Tran
 * */
class ExternalStudentDto {
    protected String fullName;
    protected String studentID;
    protected String firstName;
    protected String lastName;

    /**
     * Creates a new ExternalStudentDto object, this object captures relevant data on the Student team.
     * @param fullName the full name of the Student. This will be automatically split into firstName and lastName
     * @param studentID the unique id of the Student.
     *
     */
    public ExternalStudentDto(String studentID, String fullName) {
        this.fullName = fullName;
        this.studentID = studentID;
        this.firstName = nameSeparator(this.fullName)[0];
        this.lastName = nameSeparator(this.fullName)[1];
    }

    public ExternalStudentDto(String studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ID = " + studentID + " fullName = " + fullName;
    }

    /**
     * Background: the fullName data on the Student team has only a single field, attaching both the first fullName and
     * the last fullName of the student. While on the Academic Manger's side, we separate those two attributes.
     * This method provides an adaptation to this difference.
     * @param name the raw fullName
     * @return a String array contains exactly two elements: the first fullName and the last fullName.
     * */
    private static String[] nameSeparator(String name) {
        String[] splitted = name.split(" ");
        // assumes the student doesn't have a last name.
        if (splitted.length < 2) {
            String first_name = splitted[0];
            String last_name = "<No_last_name>";
            splitted = new String[2];
            splitted[0] = first_name;
            splitted[1] = last_name;
        }
        // name has more than three words, in this case, the first word will be the first name,
        // the rest will become the last name.
        else if (splitted.length > 2) {
            String first_name = splitted[0];
            String last_name = "";
            for (int i = 1; i < splitted.length; i++) {
                last_name += splitted[i];
            }
            splitted = new String[2];
            splitted[0] = first_name;
            splitted[1] = last_name;
        }
        return splitted;
    }
}

/**
 * Helper class to contain the Term information from the external subsystem.
 * @author Bach Tran
 */
class ExternalTermDto extends TermDto {
    /**
     * Creates a new ExernalTermDto object.
     * This constructor is specifically designed for Team 04's data.
     * @param termName the name of the term.
     * @param date1 the deadline for the students to submit their evaluation form.
     * @param date2 the deadline for the employers to submit their evaluation form.
     */
    ExternalTermDto(String termID, String termName, String date1, String date2) {
        setTermID(termID);
        setTermName(termName);
        setDate1(Date.valueOf(date1));
        setDate2(Date.valueOf(date2));
    }

    /**
     * Creates a new Term.
     * Assuming that the deadline for students to submit their evaluation form
     * is on the 15th of March, and the deadline for the Employer is 30th of April.
     * @param termID the specific term ID.
     * @param termName the name of the term.
     * @param year the year of the term.
     */
    ExternalTermDto(String termID, String termName, String year) {
        setTermID(termID);
        setTermName(termName + " " + year);
        setDate1(Date.valueOf(year + "-03-15"));
        setDate2(Date.valueOf(year + "-04-30"));
    }

    /**
     * Creates a termName suitable for the internal viewpoint.
     * @param termName name of the term, correspond to the "term" attribute.
     * @param year year of the offer, correspond to the "year" attribute.
     * @return term full name, compatible with the data attribute in the AcademicManger's viewpoint.
     */
    static String termNameAdder(String termName, String year) {
        return termName.trim() + year.trim();
    }
    static String generateId(String termName, String year) {
        if (termName.isEmpty()) {
            // try to randomize a unique id
            return "NONAME" + year + System.currentTimeMillis();
        }
        return termName.toLowerCase() + year;
    }

    /**
     * We rewrite the equals method to ensure object uniqueness.
     * Compares if two ExternalStudentDto objects are equal.
     * @param o the other object.
     * @return true if two objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExternalTermDto)) {
            return false;
        }
        ExternalTermDto object = (ExternalTermDto) o;
        return this.getTermID() == object.getTermID();
    }
}

class ExternalCourseDto extends CourseDto {
    /**
     * Construct an ExternalCourseDto object directly from the Course
     * @param course a course entity
     */
    ExternalCourseDto(Course course) {
        this.setCourseID(course.getCourseID());
        this.setTerm(course.getTerm());
        this.setCourseName(course.getCourseName());
        this.setCourseRank(course.getCourseRank());
    }

    /**
     * Creates a new CourseDto object with all attributes.
     *
     * @param courseID   (primary key) identifies the course.
     * @param term       (primary key) the term that this course belongs to.
     * @param courseName the name of the course.
     * @param courseRank represents the usefulness of the course.
     * @throws IllegalArgumentException if courseID or term == null or courseID or term is an empty String.
     */
    public ExternalCourseDto(String courseID, String term, String courseName, int courseRank) {
        super(courseID, term, courseName, courseRank);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) { return true; }
        if (!(o instanceof ExternalTermDto)) { return false; }
        ExternalCourseDto obj = (ExternalCourseDto) o;
        return this.getCourseID().equals(obj.getCourseID()) && this.getTerm().equals(obj.getTerm());
    }
}