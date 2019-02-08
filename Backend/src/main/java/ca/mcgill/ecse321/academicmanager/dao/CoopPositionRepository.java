package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.CoopPosition;

public interface CoopPositionRepository extends CrudRepository<CoopPosition, String> {
	CoopPosition findByJobID(String jobID);
}
