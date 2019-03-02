package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.*;

import ca.mcgill.ecse321.academicmanager.exceptions.*;

import ca.mcgill.ecse321.academicmanager.model.*;

@Service
public class AcademicManagerService {

	@Autowired
	CooperatorRepository cooperatorRepository;
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	FormRepository formRepository;
	@Autowired
	MeetingRepository meetingRepository;
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	TermRepository termRepository;

	// ---Cooperator---
	@Transactional
	public Cooperator createCooperator(Integer id) {
		if (!checkArg(id)) {
			throw new NullArgumentException();
		}
		Cooperator c = new Cooperator();
		c.setId(id);

		return cooperatorRepository.save(c);
	}

	@Transactional
	public Cooperator getCooperator(Integer id) {
		return cooperatorRepository.findByid(id);
	}

	@Transactional
	public Set<Cooperator> getAllCooperators() {
		return toSet(cooperatorRepository.findAll());
	}
	// ---Cooperator---

	// ---CoopTermRegistration---
	@Transactional
	public CoopTermRegistration createCoopTermRegistration(String registrationID, String jobID, TermStatus status,
			Grade grade, Student student, Term term) {
		if (!checkArg(registrationID) || !checkArg(jobID) || !checkArg(status) || !checkArg(student)
				|| !checkArg(term)) {
			throw new NullArgumentException();
		}

		CoopTermRegistration ctr = new CoopTermRegistration();
		ctr.setRegistrationID(registrationID);
		ctr.setTermStatus(status);
		ctr.setJobID(jobID);
		ctr.setGrade(grade);

		// check if student is already registered in that term
		Set<CoopTermRegistration> StudentCtrs = student.getCoopTermRegistration();
		if (StudentCtrs != null) {
			for (CoopTermRegistration ctrTemp : StudentCtrs) {
				if (ctrTemp.getTerm() == term) {
					throw new IllegalAddException("Student is already registerd for the given term");
				}
			}
		}

		ctr.setStudent(student);
		ctr.setTerm(term);
		// student.addCoopTermRegistration(ctr);
		// term.addCoopTermRegistration(ctr);

		return coopTermRegistrationRepository.save(ctr);
	}

	@Transactional
	public CoopTermRegistration updateCoopTermRegistration(CoopTermRegistration ctr, TermStatus status, Grade grade) {
		if (checkArg(status)) {
			ctr.setTermStatus(status);
		}
		if (checkArg(grade)) {
			ctr.setGrade(grade);
		}

		return coopTermRegistrationRepository.save(ctr);
	}

	@Transactional
	public CoopTermRegistration getCoopTermRegistration(String registrationID) {
		return coopTermRegistrationRepository.findByRegistrationID(registrationID);
	}

	@Transactional
	public Set<CoopTermRegistration> getAllCoopTermRegistration() {
		return toSet(coopTermRegistrationRepository.findAll());
	}
	// ---CoopTermRegistration---

	// ---Course---
	@Transactional
	public Course createCourse(String courseID, String term, String courseName, Integer rank, Cooperator c) {
		if (!checkArg(courseID) || !checkArg(term) || !checkArg(courseName) || !checkArg(c)) {
			throw new NullArgumentException();
		}

		Course course = new Course();
		course.setCourseID(courseID);
		course.setTerm(term);
		course.setCourseName(courseName);
		course.setCourseRank(rank);

		course.setCooperator(c);
		// c.addCourse(course);

		return courseRepository.save(course);
	}

	@Transactional
	public Course updateCourseRank(Course course, Integer rank) {
		if(checkArg(rank))
			course.setCourseRank(rank);
		return courseRepository.save(course);
	}

	@Transactional
	public Course getCourse(String courseID, String term) {
		return courseRepository.findByCourseIDAndTerm(courseID, term);
	}

	@Transactional
	public Set<Course> getAllCourses() {
		return toSet(courseRepository.findAll());
	}
	// ---Course---

	// ---Form---
	@Transactional
	public Form createForm(String formID, String name, String pdflink, FormType formtype, CoopTermRegistration ctr) {
		if (!checkArg(name) || !checkArg(pdflink) || !checkArg(formtype) || !checkArg(ctr)) {
			throw new NullArgumentException();
		}

		Form form = new Form();
		form.setFormID(formID);
		form.setName(name);
		form.setPdfLink(pdflink);
		form.setFormType(formtype);

		ctr.addForm(form);

		return formRepository.save(form);
	}

