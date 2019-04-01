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
import java.util.Set;


/**
 * This class is an intermediate class capturing the JSON object on the Student team.
 * @author Bach Tran
 * */
class ExternalStudentDto {
    protected String name;
    protected String studentID;
//    protected String email;
//    protected String major;
//    protected String phone;
//    protected String coopPosition;

    public ExternalStudentDto(String studentID, String name) {
        this.name = name;
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "ID = " + studentID + " name = " + name;
    }
}

@RestController
public class StudentClientController {
    public static final String GET_URL = "https://employer-backend-8888.herokuapp.com/mainapp/1/getstudents";
    private HashMap<String, ExternalStudentDto> students = new HashMap<>();
    @Autowired
    private StudentService studentService;
    @Autowired
    private CooperatorService cooperatorService;
    /**
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
     * Main response method for the HTTP GET request /students/sync
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
     * Background: the name data on the Student team has only a single field, attaching both the first name and
     * the last name of the student. While on the Academic Manger's side, we separate those two attributes.
     * This method provides an adaptation to this difference.
     * @param name the raw name
     * @return a String array contains exactly two elements: the first name and the last name.
     * */
    private static String[] nameSeperator(String name) {
        String[] splitted = name.split(" ");
        if (splitted.length < 2) {
            String first_name = splitted[0];
            String last_name = "<No_last_name>";
            splitted = new String[2];
            splitted[0] = first_name;
            splitted[1] = last_name;
        } else if (splitted.length > 2) {
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
                studentService.updateFirstName(student ,nameSeperator(students.get(externalStudentID).name)[0]);
                studentService.updateLastName(student, nameSeperator(students.get(externalStudentID).name)[1]);
            } else {
                // create new, non-problematic student
                studentService.create(externalStudentID,
                        nameSeperator(students.get(externalStudentID).name)[0],
                        nameSeperator(students.get(externalStudentID).name)[1],
                        cooperatorService.get(DEFAULT_COOPERATOR_ID));
            }
        }
        System.out.println("Updated data in the backend database!");
    }
}
