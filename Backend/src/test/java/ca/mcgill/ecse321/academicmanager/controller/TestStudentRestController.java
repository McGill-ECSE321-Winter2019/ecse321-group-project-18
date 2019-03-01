package ca.mcgill.ecse321.academicmanager.controller;


import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class TestStudentRestController extends TestAcademicManagerRestController {
    private String relation_name = "students/";
    protected String _prefix = HOMEPAGE + relation_name;

    @Test
    public void TestView() {
        given().
                when().
                get(_prefix).
                then().
                assertThat().
                statusCode(OK);
    }
}
