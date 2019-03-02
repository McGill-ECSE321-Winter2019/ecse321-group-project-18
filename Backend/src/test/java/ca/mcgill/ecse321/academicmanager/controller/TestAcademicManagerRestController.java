package ca.mcgill.ecse321.academicmanager.controller;

import org.junit.Test;

import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;
public class TestAcademicManagerRestController {
    static final int OK = 200;
    static final int INTERNAL_SERVER_ERROR = 500;
    static final int NOT_FOUND = 404;
    static final String POST = "create";
    static final String HOMEPAGE = "https://cooperatorapp-backend-18.herokuapp.com/";
    String relation_name = "";

    protected String ConstructLink() {
        return HOMEPAGE;
    }
//    @Test
//    public void justATest() {
//        given().
//                when().
//                get("https://cooperatorapp-backend-18.herokuapp.com/cooperators/").
//                then().
//                    assertThat().
//                    statusCode(200);
//    }
    /**
     * Check if the database is working properly.
     * */
    @Test
    public void TestView() {
        given().
        when().
                get(HOMEPAGE).
        then().
                assertThat().statusCode(OK);
    }
}