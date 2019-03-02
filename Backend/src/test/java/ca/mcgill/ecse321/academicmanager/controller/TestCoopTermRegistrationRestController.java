package ca.mcgill.ecse321.academicmanager.controller;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class TestCoopTermRegistrationRestController extends TestAcademicManagerRestController {
    //https://cooperatorapp-backend-18.herokuapp.com/coopTermRegistrations/create?registrationid=unemployed&jobid=830102&studentid=112003052&termid=summer-2020
    static String _relation_name = "coopTermRegistrations/";
    static String _prefix = HOMEPAGE + _relation_name;

    private String _id = "unemployed";
    private String _jobid = "830102";
    private String _studentid = "112003052";
    private String _termid = TestTermRestController._id;
    private String _coopid = "3";

    static String ConstructLink(String id, String jobid, String studentid, String termid) {
        return _prefix + POST + "?registrationid=" + id + "&jobid=" + jobid
                + "&studentid=" + studentid + "&termid=" + termid;
    }

    @Before
    public void TestDependenciesExistence() {
        // checks if we have a Cooperator id=1 available on the database.
        Response fromCooperators = get(HOMEPAGE + "cooperators/" + _coopid);
        if (fromCooperators.getStatusCode() != OK) {
            given()
                    .when()
                    .post(HOMEPAGE + "cooperators/create?id=" + _coopid)
                    .then()
                    .assertThat().statusCode(OK);
        }
        // check if we have a Student ready
        Response fromStudents = get(HOMEPAGE + "students/" + _studentid);
        if (fromStudents.getStatusCode() != OK) {
            String _firstname = "Peter";
            String _lastname = "Parker";
            given()
                    .when()
                    .post(HOMEPAGE + "students/create?id=" + _studentid
                    + "&firstname=" + _firstname + "&lastname=" + _lastname + "&cooperatorid=" + _coopid)
                    .then()
                    .assertThat().statusCode(OK);
        }
        // check if we have a Term ready
        Response fromTerms = get(HOMEPAGE + "terms/" + _termid);
        if (fromTerms.getStatusCode() != OK) {

            given()
                    .when()
                    .post(TestTermRestController.ConstructLink(_termid,
                            TestTermRestController._name,
                            TestTermRestController._student_deadline,
                            TestTermRestController._coop_deadline))
                    .then()
                    .assertThat().statusCode(OK);
        }
    }

    @After
    public void ClearDependencies() {
        // checks if we have a Cooperator id=1 available on the database.
        Response fromCooperators = get(HOMEPAGE + "cooperators/" + _coopid);
        if (fromCooperators.getStatusCode() == OK) {
            given()
                    .when()
                    .delete(HOMEPAGE + "cooperators/" + _coopid)
                    .then()
                    .assertThat().statusCode(NO_CONTENT);
        }
        // check if we have a Student ready
        Response fromStudents = get(HOMEPAGE + "students/" + _studentid);
        if (fromStudents.getStatusCode() == OK) {
            given()
                    .when()
                    .delete(HOMEPAGE + "students/" + _studentid)
                    .then()
                    .assertThat().statusCode(NO_CONTENT);
        }
        // check if we have a Term ready
        Response fromTerms = get(HOMEPAGE + "terms/" + _termid);
        if (fromTerms.getStatusCode() == OK) {

            given()
                    .when()
                    .delete(HOMEPAGE + "terms/" + _termid)
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
        // Try to POST a Term
        given().
                when().
                post(ConstructLink(_id, _jobid, _studentid, _termid)).
                then()
                .assertThat().statusCode(OK);
        // Try to receive the same Term having just posted.
        when()
                .get(_prefix + _id)
                .then()
                .assertThat().statusCode(OK);
        // Delete the database afterwards
        delete(_prefix + _id).then().assertThat().statusCode(NO_CONTENT);
    }
}
