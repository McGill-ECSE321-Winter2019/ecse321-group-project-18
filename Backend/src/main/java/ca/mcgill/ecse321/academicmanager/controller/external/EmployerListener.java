package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.service.CooperatorService;
import ca.mcgill.ecse321.academicmanager.service.StudentService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;


/**
 * An intermediate class capturing the JSON object on the Student team.
 * This class is in charge of making the data in the Student team and the AcademicManger team compatible.
 * @author Bach Tran
 * */
class ExternalStudentDto {
    protected String fullName;
    protected String studentID;
    protected String firstName;
    protected String lastName;

    /**
     * Creates a new ExternalStudentDto object, this object captures relevant data on the Student team.
     * @param fullName the full name of the Student. This will be automatically split into firstName and lastName
     * @param studentID the unique id of the Student.
     *
     */
    public ExternalStudentDto(String studentID, String fullName) {
        this.fullName = fullName;
        this.studentID = studentID;
        this.firstName = nameSeparator(this.fullName)[0];
        this.lastName = nameSeparator(this.fullName)[1];
    }

    public ExternalStudentDto(String studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "ID = " + studentID + " fullName = " + fullName;
    }

    /**
     * Background: the fullName data on the Student team has only a single field, attaching both the first fullName and
     * the last fullName of the student. While on the Academic Manger's side, we separate those two attributes.
     * This method provides an adaptation to this difference.
     * @param name the raw fullName
     * @return a String array contains exactly two elements: the first fullName and the last fullName.
     * */
    private static String[] nameSeparator(String name) {
        String[] splitted = name.split(" ");
        // assumes the student doesn't have a last name.
        if (splitted.length < 2) {
            String first_name = splitted[0];
            String last_name = "<No_last_name>";
            splitted = new String[2];
            splitted[0] = first_name;
            splitted[1] = last_name;
        }
        // name has more than three words, in this case, the first word will be the first name,
        // the rest will become the last name.
        else if (splitted.length > 2) {
            String first_name = splitted[0];
            String last_name = "";
            for (int i = 1; i < splitted.length; i++) {
                last_name += splitted[i];
            }
            splitted = new String[2];
            splitted[0] = first_name;
            splitted[1] = last_name;
        }
        return splitted;
    }
}
/**
 * Main class handle the synchronization with the Student team's data.
 * @author Bach Tran
 * */
@CrossOrigin(origins="*")
@RestController
public class EmployerListener extends Listener {
    public static final int DEFAULT_COOPERATOR_ID = 1;
    public static final String GET_URL = "https://employer-backend-8888.herokuapp.com/mainapp/1/getstudents";
    private HashMap<String, ExternalStudentDto> students = new HashMap<>();
    @Autowired
    private StudentService studentService;
    @Autowired
    private CooperatorService cooperatorService;
    /**
     * Main response method for the HTTP GET request /students/sync.
     * This method aims to get all Student from the Student team's database.
     * @return a message to the REST Client
     * @author Bach Tran
     * */
    @GetMapping(value = { "/students/sync", "/students/sync/" })
    @ResponseBody
    protected String trigger() {
        return super.trigger(GET_URL);
    }

    /**
     * Helper method: convert a jsonArray of students to a Java.util.ArrayList of ExternalStudentDto
     * @return a List of ExternalStudentDto
     * */
    private static HashMap<String, ExternalStudentDto> jsonArrayToList(JsonArray jsonArray) {
        HashMap<String, ExternalStudentDto> result = new HashMap<>();
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                // extracts necessary information
                JsonObject intermediateObject = jsonElement.getAsJsonObject();
                String studentID = intermediateObject.get("studentID").toString();
                String name = intermediateObject.get("name").getAsString();
                // persists into HashMap
                result.put(studentID, new ExternalStudentDto(studentID, name));
            }
        }
        return result;
    }

    @Override
    protected void interpretRequest(String jsonString) {
        // parse raw data
        JsonParser parser = new JsonParser();
        JsonArray jsonStudents = parser.parse(jsonString).getAsJsonArray();
        // parse to Java Objects
        students = jsonArrayToList(jsonStudents);
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
