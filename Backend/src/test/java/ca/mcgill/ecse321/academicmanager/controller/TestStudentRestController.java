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
    protected String _coopid = "3";

    protected String ConstructLink(String order, String id, String firstname, String lastname, String coopid) {
        return super.ConstructLink() + relation_name + order + "?id=" + id +
                "&firstname=" + firstname + "&lastname=" + lastname + "&cooperatorid=" + coopid;
    }

    /**
     * Creating a Student need an existence of at least 1 Cooperator on the server.
     * This method will make sure it is ok, and if the Cooperator doesn't exist, then
     * create one.
     * */
    @Before
    public void TestIfCooperatorExist() {

        // checks if we have a Cooperator id=1 available on the database.
        Response fromCooperators = get(HOMEPAGE + "cooperators/" + _coopid);
        if (fromCooperators.getStatusCode() != OK) {
            given()
            .when()
                    .post(HOMEPAGE + "cooperators/create?id=" + _coopid)
            .then()
                    .assertThat().statusCode(OK);
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
    public void TestCreateStudent() {
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
    }
}