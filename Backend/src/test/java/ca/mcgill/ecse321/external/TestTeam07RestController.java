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
    @Test
    public void TestInitApp() {
        // try to use their POST...
        Response teamSeven = get(HOMEPAGE_TEAM07 + "mainapp/"+ TEAM18_APP_FINGERPRINT + "/getapp");
        if (teamSeven.getStatusCode() != OK) {
            // the app hasn't exist yet!
            post(HOMEPAGE_TEAM07 + "/mainapp/" + TEAM18_APP_FINGERPRINT)
                    .then().assertThat().statusCode(OK);
        }

        teamSeven.then().assertThat().statusCode(OK);
    }

    @Test
    public void TestPostGetStudent() {
        String _name = "Anthony Stark";
        String _id = "42";
        String _email = "tony.stark@avengers.org";
        String _major = "genius, billionaire, playboy, philanthropist";
        String _phone = "911";
        String _appid = TEAM18_APP_FINGERPRINT;

        Response ironMan = post(HOMEPAGE_TEAM07 + "students/" + _name + "/" + _id + "/" + _email
        + "/" + _major + "/" + _phone + "/" + _appid);
        if (ironMan.getStatusCode() != OK) {
            // if it's already exists...
            if (ironMan.getStatusCode() == INTERNAL_SERVER_ERROR) {
                get(HOMEPAGE_TEAM07 + "students/" + _id).then().assertThat().statusCode(OK);
            }
        }
        get(HOMEPAGE_TEAM07 + "students/" + _id).then().assertThat().statusCode(OK)
                .body("name", equalTo(_name))
                .body("studentID", equalTo(Integer.parseInt(_id)))
                .body("email", equalTo(_email))
                .body("major", equalTo(_major))
                .body("phone", equalTo(Integer.parseInt(_phone)));
    }

    @Test
    public void TestExamples() {

        String[] examples = {
        "https://employer-backend-8888.herokuapp.com/mainapp/1/getapp",
        "https://employer-backend-8888.herokuapp.com/students/26080",
        "https://employer-backend-8888.herokuapp.com/mainapp/1/getstudents",
        "https://employer-backend-8888.herokuapp.com/mainapp/1/getemployers"
        };


        for (String example : examples) {
            get(example).then().assertThat().statusCode(OK);
        }
    }
}
