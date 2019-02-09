package ca.mcgill.ecse321.academicmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.academicmanager.dao.CoopTermRegistrationRepository;
import ca.mcgill.ecse321.academicmanager.dao.CourseRepository;
import ca.mcgill.ecse321.academicmanager.dao.FormRepository;
import ca.mcgill.ecse321.academicmanager.dao.MeetingRepository;
import ca.mcgill.ecse321.academicmanager.dao.StudentRepository;
import ca.mcgill.ecse321.academicmanager.dao.TermRepository;

@Service
public class CooperatorService {
	@Autowired
	StudentRepository studentRepository;
	
	@Autowired
	CoopTermRegistrationRepository coopTermRegistrationRepository;
	
	@Autowired
	FormRepository formRepository;
	
	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	MeetingRepository meetingRepository;
	
	@Autowired
	TermRepository termRepository;
}
