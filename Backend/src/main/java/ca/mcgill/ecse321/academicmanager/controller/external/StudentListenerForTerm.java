package ca.mcgill.ecse321.academicmanager.controller.external;

import ca.mcgill.ecse321.academicmanager.dto.TermDto;
import ca.mcgill.ecse321.academicmanager.service.CourseService;
import ca.mcgill.ecse321.academicmanager.service.TermService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashSet;

/**
 * Gets all courses from the Student team.
 * This class will also try to create term if not existed.
 */
@RestController
@CrossOrigin(origins = "*")
public class StudentListenerForTerm extends Listener {
    public static final String GET_ALL_COURSES_URL = "http://cooperator-backend-0000.herokuapp.com/coopCourseOfferings";
    private HashSet<ExternalTermDto> terms = new HashSet<>();

    @Autowired
    TermService termService;

    @GetMapping(value = {"/terms/sync", "/terms/sync"})
    @Override
    protected String trigger() {
        return super.mainProceudure(GET_ALL_COURSES_URL);
    }

    @Override
    protected void interpretRequest(String jsonString) {
        JsonParser parser = new JsonParser();
        if (!parser.parse(jsonString).isJsonObject()) {
            System.out.println("Cannot interpret!");
            return;
        }
        // break the complex json down to an array of CourseOfferings
        JsonObject complex = (JsonObject) parser.parse(jsonString).getAsJsonObject().get("_embedded");
        JsonArray courseOfferings = complex.get("coopCourseOfferings").getAsJsonArray();
        // traverse the array to find the courses and the terms
        for (JsonElement courseOffering : courseOfferings) {
            JsonObject courseOfferingObject = courseOffering.getAsJsonObject();
            // easy: get "term" & "year" --> termName
            String externalTerm = courseOfferingObject.get("term").getAsString();
            String externalYear = courseOfferingObject.get("year").getAsString();
            String internalId = ExternalTermDto.generateId(externalTerm, externalYear);
            // adding to a HashSet can guarantee uniqueness
            terms.add(new ExternalTermDto(internalId, externalTerm, externalYear));
        }
    }

    @Override
    protected void handleDependencies() {
        // No dependencies for Term!
    }

    @Override
    protected void persist() {
        super.persist();
        // persist in database.
        for (ExternalTermDto externalTerm : terms) {
            if (!termService.exists(externalTerm.getTermID()))
            {
                termService.create(
                    externalTerm.getTermID(),
                    externalTerm.getTermName(),
                    externalTerm.getDate1(),
                    externalTerm.getDate2()
            );}
        }
        // shouldn't wipe anything...
        System.out.println("Updated data to the database!");
    }
}

/**
 * Helper class to contain the Term information from the external subsystem.
 * @author Bach Tran
 */
class ExternalTermDto extends TermDto {
    /**
     * Creates a new ExernalTermDto object.
     * This constructor is specifically designed for Team 04's data.
     * @param termName the name of the term.
     * @param date1 the deadline for the students to submit their evaluation form.
     * @param date2 the deadline for the employers to submit their evaluation form.
     */
    ExternalTermDto(String termID, String termName, String date1, String date2) {
        setTermID(termID);
        setTermName(termName);
        setDate1(Date.valueOf(date1));
        setDate2(Date.valueOf(date2));
    }

    /**
     * Creates a new Term.
     * Assuming that the deadline for students to submit their evaluation form
     * is on the 15th of March, and the deadline for the Employer is 30th of April.
     * @param termID the specific term ID.
     * @param termName the name of the term.
     * @param year the year of the term.
     */
    ExternalTermDto(String termID, String termName, String year) {
        setTermID(termID);
        setTermName(termName + " " + year);
        setDate1(Date.valueOf(year + "-03-15"));
        setDate2(Date.valueOf(year + "-04-30"));
    }

    /**
     * Creates a termName suitable for the internal viewpoint.
     * @param termName name of the term, correspond to the "term" attribute.
     * @param year year of the offer, correspond to the "year" attribute.
     * @return term full name, compatible with the data attribute in the AcademicManger's viewpoint.
     */
    static String termNameAdder(String termName, String year) {
        return termName.trim() + year.trim();
    }
    static String generateId(String termName, String year) {
        if (termName.isEmpty()) {
            // try to randomize a unique id
            return "NONAME" + year + System.currentTimeMillis();
        }
        return termName.toLowerCase() + year;
    }

    /**
     * We rewrite the equals method to ensure object uniqueness.
     * Compares if two ExternalStudentDto objects are equal.
     * @param o the other object.
     * @return true if two objects are equal.
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ExternalTermDto)) {
            return false;
        }
        ExternalTermDto object = (ExternalTermDto) o;
        return this.getTermID() == object.getTermID();
    }
}
