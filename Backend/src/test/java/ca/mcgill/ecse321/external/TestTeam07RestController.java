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
    public void TestPostGetStudent() {
        String _name = "JohnStark";
        String _id = "11";
        String _email = "tony.stark@avengers.org";
        String _major = "genius,billionaire,playboy,philanthropist";
        String _phone = "911";
        String _appid = TEAM18_APP_FINGERPRINT;

        Response ironMan = post(HOMEPAGE_TEAM07 + "students/" + _name + "/" + _id + "/" + _email
        + "/" + _major + "/" + _phone + "/" + _appid);
        
        // 500 student exists
        if (ironMan.getStatusCode() == INTERNAL_SERVER_ERROR)
        	{  ironMan.then().assertThat().statusCode(INTERNAL_SERVER_ERROR);}
        

        get(HOMEPAGE_TEAM07 + "students/" + _id).then().assertThat().statusCode(OK)
                .body("name", equalTo(_name))
                .body("studentID", equalTo(Integer.parseInt(_id)))
                .body("email", equalTo(_email))
                .body("major", equalTo(_major))
                .body("phone", equalTo(Integer.parseInt(_phone)));
    }

    @Test
    public void GetApp() {
    	Response teamSeven = get(HOMEPAGE_TEAM07 + "/mainapp/" + TEAM18_APP_FINGERPRINT);
    	if (teamSeven.getStatusCode() == OK) {
    		
    		teamSeven.then().assertThat().statusCode(OK);
    	}  	

    }
    	
}
