package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.Cooperator;

public interface CooperatorRepository extends CrudRepository<Cooperator, Integer> {
	Cooperator findByid(Integer id);
}
