import Dto.LoginRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ExampleTest {
    private static final Log log = LogFactory.getLog(ExampleTest.class);

    @Before
    public  void setup() {
        RestAssured.baseURI = "https://svc.pamafix-dev.dot.co.id";
    }

    @Test
    public void testLogin() throws JsonProcessingException {
        LoginRequestDto dto = new LoginRequestDto();
        dto.userId = "9c968871-fbd4-41de-916b-a958e3765cd2";
        dto.roleId = "71b136bc-cb1c-49cf-af18-a6808d253068";
        dto.applicationId = "5b8428be-eb38-432e-b825-b851e07d1745";
        dto.groupId = "d0a3fe11-012d-42aa-a46a-4a9d84ffefe5";

        ObjectMapper mapper = TestHelper.getMapper();
        String token = TestHelper.getToken("", "");
        String requestJsonString = mapper.writeValueAsString(dto);

        Response loginResponse = given()
                .contentType("application/json")
                .body(requestJsonString)
                .when()
                .post("/iam/api/v1/auth/login")
                .then()
                .statusCode(200)
                .extract().response();

        loginResponse.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/login.json"));
    }
}
