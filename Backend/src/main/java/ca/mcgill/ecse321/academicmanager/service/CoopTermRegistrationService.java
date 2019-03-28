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

@Service
public class CoopTermRegistrationService {
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;

	// ---CREATE---
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
	@Transactional
	public CoopTermRegistration updateTermStatus(CoopTermRegistration ctr, TermStatus status) {
		if (Helper.checkArg(status)) {
			ctr.setTermStatus(status);
		}

		return coopTermRegistrationRepository.save(ctr);
	}

	@Transactional
	public CoopTermRegistration updateTermGrade(CoopTermRegistration ctr, Grade grade) {
		if (Helper.checkArg(grade)) {
			ctr.setGrade(grade);
		}

		return coopTermRegistrationRepository.save(ctr);
	}

	// ---GET---
	@Transactional
	public CoopTermRegistration get(String registrationID) {
		return coopTermRegistrationRepository.findByRegistrationID(registrationID);
	}

	@Transactional
	public Set<CoopTermRegistration> getAll() {
		return Helper.toSet(coopTermRegistrationRepository.findAll());
	}

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
	@Transactional
	public void delete(CoopTermRegistration coopTermRegistration) {
		coopTermRegistrationRepository.delete(coopTermRegistration);
	}
}
