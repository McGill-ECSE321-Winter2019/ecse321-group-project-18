package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.TermRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Term;

/**
 * term service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class TermService {
	@Autowired
	TermRepository termRepository;

	// ---CREATE---
	/**
	 * Creates a new Term instance
	 * 
	 * @param termID                  id of the term
	 * @param termName                name of the term
	 * @param studentEvalFormDeadline student evaluation deadline
	 * @param coopEvalFormDeadline    coop evaluation deadline
	 * @return Term instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
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
	/**
	 * Updates name of Term instance
	 * 
	 * @param term     Term instance
	 * @param termName new name
	 * @return updated Term instance
	 */
	@Transactional
	public Term updateName(Term term, String termName) {
		if (Helper.checkArg(termName)) {
			term.setTermName(termName);
		}

		return termRepository.save(term);
	}

	/**
	 * Updates student evaluation deadline of Term instance
	 * 
	 * @param term                    Term instance
	 * @param studentEvalFormDeadline new deadline
	 * @return updated Term instance
	 */
	@Transactional
	public Term updateStudentEvalDeadline(Term term, Date studentEvalFormDeadline) {
		if (Helper.checkArg(studentEvalFormDeadline)) {
			term.setStudentEvalFormDeadline(studentEvalFormDeadline);
		}

		return termRepository.save(term);
	}

	/**
	 * Updates coop evaluation deadline of Term instance
	 * 
	 * @param term                 Term instance
	 * @param coopEvalFormDeadline new deadline
	 * @return updated Term instance
	 */
	@Transactional
	public Term updateCoopEvalDeadline(Term term, Date coopEvalFormDeadline) {
		if (Helper.checkArg(coopEvalFormDeadline)) {
			term.setCoopEvalFormDeadline(coopEvalFormDeadline);
		}

		return termRepository.save(term);
	}

	// ---GET---
	/**
	 * Gets Term instance based on termID
	 * 
	 * @param termID id of the term
	 * @return Term instance
	 */
	@Transactional
	public Term get(String termID) {
		return termRepository.findByTermID(termID);
	}

	/**
	 * Gets all Term instances
	 * 
	 * @return Set of Term instances
	 */
	@Transactional
	public Set<Term> getAll() {
		return Helper.toSet(termRepository.findAll());
	}

	// ---DELETE---
	/**
	 * Deletes a Term instance
	 * 
	 * @param term Term instance to be deleted
	 */
	@Transactional
	public void delete(Term term) {
		termRepository.delete(term);
	}

	@Transactional
	public boolean exists(String termID) { return termRepository.existsById(termID); }
}
