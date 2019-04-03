package ca.mcgill.ecse321.academicmanager.controller;

import ca.mcgill.ecse321.academicmanager.service.AcademicManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * This class stands between the RESTful services and the corresponding Java Services.
 * @see ca.mcgill.ecse321.academicmanager.service.AcademicManagerService
 * @author Bach Tran
 * @since Sprint 4
 * */
@CrossOrigin(origins = "*")
@RestController
public class DeveloperRestController {
    public static final String SUCCESS = "operation executed!";
    public static final String FAILED = "operation failed!";
    @Autowired
    private AcademicManagerService academicManagerService;
    /**
     * Delete all instances existing in the database.
     * The database will have no instances after execution.
     * Warning: this method wipes out all instances, and there's no way to get them back.
     * Please think twice before calling.
     * @author Bach Tran
     * @return responseMessage a String announcing method's completion.
     * */
    @GetMapping(value = {"/developers/wipe", "/developers/wipe/"})
    public String wipeDatabase() {
        academicManagerService.clearDatabase();
        return SUCCESS;
    }
}
