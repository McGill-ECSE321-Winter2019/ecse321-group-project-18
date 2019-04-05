package ca.mcgill.ecse321.academicmanager.controller.external;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * This class specifically works for the Student viewpoint (team 04).
 * It specifically get all students and add it straight to the backend using our services.
 * @author Bach Tran
 * @since Sprint 4
 */
@CrossOrigin(origins = "*")
@RestController
class StudentTeamListenerForStudent extends ListenerForStudent {

    public static final String GET_ALL_STUDENTS_URL = "https://cooperator-backend-0000.herokuapp.com/students/";

    @Override
    protected void interpretRequest(String jsonString) throws RuntimeException {
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
    @GetMapping(value = {"/students/sync", "/students/sync/"})
    @ResponseBody
    @Override
    protected String trigger() {
        return super.mainProcedure(GET_ALL_STUDENTS_URL);
    }
}
