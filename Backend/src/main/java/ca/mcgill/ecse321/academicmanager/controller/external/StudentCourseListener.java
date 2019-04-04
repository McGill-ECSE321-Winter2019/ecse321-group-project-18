package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.dto.CourseDto;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

/**
 * Gets all courses from the Student team.
 */
public class StudentCourseListener extends Listener {
    public static final String GET_ALL_COURSES_URL = "http://cooperator-backend-0000.herokuapp.com/coopCourseOfferings";
    private HashMap<String, CourseDto> courses = new HashMap<>();
    public static final int DEFAULT_COOPERATOR_ID = 1;
    @GetMapping(value = {"/courses/sync", "/courses/sync"})
    @Override
    protected String trigger() {
        return super.mainProceudure(GET_ALL_COURSES_URL);
    }

    @Override
    protected void interpretRequest(String jsonString) {

    }

    @Override
    protected void handleDependencies() {

    }
}
