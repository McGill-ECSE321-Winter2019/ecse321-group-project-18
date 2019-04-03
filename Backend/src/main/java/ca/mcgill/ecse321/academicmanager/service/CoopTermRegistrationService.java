package ca.mcgill.ecse321.academicmanager.service;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.dom4j.IllegalAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;
import ca.mcgill.ecse321.academicmanager.model.Grade;
import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.model.Term;
import ca.mcgill.ecse321.academicmanager.model.TermStatus;

/**
 * coop term registration service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class CoopTermRegistrationService {
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;

	// ---CREATE---
	/**
	 * Creates a new coop term registration instance
	 * 
	 * @param registrationID id of the CoopTermRegistration instance
	 * @param jobID          id of the job
	 * @param status         status of the internship
	 * @param grade          grade of the internship
	 * @param student        student associated with the internship
	 * @param term           the term of the internship
	 * @return CoopTermRegistration instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public CoopTermRegistration create(String registrationID, String jobID, TermStatus status, Grade grade,
			Student student, Term term) {
		if (!Helper.checkArg(registrationID) || !Helper.checkArg(jobID) || !Helper.checkArg(status)
				|| !Helper.checkArg(student) || !Helper.checkArg(term)) {
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
				if (ctrTemp.getTerm().equals(term)) {
					throw new IllegalAddException("Student is already registerd for the given term");
				}
			}
		}

		ctr.setStudent(student);
		ctr.setTerm(term);
		return coopTermRegistrationRepository.save(ctr);
	}

	// ---UPDATE---
	/**
	 * Updates the status of the internship
	 * 
	 * @param ctr    CoopTermRegistration instance
	 * @param status status of the internship
	 * @return updated CoopTermRegistration instance
	 */
	@Transactional
	public CoopTermRegistration updateTermStatus(CoopTermRegistration ctr, TermStatus status) {
		if (Helper.checkArg(status)) {
			ctr.setTermStatus(status);
		}

		return coopTermRegistrationRepository.save(ctr);
	}

	/**
	 * Updates the grade of the internship
	 * 
	 * @param ctr   CoopTermRegistration instance
	 * @param grade grade of the internship
	 * @return updated CoopTermRegistration instance
	 */
	@Transactional
	public CoopTermRegistration updateTermGrade(CoopTermRegistration ctr, Grade grade) {
		if (Helper.checkArg(grade)) {
			ctr.setGrade(grade);
		}

		return coopTermRegistrationRepository.save(ctr);
	}

	// ---GET---
	/**
	 * Gets a CoopTermRegistration instance based on registrationID
	 * 
	 * @param registrationID id of the CoopTermRegistration instance
	 * @return CoopTermRegistration instance
	 */
	@Transactional
	public CoopTermRegistration get(String registrationID) {
		return coopTermRegistrationRepository.findByRegistrationID(registrationID);
	}

	/**
	 * Gets all CoopTermRegistration instances
	 * 
	 * @return Set of CoopTermRegistration instances
	 */
	@Transactional
	public Set<CoopTermRegistration> getAll() {
		return Helper.toSet(coopTermRegistrationRepository.findAll());
	}

	/**
	 * Gets CoopTermRegistration instances based on studentID
	 * 
	 * @param studentID id of a student
	 * @return Set of CoopTermRegistration instances
	 */
	@Transactional
	public Set<CoopTermRegistration> getByStudentID(String studentID) {
		Set<CoopTermRegistration> ret = new HashSet<CoopTermRegistration>();

		Set<CoopTermRegistration> ctrs = Helper.toSet(coopTermRegistrationRepository.findAll());
		for (CoopTermRegistration ctr : ctrs) {
			if (ctr.getStudent().getStudentID().equals(studentID))
				ret.add(ctr);
		}
		return ret;
	}

	/**
	 * Gets CoopTermRegistration instances based on termName
	 * 
	 * @param termName name of a term
	 * @return Set of CoopTermRegistration instances
	 */
	@Transactional
	public Set<CoopTermRegistration> getByTermName(String termName) {
		Set<CoopTermRegistration> ret = new HashSet<CoopTermRegistration>();
		Set<CoopTermRegistration> ctrs = Helper.toSet(coopTermRegistrationRepository.findAll());
		for (CoopTermRegistration ctr : ctrs) {
			if (ctr.getTerm().getTermName().equals(termName))
				ret.add(ctr);
		}
		return ret;
	}

	/**
	 * Gets CoopTermRegistration instances based on termName and studentID
	 * 
	 * @param termName termName name of a term
	 * @param studentID studentID id of a student
	 * @return Set of CoopTermRegistration instances
	 */
	@Transactional
	public Set<CoopTermRegistration> getByTermNameAndStudentID(String termName, String studentID) {
		Set<CoopTermRegistration> ret = new HashSet<CoopTermRegistration>();
		Set<CoopTermRegistration> ctrs = Helper.toSet(coopTermRegistrationRepository.findAll());
		for (CoopTermRegistration ctr : ctrs) {
			if (ctr.getTerm().getTermName().equals(termName) && ctr.getStudent().getStudentID().equals(studentID))
				ret.add(ctr);
		}
		return ret;
	}

	// ---DELETE---
	/**
	 * Deletes a coopTermRegistration instance
	 * 
	 * @param coopTermRegistration coopTermRegistration instance to delete
	 */
	@Transactional
	public void delete(CoopTermRegistration coopTermRegistration) {
		coopTermRegistrationRepository.delete(coopTermRegistration);
	}
}
