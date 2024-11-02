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
        RestAssured.baseURI = "";
    }

    @Test
    public void testLogin() throws JsonProcessingException {
        LoginRequestDto dto = new LoginRequestDto();
        dto.userId = "";
        dto.roleId = "";
        dto.applicationId = "";
        dto.groupId = "";

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