	@Transactional
	Form updateForm(Form form, String formID, String name, String pdflink, FormType formtype) {
		if (!checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if (checkArg(formID)) {
			form.setFormID(formID);
		}
		if (checkArg(name)) {
			form.setName(name);
		}
		if (checkArg(pdflink)) {
			form.setPdfLink(pdflink);
		}
		if (checkArg(formtype)) {
			form.setFormType(formtype);
		}

		return formRepository.save(form);
	}

	@Transactional
	public Set<Form> getAllEmployerEvalForms() {
		Set<CoopTermRegistration> ctrs = this.getAllCoopTermRegistration();

		Set<Form> forms = new HashSet<Form>();
		Set<Form> employerForms = new HashSet<Form>();

		for (CoopTermRegistration ctr : ctrs) {
			forms = ctr.getForm();
			for (Form form : forms) {
				if (form.getFormType() == FormType.COOPEVALUATION) {
					employerForms.add(form);
				}
			}
		}
		return employerForms;
	}

	@Transactional
	public Set<Form> getAllStudentEvalForms() {
		Set<CoopTermRegistration> ctrs = this.getAllCoopTermRegistration();

		Set<Form> forms = new HashSet<Form>();
		Set<Form> studentForms = new HashSet<Form>();

		for (CoopTermRegistration ctr : ctrs) {
			forms = ctr.getForm();
			for (Form form : forms) {
				if (form.getFormType() == FormType.STUDENTEVALUATION) {
					studentForms.add(form);
				}
			}
		}
		return studentForms;
	}
	// ---Form---

	// ---Meeting---
	/*@Transactional
	public Meeting createMeeting(String meetingID, String location, String details, Date date, Time startTime,
			Time endTime) {
		// check for nulls
		if (!checkArg(meetingID) || !checkArg(location) || !checkArg(startTime) || !checkArg(endTime)) {
			throw new NullArgumentException();
		}

		// check for invalid time constraints
		if (endTime.compareTo(startTime) < 0) {
			throw new InvalidEndTimeException();
		}

		Meeting meeting = new Meeting();
		meeting.setMeetingID(meetingID);
		meeting.setLocation(location);
		meeting.setDate(date);
		meeting.setStartTime(startTime);
		meeting.setEndTime(endTime);

		if (checkArg(details)) {
			meeting.setDetails(details);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting addMeetingStudent(Meeting meeting, Student student) {
		if (!checkArg(student)) {
			throw new NullArgumentException();
		}

		student.addMeeting(meeting);
		meeting.addStudent(student);

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting updateMeeting(Meeting meeting, String location, String details, Date date, Time startTime,
			Time endTime, Student student) {
		if (checkArg(location)) {
			meeting.setLocation(location);
		}
		if (checkArg(details)) {
			meeting.setDetails(details);
		}
		if (checkArg(date)) {
			meeting.setDate(date);
		}
		if (checkArg(startTime)) {
			meeting.setStartTime(startTime);
		}
		if (checkArg(endTime)) {
			meeting.setEndTime(endTime);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting getMeeting(String meetingID) {
		return meetingRepository.findByMeetingID(meetingID);
	}

	@Transactional
	public Set<Student> getMeetingStudents(Meeting meeting) {
		return meeting.getStudent();
	}

	@Transactional
	public Set<Meeting> getAllMeetings() {
		return toSet(meetingRepository.findAll());
	}*/
	// ---Meeting---

	// ---Student---
	@Transactional
	public Student createStudent(String studentID, String firstname, String lastname, Cooperator c) {
		if (!checkArg(studentID) || !checkArg(firstname) || !checkArg(lastname) || !checkArg(c)) {
			throw new NullArgumentException();
		}

		Student student = new Student();

		student.setStudentID(studentID);
		student.setFirstName(firstname);
		student.setLastName(lastname);
		student.setIsProblematic(false);

		student.setCooperator(c);
		// c.addStudent(student);

		return studentRepository.save(student);
	}

//	@Transactional
//	public Student addStudentMeeting(Student student, Meeting meeting) {
//		if (!checkArg(meeting)) {
//			throw new NullArgumentException();
//		}
//
//		student.addMeeting(meeting);
//		meeting.addStudent(student);
//
//		return studentRepository.save(student);
//	}

	@Transactional
	public Student updateStudentProblematicStatus(Student student, boolean isProblematic) {
		student.setIsProblematic(isProblematic);
		return studentRepository.save(student);
	}

	@Transactional
	public List<Student> getAllProblematicStudents() {
		return studentRepository.findByIsProblematic(true);
	}

	@Transactional
	public Student getStudent(String studentID) {
		return studentRepository.findByStudentID(studentID);
	}

	@Transactional
	public Set<Student> getAllStudents() {
		return toSet(studentRepository.findAll());
	}

	@Transactional
	public Grade getStudentGrade(CoopTermRegistration ctr) {
		return ctr.getGrade();
	}
	// ---Student---

	// ---Term---
	@Transactional
	public Term createTerm(String termID, String termName, Date studentEvalFormDeadline, Date coopEvalFormDeadline) {
		if (!checkArg(termID)) {
			throw new NullArgumentException();
		}

		Term term = new Term();
		term.setTermID(termID);
		term.setTermName(termName);
		term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		term.setCoopEvalFormDeadline(coopEvalFormDeadline);

		return termRepository.save(term);
	}
	//
	// @Transactional
	// public void addTermCtr(Term term, CoopTermRegistration ctr) {
	// if(!checkArg(ctr)) {
	// throw new NullArgumentException();
	// }
	//
	// Set<CoopTermRegistration> ctrs = term.getCoopTermRegistration();
	// try {
	// ctrs.add(ctr);
	// }
	// catch(Exception e) {
	// ctrs = new HashSet<>();
	// ctrs.add(ctr);
	// }
	// term.setCoopTermRegistration(ctrs);
	//
	//// return termRepository.save(term);
	// }

	@Transactional
	public Term updateTerm(Term term, String termName, Date studentEvalFormDeadline, Date coopEvalFormDeadline) {
		if (checkArg(termName)) {
			term.setTermName(termName);
		}
		if (checkArg(studentEvalFormDeadline)) {
			term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		}
		if (checkArg(coopEvalFormDeadline)) {
			term.setCoopEvalFormDeadline(coopEvalFormDeadline);
		}

		return termRepository.save(term);
	}

	@Transactional
	public Term getTerm(String termID) {
		return termRepository.findByTermID(termID);
	}

	// @Transactional
	// public Set<Term> getAllTerms() {
	// return toSet(termRepository.findAll());
	// }
	// ---Term---

	// ---HELPER METHODS---
	private <T> Set<T> toSet(Iterable<T> iterable) {
		Set<T> res = new HashSet<T>();
		for (T t : iterable) {
			res.add(t);
		}
		return res;
	}

	private <T> boolean checkArg(T arg) {
		boolean legal = true;
		if (arg == null) {
			legal = false;
		} else if (arg instanceof String && ((String) arg).trim().length() == 0) {
			legal = false;
		}

		return legal;
	}

}