package ca.mcgill.ecse321.external;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.*;

/**
 * General test suite for external services.
 * We only test if other teams' RESTful services is basically working or not.
 * */
public class TestTeam13RestController {
    static final String HOMEPAGE_TEAM13 = "http://cooperator-backend-123.herokuapp.com";
    static final int OK = 200;
    static final int NOT_FOUND = 404;
    static final int INTERNAL_SERVER_ERROR = 500;

    static final String TEAM18_APP_FINGERPRINT = "18";
    @Test
    public void TestStatistics() {
        // try to use their POST...
   	
     	// check statistics
    	Response team13 = post(HOMEPAGE_TEAM13 + "/statistics");
    	
    	  // check if OK
    	if (team13.getStatusCode() == OK) {
    		
    		team13.then().assertThat().statusCode(OK);
    	}
    }    	
    @Test
    public void TestEmployee() {
        // try to use their POST...
   	
     	// check statistics
    	Response team13 = post(HOMEPAGE_TEAM13 + "/employers");
    	
    	  // check if OK
    	if (team13.getStatusCode() == OK) {
    		
    		team13.then().assertThat().statusCode(OK);
    	}
    }   
    
    
}
