package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CooperatorRepository;
import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;
import ca.mcgill.ecse321.academicmanager.dao.TermRepository;

import ca.mcgill.ecse321.academicmanager.model.Cooperator;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Course;
import ca.mcgill.ecse321.academicmanager.model.Form;
import ca.mcgill.ecse321.academicmanager.model.FormType;
import ca.mcgill.ecse321.academicmanager.model.Grade;
import ca.mcgill.ecse321.academicmanager.model.Meeting;
import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.model.Term;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;

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
	
	//---Cooperator---
	@Transactional
	public Cooperator createCooperator(Integer id) {
		if(!checkArg(id)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		Cooperator c = new Cooperator();
		c.setId(id);
		
		return cooperatorRepository.save(c);
	}
	
	@Transactional
	public Cooperator updateCooperator(Cooperator c, Student student, Course course) {
		if(checkArg(student)) {
			Set<Student> students = c.getStudent();
			if(!students.contains(student)) {
				students.add(student);
				c.setStudent(students);		
			}
		}
		if(checkArg(course)) {
			Set<Course> courses = c.getCourse();
			if(!courses.contains(course)) {
				courses.add(course);
				c.setCourse(courses);
			}
		}
		
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
	//---Cooperator---
	
	//---CoopTermRegistration---
	@Transactional
	public CoopTermRegistration createCoopTermRegistration(String registrationID, String jobID, TermStatus status, Student student) {
		if(!checkArg(registrationID) || !checkArg(jobID) || !checkArg(student) || !checkArg(status)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		CoopTermRegistration CTR = new CoopTermRegistration();
		CTR.setRegistrationID(registrationID);
		CTR.setTermStatus(status);
		CTR.setJobID(jobID);
		CTR.setStudent(student);
		student.setCoopTermRegistration(CTR);

		return coopTermRegistrationRepository.save(CTR);
	}
	
	@Transactional
	public CoopTermRegistration updateCoopTermRegistration(CoopTermRegistration CTR, TermStatus status, Form form, Student student, Term term) {
		if(checkArg(status)) {
			CTR.setTermStatus(status);
		}
		if(checkArg(form)) {
			Set<Form> forms = CTR.getForm();
			if(!forms.contains(form)) {
				forms.add(form);
				CTR.setForm(forms);				
			}
		}
		if(checkArg(student)) {
			CTR.setStudent(student);
		}
		if(checkArg(term)) {
			CTR.setTerm(term);
		}
		
		return coopTermRegistrationRepository.save(CTR);
	}
	
	@Transactional
	public CoopTermRegistration getCoopTermRegistration(String registrationID) {
		return coopTermRegistrationRepository.findByRegistrationID(registrationID);
	}
	
	@Transactional
	public Set<CoopTermRegistration> getAllCoopTermRegistration() {
		return toSet(coopTermRegistrationRepository.findAll());
	}
	//---CoopTermRegistration---
	
	//---Course---
	@Transactional
	public Course createCourse(String courseID, String term, String courseName, Integer rank, Cooperator c) {
		if(!checkArg(courseID) || !checkArg(term) || !checkArg(courseName) || !checkArg(c)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		Course course = new Course();
		course.setCourseID(courseID);
		course.setTerm(term);
		course.setCourseName(courseName);
		course.setCourseRank(rank);
		
		course.setCooperator(c);
		
		return courseRepository.save(course);
	}
	
	@Transactional
	public Course updateCourseRank(Course course, Integer rank) {
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
	//---Course---
	
	//---Form---
	@Transactional
	public Form createForm(String formID, String name, String pdflink, FormType formtype, CoopTermRegistration ctr) {
		if(!checkArg(name) || !checkArg(pdflink) || !checkArg(formtype)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		Form form = new Form();
		form.setFormID(formID);
		form.setName(name);
		form.setPdfLink(pdflink);
		form.setFormType(formtype);
		form.setCoopTermRegistration(ctr);
		
		if(checkArg(ctr)) {
			Set<Form> forms = ctr.getForm();
			
			if(!checkArg(forms)) {
				forms = new HashSet<Form>();
			}
			
			forms.add(form);
			ctr.setForm(forms);
		}
		
		return formRepository.save(form);
	}
	
	@Transactional
	Form updateForm(Form form, String formID, String name, String pdflink, FormType formtype, CoopTermRegistration ctr) {
		if(!checkArg(form)) {
			throw new IllegalArgumentException("form is null");
		}
		if(checkArg(formID)) {
			form.setFormID(formID);
		}
		if(checkArg(name)) {
			form.setName(name);
		}
		if(checkArg(pdflink)) {
			form.setPdfLink(pdflink);
		}
		if(checkArg(formtype)) {
			form.setFormType(formtype);
		}
		if(checkArg(ctr)) {
			form.setCoopTermRegistration(ctr);
		}
		
		return formRepository.save(form);
	}

	
	@Transactional
	public Set<Form> getAllStudentEvalFormsOfStudent(Student student) {
		if(!checkArg(student)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		CoopTermRegistration ctr = student.getCoopTermRegistration();
		
		if(!checkArg(ctr) ) {
			throw new IllegalArgumentException("student is not registered for a term");
		}
		
		Set<Form> forms = ctr.getForm();
		
		for(Form form : forms) {
			if(form.getFormType() != FormType.STUDENTEVALUATION) {
				forms.remove(form);
			}
		}
		
		return forms;
	}
	
	@Transactional
	public Set<Form> getAllEmployerEvalFormsOfStudent(Student student) {
		if(!checkArg(student)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		CoopTermRegistration ctr = student.getCoopTermRegistration();
		
		if(!checkArg(ctr) ) {
			throw new IllegalArgumentException("student is not registered for a term");
		}
		
		Set<Form> forms = ctr.getForm();
		
		for(Form form : forms) {
			if(form.getFormType() != FormType.COOPEVALUATION) {
				forms.remove(form);
			}
		}
		
		return forms;
	}
	//---Form---
	
	//---Meeting---
	@Transactional
	public Meeting createMeeting(String meetingID, String location, String details, Date date, Time startTime, Time endTime, Set<Student> students) 
	{
		// check for nulls
		if(!checkArg(meetingID) || !checkArg(location) || !checkArg(startTime) || !checkArg(endTime)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		// check for invalid time constraints
		if (endTime.compareTo(startTime) < 0) {
			throw new IllegalArgumentException("endTime need to happen after startTime.");
		}
		
		Meeting meeting = new Meeting();
		meeting.setMeetingID(meetingID);
		meeting.setLocation(location);
		meeting.setDate(date);
		meeting.setStartTime(startTime);
		meeting.setEndTime(endTime);
		
		if(checkArg(details)) {
			meeting.setDetails(details);
		}
		if(checkArg(students)) {
			meeting.setStudent(students);
		}
		
		return meetingRepository.save(meeting);
	}
	
	@Transactional
	public Meeting updateMeeting(Meeting meeting, String location, String details, Date date, Time startTime, Time endTime, Student student) {
		if(checkArg(location)) {
			meeting.setLocation(location);
		}
		if(checkArg(details)) {
			meeting.setDetails(details);
		}
		if(checkArg(date)) {
			meeting.setDate(date);
		}
		if(checkArg(startTime)) {
			meeting.setStartTime(startTime);
		}
		if(checkArg(endTime)) {
			meeting.setEndTime(endTime);
		}
		if(checkArg(student)) {
			Set<Student> students = meeting.getStudent();
			if(!students.contains(student)) {
				students.add(student);
				meeting.setStudent(students);
			}
		}
		
		return meetingRepository.save(meeting);
	}
	
	@Transactional
	public Meeting getMeeting(String meetingID) {
		return meetingRepository.findByMeetingID(meetingID);
	}
	
	@Transactional
	public Set<Meeting> getAllMeetings() {
		return toSet(meetingRepository.findAll());
	}
	//---Meeting---
	
	//---Student---
	@Transactional
	public Student createStudent(String studentID, String firstname, String lastname, Grade grade, Cooperator c) {
		if(!checkArg(studentID) || !checkArg(firstname) || !checkArg(lastname) || !checkArg(grade) || !checkArg(c)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		
		Student student = new Student();
		
		student.setStudentID(studentID);
		student.setFirstName(firstname);
		student.setLastName(lastname);
		student.setGrade(grade);
		student.setCooperator(c);
		student.setIsProblematic(false);
		
		return studentRepository.save(student);
	}
	
	@Transactional
	public List<Student> getAllProblematicStudents(){
		List<Student> students = studentRepository.findByIsProblematic(true);
		return students;
	}
	
	@Transactional
	public Student updateStudent(Student student, CoopTermRegistration coopTermRegistration, Meeting meeting) {
		if(checkArg(coopTermRegistration)) {
			student.setCoopTermRegistration(coopTermRegistration);
		}
		if(checkArg(meeting)) {
			Set<Meeting> meetings = student.getMeeting();
			if(!meetings.contains(meeting)) {
				meetings.add(meeting);
				student.setMeeting(meetings);
			}
		}
		
		return studentRepository.save(student);
	}
	
	@Transactional
	public Student updateStudentProblematicStatus(Student student, boolean isProblematic) {
		student.setIsProblematic(isProblematic);
		return studentRepository.save(student);
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
	public Grade getStudentGrade(String studentID) {
		Grade grade;
		Student student = getStudent(studentID);
		grade = student.getGrade();
		return grade;
	}
	//---Student---
	
	//---Term---
	@Transactional
	public Term createTerm(String termID, String termName, Date studentEvalFormDeadline, Date coopEvalFormDeadline, Set<CoopTermRegistration> CTRs) {
		if(!checkArg(termID) || !checkArg(termName)) {
			throw new IllegalArgumentException("one or more argument(s) is/are null/empty");
		}
		
		Term term = new Term();
		term.setTermID(termID);
		term.setTermName(termName);
		term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		term.setCoopEvalFormDeadline(coopEvalFormDeadline);
		
		if(checkArg(CTRs)) {
			term.setCoopTermRegistration(CTRs);
		}
		
		return termRepository.save(term);
	}
	@Transactional
	public Term addCoopTermRegistration(Term term, CoopTermRegistration CTR) {
		if(checkArg(CTR)) {
			Set<CoopTermRegistration> CTRs = term.getCoopTermRegistration();
			if(!CTRs.contains(CTR)) {
				CTRs.add(CTR);
				term.setCoopTermRegistration(CTRs);
			}
		}
		
		return termRepository.save(term);
	}
	
	@Transactional
	public Term updateTerm(Term term, Date studentEvalFormDeadline, Date coopEvalFormDeadline, CoopTermRegistration CTR) {
		if(checkArg(studentEvalFormDeadline)) {
			term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		}
		if(checkArg(coopEvalFormDeadline)) {
			term.setCoopEvalFormDeadline(coopEvalFormDeadline);
		}
		if(checkArg(CTR)) {
			Set<CoopTermRegistration> CTRs = term.getCoopTermRegistration();
			if(!CTRs.contains(CTR)) {
				CTRs.add(CTR);
				term.setCoopTermRegistration(CTRs);
			}
		}
		
		return termRepository.save(term);
	}
	
	@Transactional
	public Term getTerm(String termID) {
		return termRepository.findByTermID(termID);
	}
	
	@Transactional
	public Set<Term> getAllTerms() {
		return toSet(termRepository.findAll());
	}
	//---Term---
	
	//---HELPER METHODS---
	private <T> Set<T> toSet(Iterable<T> iterable){
		Set<T> res = new HashSet<T>();
		for (T t : iterable) {
			res.add(t);
		}
		return res;
	}
	
	private <T> boolean checkArg( T arg ) {
		boolean legal = true;
		if(arg == null) {
			legal = false;
		} else if(arg instanceof String && ((String) arg).trim().length() == 0) {
			legal = false;
		}
		
		return legal;
	}
	
}
