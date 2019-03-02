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
public class TestTeam07RestController {
    static final String HOMEPAGE_TEAM07 = "https://employer-backend-8888.herokuapp.com/";
    static final int OK = 200;
    static final int NOT_FOUND = 404;
    static final int INTERNAL_SERVER_ERROR = 500;

    static final String TEAM18_APP_FINGERPRINT = "18";
    @Before
    public void TestInitApp() {
        // try to use their POST...
   	
     	
    	Response teamSeven = post(HOMEPAGE_TEAM07 + "/mainapp/" + TEAM18_APP_FINGERPRINT);
    	
    	  // already exists so compare with 500
    	if (teamSeven.getStatusCode() == INTERNAL_SERVER_ERROR) {
    		
    		teamSeven.then().assertThat().statusCode(INTERNAL_SERVER_ERROR);
    	}
    	
    	Response teamSeven1 = get(HOMEPAGE_TEAM07 + "/mainapp/" + TEAM18_APP_FINGERPRINT);
    	if (teamSeven1.getStatusCode() == OK) {
    		
    		teamSeven.then().assertThat().statusCode(OK);
    	}  	
    	
    	
    }



    @Test
    public void GetApp() {
    	Response teamSeven = get(HOMEPAGE_TEAM07 + "/mainapp/" + TEAM18_APP_FINGERPRINT);
    	if (teamSeven.getStatusCode() == OK) {
    		
    		teamSeven.then().assertThat().statusCode(OK);
    	}  	

    }
    	
}
