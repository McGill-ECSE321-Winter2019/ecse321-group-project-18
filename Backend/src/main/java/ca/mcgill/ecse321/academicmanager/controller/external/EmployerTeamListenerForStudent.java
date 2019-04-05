package ca.mcgill.ecse321.academicmanager.controller.external;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;



/**
 * Main class handle the synchronization with the Student team's data.
 * @author Bach Tran
 * */
@CrossOrigin(origins="*")
@RestController
class EmployerTeamListenerForStudent extends ListenerForStudent {
    public static final String GET_URL = "https://employer-backend-8888.herokuapp.com/mainapp/1/getstudents";
    /**
     * Main response method for the HTTP GET request /students/sync.
     * This method aims to get all Student from the Student team's database.
     * @return a message to the REST Client
     * @author Bach Tran
     * */
    @GetMapping(value = { "/students/sync", "/students/sync/" })
    @ResponseBody
    @Override
    protected String trigger() {
        return super.mainProcedure(GET_URL);
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
    protected void interpretRequest(String jsonString) throws RuntimeException {
        // parse raw data
        JsonParser parser = new JsonParser();
        JsonArray jsonStudents = parser.parse(jsonString).getAsJsonArray();
        // parse to Java Objects
        students = jsonArrayToList(jsonStudents);
    }
}
