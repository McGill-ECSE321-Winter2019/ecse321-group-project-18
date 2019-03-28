package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.TermRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Term;

@Service
public class TermService {
	@Autowired
	TermRepository termRepository;

	// ---CREATE---
	@Transactional
	public Term create(String termID, String termName, Date studentEvalFormDeadline, Date coopEvalFormDeadline) {
		if (!Helper.checkArg(termID)) {
			throw new NullArgumentException();
		}

		Term term = new Term();
		term.setTermID(termID);
		term.setTermName(termName);
		term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		term.setCoopEvalFormDeadline(coopEvalFormDeadline);

		return termRepository.save(term);
	}

	// ---UPDATE---
	@Transactional
	public Term updateName(Term term, String termName) {
		if (Helper.checkArg(termName)) {
			term.setTermName(termName);
		}

		return termRepository.save(term);
	}

	@Transactional
	public Term updateStudentEvalDeadline(Term term, Date studentEvalFormDeadline) {
		if (Helper.checkArg(studentEvalFormDeadline)) {
			term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		}

		return termRepository.save(term);
	}

	@Transactional
	public Term updateCoopEvalDeadline(Term term, Date coopEvalFormDeadline) {
		if (Helper.checkArg(coopEvalFormDeadline)) {
			term.setCoopEvalFormDeadline(coopEvalFormDeadline);
		}

		return termRepository.save(term);
	}

	// ---GET---
	@Transactional
	public Term get(String termID) {
		return termRepository.findByTermID(termID);
	}

	@Transactional
	public Set<Term> getAll() {
		return Helper.toSet(termRepository.findAll());
	}

	// ---DELETE---
	@Transactional
	public void delete(Term term) {
		termRepository.delete(term);
	}
}
