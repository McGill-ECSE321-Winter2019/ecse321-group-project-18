package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.CoopTermRegistration;

public interface CoopTermRegistrationRepository extends CrudRepository<CoopTermRegistration, String> {
	CoopTermRegistration findByRegistrationID(String registrationID);
}
