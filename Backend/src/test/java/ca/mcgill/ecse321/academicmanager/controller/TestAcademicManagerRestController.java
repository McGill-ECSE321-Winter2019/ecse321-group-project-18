package ca.mcgill.ecse321.academicmanager.controller;

import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;
//import static org.hamcrest.Matchers.*;
public class TestAcademicManagerRestController {
    static final int OK = 200;
    static final int INTERNAL_SERVER_ERROR = 500;
    static final int NOT_FOUND = 404;
    static final String POST = "create";
    static final String HOMEPAGE = "https://cooperatorapp-backend-18.herokuapp.com/";
    String relation_name = "";

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
     * Some entities relies on other entities to be existed before its own creation.
     * This method ensures that these dependencies exists so that creating the object is safe.
     * */
    @Before
    public void TestDependenciesExistence() {
        // no dependencies for this class.
    }

    /**
     * Using GET to query all objects from a relation.
     * Note that this test will not check deeply for each elements in that relation,
     * but only check if the GET call is running appropriately, i.e. if a 200 response value is returned.
     * */
    @Test
    public void TestView() {
        given().
        when().
                get(HOMEPAGE).
        then().
                assertThat().statusCode(OK);
    }
    /**
     * Using POST to create a new object and in the database persistence,
     * then use GET to obtain the same object.
     * The tess passes if all necessary attributes is sent and received correctly.
     * @author Bach Tran
     * @since 2019-03-01
     * */
    @Test
    public void TestPostGet() {
        //nothing to do! this is the homepage.
    }
}