package ca.mcgill.ecse321.academicmanager.controller;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.*;

/**
 * Test suite related to the Student.
 * Relation name: students.
 * @author Bach Tran
 * */
public class TestStudentRestController extends TestAcademicManagerRestController {
    private String relation_name = "students/";
    protected String _prefix = HOMEPAGE + relation_name;

    protected String _id = "260881053";
    protected String _firstname = "Donald";
    protected String _lastname = "Duck";
    protected String _coopid = "-3";

    protected String ConstructLink(String order, String id, String firstname, String lastname, String coopid) {
        return super.ConstructLink() + relation_name + order + "?id=" + id +
                "&firstname=" + firstname + "&lastname=" + lastname + "&cooperatorid=" + coopid;
    }
    
    @Before
    public void TestDependenciesExistence() {

        // checks if we have a Cooperator available on the database.
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
        // checks if we have a Cooperator available on the database.
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
        // try to POST a Student to the database.
        given().
        when().
                post(ConstructLink(POST, _id, _firstname, _lastname, _coopid)).
                then().assertThat().statusCode(OK);
        // assure that we can GET what exactly we have just posted.
        when()
                .get(_prefix + _id)
        .then()
                .assertThat().statusCode(OK).
                body("firstName", equalTo(_firstname)).
                body("lastName", equalTo(_lastname));
        // clear that Student out of the database, it's just a test Student anyway :)
        delete(_prefix + _id).then().assertThat().statusCode(NO_CONTENT);
    }
}