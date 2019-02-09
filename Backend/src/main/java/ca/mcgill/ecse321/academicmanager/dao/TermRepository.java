package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.Term;

public interface TermRepository extends CrudRepository<Term, String> {
	Term findByTermID(String termID);
}
