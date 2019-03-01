package ca.mcgill.ecse321.academicmanager.controller;

import org.junit.Test;

import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;
public class TestAcademicManagerRestController {
    protected static final int OK = 200;
    protected static final int INTERNAL_SERVER_ERROR = 500;
    protected static final int NOT_FOUND = 404;
    protected static final String POST = "create";
    protected static final String HOMEPAGE = "https://cooperatorapp-backend-18.herokuapp.com/";
    protected String relation_name = "";

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
     * Checks if the home link is working properly.
     * */
    @Test
    public void TestView() {
        given().
                when().
                get(HOMEPAGE + this.relation_name).
                then().
                assertThat().
                statusCode(OK);
    }
}