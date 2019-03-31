package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.service.StudentService;
import com.google.gson.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



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

    public ExternalStudentDto(String name, String studentID) {
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
    private List<ExternalStudentDto> students = new ArrayList<>();
    @Autowired
    private StudentService studentService;
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
        System.out.println(responseCode);
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
        return responseMessage + "\n" + students.toString();
    }
    /**
     * Helper method: convert a jsonArray of students to a Java.util.ArrayList of ExxternalStudentDto
     * @return a List of ExternalStudentDto
     * */
    private List<ExternalStudentDto> jsonArrayToList(JsonArray jsonArray) {
        List<ExternalStudentDto> result = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                JsonObject intermediateObject = jsonElement.getAsJsonObject();
                result.add(new ExternalStudentDto(intermediateObject.get("studentID").toString(),
                        intermediateObject.get("name").toString()));
            }
        }
        return result;
    }
    /**
     * Puts the received data into the Backend's database.
     * @author Bach Tran
     * */
    private void persist() {
        
    }
}
