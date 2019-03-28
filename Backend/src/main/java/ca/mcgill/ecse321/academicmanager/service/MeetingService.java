package ca.mcgill.ecse321.academicmanager.service;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
import ca.mcgill.ecse321.academicmanager.exceptions.InvalidEndTimeException;
import ca.mcgill.ecse321.academicmanager.exceptions.NullArgumentException;
import ca.mcgill.ecse321.academicmanager.model.Meeting;
import ca.mcgill.ecse321.academicmanager.model.Student;

/**
 * meeting service class to interface with the database
 * 
 * @author Group-18
 * @version 2.0
 *
 */
@Service
public class MeetingService {
	@Autowired
	MeetingRepository meetingRepository;

	// ---CREATE---
	/**
	 * Creates a new Meeting instance
	 * 
	 * @param meetingID id of the meeting
	 * @param location  location of the meeting
	 * @param details   description of the meeting
	 * @param date      date of the meeting
	 * @param startTime start time of the meeting
	 * @param endTime   end time of the meeting
	 * @return Meeting instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public Meeting create(String meetingID, String location, String details, Date date, Time startTime, Time endTime) {
		if (!Helper.checkArg(meetingID) || !Helper.checkArg(location) || !Helper.checkArg(startTime)
				|| !Helper.checkArg(endTime)) {
			throw new NullArgumentException();
		}

		// check for invalid time constraints
		if (endTime.compareTo(startTime) < 0) {
			throw new InvalidEndTimeException();
		}

		Meeting meeting = new Meeting();
		meeting.setMeetingID(meetingID);
		meeting.setLocation(location);
		meeting.setDate(date);
		meeting.setStartTime(startTime);
		meeting.setEndTime(endTime);

		if (Helper.checkArg(details)) {
			meeting.setDetails(details);
		}

		return meetingRepository.save(meeting);
	}

	// ---UPDATE---
	/**
	 * Updates date of the meeting
	 * 
	 * @param meeting Meeting instance
	 * @param date    new date
	 * @return updated Meeting instance
	 */
	@Transactional
	public Meeting updateDate(Meeting meeting, Date date) {
		if (Helper.checkArg(date)) {
			meeting.setDate(date);
		}

		return meetingRepository.save(meeting);
	}

	/**
	 * Updates start time of the meeting
	 * 
	 * @param meeting   Meeting instance
	 * @param startTime new start time
	 * @return updated Meeting instance
	 */
	@Transactional
	public Meeting updateStartTime(Meeting meeting, Time startTime) {
		if (Helper.checkArg(startTime)) {
			meeting.setStartTime(startTime);
		}

		return meetingRepository.save(meeting);
	}

	/**
	 * Updates end time of the meeting
	 * 
	 * @param meeting Meeting instance
	 * @param endTime new end time
	 * @return updated Meeting instance
	 */
	@Transactional
	public Meeting updateEndTime(Meeting meeting, Time endTime) {
		if (Helper.checkArg(endTime)) {
			meeting.setEndTime(endTime);
		}

		return meetingRepository.save(meeting);
	}

	/**
	 * Updates location of the meeting
	 * 
	 * @param meeting  Meeting instance
	 * @param location new location
	 * @return upated meeting instance
	 */
	@Transactional
	public Meeting updateLocation(Meeting meeting, String location) {
		if (Helper.checkArg(location)) {
			meeting.setLocation(location);
		}

		return meetingRepository.save(meeting);
	}

	/**
	 * Updated description of the meeting
	 * 
	 * @param meeting Meeting instance
	 * @param details description
	 * @return updated Meeting instance
	 */
	@Transactional
	public Meeting updateDetails(Meeting meeting, String details) {
		if (Helper.checkArg(details)) {
			meeting.setDetails(details);
		}

		return meetingRepository.save(meeting);
	}

	/**
	 * Adds a student to the meeting
	 * 
	 * @param meeting Meeting instance
	 * @param student student object to be added
	 * @return updated Meeting instance
	 * @throws NullArgumentException throws exception if input(s) is/are null or
	 *                               invalid
	 */
	@Transactional
	public Meeting addMeetingStudent(Meeting meeting, Student student) {
		if (!Helper.checkArg(student)) {
			throw new NullArgumentException();
		}

		meeting.addStudent(student);

		return meetingRepository.save(meeting);
	}

	// ---GET---
	/**
	 * Gets a Meeting instance based on meetingID
	 * 
	 * @param meetingID id of the meeting
	 * @return Meeting instance
	 */
	@Transactional
	public Meeting get(String meetingID) {
		return meetingRepository.findByMeetingID(meetingID);
	}

	/**
	 * Gets all Meeting instances
	 * 
	 * @return Set of Meeting instances
	 */
	@Transactional
	public Set<Meeting> getAll() {
		return Helper.toSet(meetingRepository.findAll());
	}

	/**
	 * Gets students in meeting instance
	 * 
	 * @param meeting meetingID id of the meeting
	 * @return Set of Student intances in meeting
	 */
	@Transactional
	public Set<Student> getStudents(Meeting meeting) {
		return meeting.getStudent();
	}

	// ---DELETE---
	/**
	 * Deletes a Meeting instance
	 * 
	 * @param meeting Meeting instance to be deleted
	 */
	@Transactional
	public void delete(Meeting meeting) {
		meetingRepository.delete(meeting);
	}
}
