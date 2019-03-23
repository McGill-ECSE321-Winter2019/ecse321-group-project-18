package ca.mcgill.ecse321.academicmanager.controller;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.*;


public class TestCourseRestController extends TestAcademicManagerRestController {

    private String relation_name = "courses/";
    protected String _prefix = HOMEPAGE + relation_name;

    protected String _id = "AVEN999";
    protected String _term = "Summer 2020";
    protected String _name = "Introduction to The Avengers";
    protected String _rank = "1000";
    protected String _coopid = "3";

    protected String ConstructLink(String id, String term, String name, String rank, String coopid) {
        return super.ConstructLink() + relation_name + POST + "?id=" + id +
                "&term=" + term + "&name=" + name + "&rank=" + rank + "&cooperatorid=" + coopid;
    }

    @Before
    public void TestDependenciesExistence() {
        Response fromCooperators = get(HOMEPAGE + "cooperators/" + _coopid);
        if (fromCooperators.getStatusCode() != OK) {
            given()
                    .when()
                    .post(HOMEPAGE + "cooperators/create?id=" + _coopid)
                    .then()
                    .assertThat().statusCode(OK);
        }
    }

    @After
    public void ClearDependencies() {
        Response fromCooperators = get(HOMEPAGE + "cooperators/" + _coopid);
        if (fromCooperators.getStatusCode() == OK) {
            given()
                    .when()
                    .delete(HOMEPAGE + "cooperators/" + _coopid)
                    .then()
                    .assertThat().statusCode(NO_CONTENT);
        }
    }

    @Test
    public void TestView() {
        given().
                when().
                get(_prefix).
                then()
                .assertThat().statusCode(OK);
    }

    @Test
    public void TestPostGet() {
        System.out.println(ConstructLink(_id, _term, _name, _rank, _coopid));
        // try to POST a Student to the database.
        given().
                when().
                post(ConstructLink(_id, _term, _name, _rank, _coopid)).
                then().assertThat().statusCode(OK);
        // assure that we can GET what exactly we have just posted.
        // NOTE: we have to be careful here: courseRank is an int.
        when()
                .get(_prefix + _id)
                .then()
                .assertThat().statusCode(OK).
                body("term", equalTo(_term)).
                body("courseName", equalTo(_name)).
                body("courseRank", equalTo(Integer.parseInt(_rank)));
        // clean up the database
        delete(_prefix + _id).then().assertThat().statusCode(NO_CONTENT);
    }
}
