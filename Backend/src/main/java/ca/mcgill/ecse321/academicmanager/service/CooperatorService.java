package ca.mcgill.ecse321.academicmanager.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CooperatorRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Cooperator;

@Service
public class CooperatorService {
	@Autowired
	CooperatorRepository cooperatorRepository;

	// ---CREATE---
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
	@Transactional
	public Cooperator get(Integer id) {
		return cooperatorRepository.findByid(id);
	}

	@Transactional
	public Set<Cooperator> getAll() {
		return Helper.toSet(cooperatorRepository.findAll());
	}

	// ---DELETE---
	@Transactional
	public void delete(Cooperator cooperator) {
		cooperatorRepository.delete(cooperator);
	}
}
