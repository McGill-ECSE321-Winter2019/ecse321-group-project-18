package ca.mcgill.ecse321.external;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
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

	/**
	 * Team 7 requires an instance of an app exists before doing other RESTful services,
	 * so we will make sure there is an app available before any tests.
	 * The appid we use is 18.
	 */
	@Before
	public void InitApp() {
		// try to initialize an app, id=18
		Response teamSeven = post(HOMEPAGE_TEAM07 + "mainapp/" + TEAM18_APP_FINGERPRINT);

		// already exists so compare with 500
		if (teamSeven.getStatusCode() != OK) {
			Assert.assertEquals(teamSeven.getStatusCode(), INTERNAL_SERVER_ERROR);
		}
		// make sure app 18 exists
		get(HOMEPAGE_TEAM07 + "mainapp/" + TEAM18_APP_FINGERPRINT + "/getapp")
				.then().assertThat().statusCode(OK);
	}
	/**
	 * Applied to team 17's RESTful services.
	 * This test use POST to create a Student in the app, then try to retrieve the same Student,
	 * and finally compares all important attributes to see if they match.
	 * */
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
		if (ironMan.getStatusCode() == INTERNAL_SERVER_ERROR) {
			// ironMan might have been created before:
			get(HOMEPAGE_TEAM07 + "students/" + _id).then().assertThat().statusCode(OK);
		}

		// careful: team 7's phone number are integers
		get(HOMEPAGE_TEAM07 + "students/" + _id).then().assertThat().statusCode(OK)
				.body("name", equalTo(_name))
				.body("studentID", equalTo(Integer.parseInt(_id)))
				.body("email", equalTo(_email))
				.body("major", equalTo(_major))
				.body("phone", equalTo(Integer.parseInt(_phone)));
	}

	@Test
	public void GetApp() {
		get(HOMEPAGE_TEAM07 + "mainapp/" + TEAM18_APP_FINGERPRINT + "/getapp")
				.then().assertThat().statusCode(OK);
	}

}
