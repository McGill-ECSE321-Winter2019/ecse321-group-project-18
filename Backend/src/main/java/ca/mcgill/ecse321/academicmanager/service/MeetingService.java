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

@Service
public class MeetingService {
	@Autowired
	MeetingRepository meetingRepository;

	// ---CREATE---
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
	@Transactional
	public Meeting updateDate(Meeting meeting, Date date) {
		if (Helper.checkArg(date)) {
			meeting.setDate(date);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting updateStartTime(Meeting meeting, Time startTime) {
		if (Helper.checkArg(startTime)) {
			meeting.setStartTime(startTime);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting updateEndTime(Meeting meeting, Time endTime) {
		if (Helper.checkArg(endTime)) {
			meeting.setEndTime(endTime);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting updateLocation(Meeting meeting, String location) {
		if (Helper.checkArg(location)) {
			meeting.setLocation(location);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting updateDetails(Meeting meeting, String details) {
		if (Helper.checkArg(details)) {
			meeting.setDetails(details);
		}

		return meetingRepository.save(meeting);
	}

	@Transactional
	public Meeting addMeetingStudent(Meeting meeting, Student student) {
		if (!Helper.checkArg(student)) {
			throw new NullArgumentException();
		}

		meeting.addStudent(student);

		return meetingRepository.save(meeting);
	}

	// ---GET---
	@Transactional
	public Meeting get(String meetingID) {
		return meetingRepository.findByMeetingID(meetingID);
	}

	@Transactional
	public Set<Meeting> getAll() {
		return Helper.toSet(meetingRepository.findAll());
	}

	@Transactional
	public Set<Student> getStudents(Meeting meeting) {
		return meeting.getStudent();
	}

	// ---DELETE---
	@Transactional
	public void delete(Meeting meeting) {
		meetingRepository.delete(meeting);
	}
}
