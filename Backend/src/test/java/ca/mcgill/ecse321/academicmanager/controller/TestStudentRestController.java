package ca.mcgill.ecse321.academicmanager.controller;


import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class TestStudentRestController extends TestAcademicManagerRestController {
    private String relation_name = "students";
    protected String _prefix = HOMEPAGE + relation_name;

    protected String _id = "260881053";
    protected String _firstname = "Donald";
    protected String _lastname = "Duck";
    protected String _coopid = "1";

    protected String ConstructLink(String order, String id, String firstname, String lastname, String coopid) {
        return _prefix + order + "?id=" + id +
                "&firstname=" + firstname + "&lastname=" + lastname + "&cooperatorid=" + coopid;
    }

    @Test
    public void TestView() {
        given().
                when().
                get(_prefix).
                then().
                assertThat().
                statusCode(OK);
    }

    @Test
    public void TestCreateStudent() {
        given().
                when().
                get(ConstructLink(POST, _id, _firstname, _lastname, _coopid)).
                then().assertThat().statusCode(OK);
    }
}
