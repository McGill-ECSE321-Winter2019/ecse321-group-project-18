package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.service.TermService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gets all courses from the Student team.
 * This class will also try to create term if not existed.
 */
@RestController
@CrossOrigin(origins = "*")
class StudentTeamListenerForTerm extends ListenerForTerm {
    public static final String GET_ALL_COURSES_URL = "http://cooperator-backend-0000.herokuapp.com/coopCourseOfferings";

    @GetMapping(value = {"/terms/sync", "/terms/sync"})
    @ResponseBody
    @Override
    protected String trigger() {
        return super.mainProcedure(GET_ALL_COURSES_URL);
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
    }

}

