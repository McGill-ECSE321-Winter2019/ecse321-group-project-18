package ca.mcgill.ecse321.academicmanager.dao;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.academicmanager.model.Meeting;

public interface MeetingRepository extends CrudRepository<Meeting, String> {
	Meeting findByMeetingID(String meetingID);
}
