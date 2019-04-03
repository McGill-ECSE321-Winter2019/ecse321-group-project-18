package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.service.CooperatorService;
import ca.mcgill.ecse321.academicmanager.service.StudentService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;

/**
 * Main class handles the Student's team (team 07) data
 */
@CrossOrigin(origins = "*")
@RestController
public class StudentListener extends Listener{

    public static final String GET_ALL_STUDENTS_URL = "https://cooperator-backend-0000.herokuapp.com/students/";
    public static final int DEFAULT_COOPERATOR_ID = 1;

    private HashMap<String, ExternalStudentDto> students = new HashMap<>();

    @Autowired
    private CooperatorService cooperatorService;
    @Autowired
    private StudentService studentService;

    @GetMapping(value = { "/students/sync04", "/students/sync04/" })
    @ResponseBody
    protected String trigger() {
        return super.trigger(GET_ALL_STUDENTS_URL);
    }

    @Override
    protected void interpretRequest(String jsonString) {
        JsonParser parser = new JsonParser();
        if (!parser.parse(jsonString).isJsonObject()) {
            System.out.println("Cannot interpret");
            return;
        }
        JsonObject complex = (JsonObject) parser.parse(jsonString);
        complex = (JsonObject) (JsonObject) complex.get("_embedded");
        JsonArray jsonStudents = complex.getAsJsonArray("students");
        for (JsonElement jsonStudent : jsonStudents) {
            // easy: get first, lastName
            JsonObject jsonObject = jsonStudent.getAsJsonObject();
            String firstName = jsonObject.get("firstName").getAsString();
            String lastName = jsonObject.get("lastName").getAsString();
            // extremely hard: get student id
            String link = jsonObject.get("_links").getAsJsonObject().get("self").getAsJsonObject()
                    .get("href").getAsString();
            String id = link.trim().substring(link.lastIndexOf('/') + 1);
            students.put(id ,new ExternalStudentDto(id, firstName, lastName));
        }
    }

    /**
     * reference: https://www.tutorialspoint.com/java/java_regular_expressions.htm
     * @param messyString
     * @return
     */
    private String studentIdFinder(String messyString) {
        // had to do it the hard way...
        return messyString.substring(messyString.lastIndexOf('/') + 1);
    }

    @Override
    protected void handleDependencies() {
        // dependency: Cooperator
        if (!cooperatorService.exists(DEFAULT_COOPERATOR_ID)) {
            cooperatorService.create(DEFAULT_COOPERATOR_ID);
        }
    }

    @Override
    protected void persist() {
        // handle dependencies
        super.persist();
        // deletes all obsolete student data
        for (Student academicMangerStudent : studentService.getAll()) {
            // deletes all obsolete students from the AcademicManager's database
            if (!students.containsKey(academicMangerStudent.getStudentID())) {
                studentService.delete(academicMangerStudent.getStudentID());
            }
        }
        // persists new student to the database
        for (String externalStudentID : students.keySet()) {
            if (studentService.exists(externalStudentID)) {
                // updates the existing student
                Student student = studentService.get(externalStudentID);
                studentService.updateFirstName(student, students.get(externalStudentID).firstName);
                studentService.updateLastName(student, students.get(externalStudentID).lastName);
            } else {
                // create new, non-problematic student
                studentService.create(externalStudentID,
                        students.get(externalStudentID).firstName,
                        students.get(externalStudentID).lastName,
                        cooperatorService.get(DEFAULT_COOPERATOR_ID));
            }
        }
        // optional: return message to console.
        System.out.println("Updated data in the backend database!");
    }
}
