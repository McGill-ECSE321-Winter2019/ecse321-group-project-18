package ca.mcgill.ecse321.academicmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CooperatorRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Cooperator;

/**
 * cooperator service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class CooperatorService {
	@Autowired
	CooperatorRepository cooperatorRepository;

	// ---CREATE---
	/**
	 * Creates a new Cooperator instance
	 * 
	 * @param id integer id of the Cooperator instance
	 * @return Cooperator instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public Cooperator create(Integer id) {
		if (!Helper.checkArg(id)) {
			throw new NullArgumentException();
		}
		Cooperator c = new Cooperator();
		c.setId(id);

		return cooperatorRepository.save(c);
	}

	// ---GET---
	/**
	 * Gets a Cooperator instance based on id
	 * 
	 * @param id integer id of the Cooperator instance
	 * @return Cooperator instance
	 */
	@Transactional
	public Cooperator get(Integer id) {
		return cooperatorRepository.findByid(id);
	}

	/**
	 * Gets all Cooperator instances
	 * 
	 * @return Set of Cooperator instances
	 */
	@Transactional
	public Set<Cooperator> getAll() {
		return Helper.toSet(cooperatorRepository.findAll());
	}

	// ---DELETE---
	/**
	 * Deletes a Cooperator instance
	 * 
	 * @param cooperator Cooperator instance to delete
	 */
	@Transactional
	public void delete(Cooperator cooperator) {
		cooperatorRepository.delete(cooperator);
	}

	/**
	 * Checks if there exists a Cooperator with the given id.
	 * @author Bach Tran
	 * @param id the id of the Cooperator to be checked for existance.
	 * @return true if there exists a Cooperator with the given ID
	 * */
	@Transactional
	public boolean exists(int id) {
		return cooperatorRepository.existsById(id);
	}
}
