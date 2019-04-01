package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.model.Student;
import ca.mcgill.ecse321.academicmanager.service.CooperatorService;
import ca.mcgill.ecse321.academicmanager.service.StudentService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
//    protected String email;
//    protected String major;
//    protected String phone;
//    protected String coopPosition;
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
@RestController
public class StudentClientController {
    public static final String GET_URL = "https://employer-backend-8888.herokuapp.com/mainapp/1/getstudents";
    private HashMap<String, ExternalStudentDto> students = new HashMap<>();
    @Autowired
    private StudentService studentService;
    @Autowired
    private CooperatorService cooperatorService;
    /**
     * Low-level method to open a connection to the Student team's RESTful calls.
     * source: https://www.journaldev.com/7148/java-httpurlconnection-example-java-http-request-get-post
     * @author Bach Tran
     * @return a String in JSON format, or an empty String if request has an error.
     * */
    public String sendGETStudents() throws IOException {
        // prepare to connect
        URL getURL = new URL(GET_URL);
        HttpURLConnection connection = (HttpURLConnection) getURL.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        String responseString = "";
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            responseString = response.toString();
        }
        return responseString;
    }
    /**
     * Main response method for the HTTP GET request /students/sync.
     * This method aims to get all Student from the Student team's database.
     * @return a message to the REST Client
     * @author Bach Tran
     * */
    @GetMapping(value = { "/students/sync", "/students/sync/" })
    public String getAllStudents() {
        String responseMessage = "Sync complete!";
        String responseString = "";
        // send HTTP GET request
        try {
            responseString = sendGETStudents();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            responseMessage = e.getMessage();
        }
        // parse raw data
        JsonParser parser = new JsonParser();
        JsonArray jsonStudents = parser.parse(responseString).getAsJsonArray();
        // parse to Java Objects
        students = jsonArrayToList(jsonStudents);

        // finally, persist the new data to the backend database
        persist();
        return responseMessage + "\n" + students.toString();
    }
    /**
     * Helper method: convert a jsonArray of students to a Java.util.ArrayList of ExxternalStudentDto
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
    /**
     * Puts the received data into the Backend's database.
     * Contains the logic (instruction) on how to appropriately inject data to the database.
     * After this method, the database of the AcademicManager will be the same to that of the Student team.
     * @author Bach Tran
     * */
    private void persist() {
        // preliminary work: handle the cooperator
        final int DEFAULT_COOPERATOR_ID = 1;
        if (!cooperatorService.exists(DEFAULT_COOPERATOR_ID)) {
            cooperatorService.create(DEFAULT_COOPERATOR_ID);
        }
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
