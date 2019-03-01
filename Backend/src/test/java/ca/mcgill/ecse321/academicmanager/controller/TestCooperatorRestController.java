package ca.mcgill.ecse321.academicmanager.controller;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;

public class TestCooperatorRestController extends TestAcademicManagerRestController {
    protected String relation_name = "cooperators";
    protected String _prefix = HOMEPAGE + relation_name;
    protected String _id = "1";

    @Test
    public void TestView() {
        when()
                .get(_prefix).then().assertThat().statusCode(200);
    }

    @Test
    public void TestCreateNaive() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id","1")
        .when()
                .post(relation_name + "/create")
        .then()
                .statusCode(200);
    }

}
