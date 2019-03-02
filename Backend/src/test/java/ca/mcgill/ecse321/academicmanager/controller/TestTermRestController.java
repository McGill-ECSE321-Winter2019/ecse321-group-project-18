package ca.mcgill.ecse321.academicmanager.controller;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class TestTermRestController extends TestAcademicManagerRestController {
    static String relation_name = "terms/";
    static String _prefix= HOMEPAGE + relation_name;
    // sample attributes
    static String _id = "sample-2020";
    static String _name = "Summer 2020";
    static String _student_deadline = "2020-01-01";
    static String _coop_deadline = "2020-02-01";
    /**
     * @sampleLink https://cooperatorapp-backend-18.herokuapp.com/terms/create?id=sample-2020&name=Summer 2020&studentdeadline=2020-01-01&coopdeadline=2020-02-01
     * */
    static String ConstructLink(String id, String name, String student_deadline, String coop_deadline) {
        return _prefix + POST + "?id=" + id + "&name=" + name + "&studentdeadline="
                + student_deadline + "&coopdeadline=" + coop_deadline;
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
                post(ConstructLink(_id, _name, _student_deadline, _coop_deadline)).
                then()
                    .assertThat().statusCode(OK);
        // Try to receive the same Term having just posted.
        when()
                .get(_prefix + _id)
                .then()
                .assertThat().statusCode(OK).
                body("termName", equalTo(_name)).
                body("studentEvalFormDeadline", equalTo(_student_deadline)).
                body("coopEvalFormDeadline", equalTo(_coop_deadline));
        // clean up the database
        delete(_prefix + _id).then().assertThat().statusCode(NO_CONTENT);
    }
}
