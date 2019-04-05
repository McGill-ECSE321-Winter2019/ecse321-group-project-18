package ca.mcgill.ecse321.academicmanager.controller.external;

import java.util.Random;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin (origins = "*")
@RestController
class StudentTeamListenerForCourse extends ListenerForCourse {
    public static final String
            GET_ALL_COOP_OFFERINGS_URL = "http://cooperator-backend-0000.herokuapp.com/coopCourseOfferings";
    @GetMapping(value = {"/courses/sync", "/courses/sync"})
    @ResponseBody
    @Override
    protected String trigger() {
        return super.mainProcedure(GET_ALL_COOP_OFFERINGS_URL);
    }

    @Override
    protected void interpretRequest(String jsonString) throws RuntimeException {
        JsonParser parser = new JsonParser();
        if (!parser.parse(jsonString).isJsonObject()) {
            throw new RuntimeException("Cannot interpret!");
        }
        JsonArray courseOfferings = extractJsonArray(jsonString, parser);
        // traverse the array to find the courses and the terms
        for (JsonElement courseOffering : courseOfferings) {
            convertToInternalDto(courseOffering);
        }
    }

    private JsonArray extractJsonArray(String jsonString, JsonParser parser) {
        // break the complex json down to an array of CourseOfferings
        JsonObject complex = (JsonObject) parser.parse(jsonString).getAsJsonObject().get("_embedded");
        return complex.get("coopCourseOfferings").getAsJsonArray();
    }

    private void convertToInternalDto(JsonElement courseOffering) {
        JsonObject courseOfferingObject = courseOffering.getAsJsonObject();
        // easy: get "term" & "year" --> termName
        String externalTerm = courseOfferingObject.get("term").getAsString();
        String externalYear = courseOfferingObject.get("year").getAsString();
        String internalId = ExternalTermDto.generateId(externalTerm, externalYear);
        // adding to a HashSet can guarantee uniqueness
        terms.add(new ExternalTermDto(internalId, externalTerm, externalYear));

        // hard: get course ID & generate name and rank
        String link = courseOfferingObject.get("_links").getAsJsonObject().get("self").getAsJsonObject()
                .get("href").getAsString();
        String id = link.trim().substring(link.lastIndexOf('/') + 1);
        // For now: the Student team's course rank is not available, so we will
        // randomly generate a number from 1 to 10
        courses.add(new ExternalCourseDto(id, internalId, id, new Random().nextInt(10)));
    }
}
