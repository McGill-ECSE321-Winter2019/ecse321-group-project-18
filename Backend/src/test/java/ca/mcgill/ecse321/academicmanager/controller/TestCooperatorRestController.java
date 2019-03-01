package ca.mcgill.ecse321.academicmanager.controller;

import io.restassured.http.ContentType;
import org.junit.Test;
import static io.restassured.RestAssured.*;

public class TestCooperatorRestController extends TestAcademicManagerRestController {
    protected String relation_name = "cooperator";
    protected String _prefix = HOMEPAGE + relation_name;
    protected String _id = "1";

    @Test
    public void TestCreateNaive() {
        given()
                .contentType(ContentType.JSON)
                .pathParam("id","1")
        .when()
                .get(relation_name + "/" + _id)
        .then()
                .statusCode(200);
    }

}
