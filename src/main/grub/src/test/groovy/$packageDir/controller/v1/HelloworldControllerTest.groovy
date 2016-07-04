package ${packageName}.controller.v1

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response

import com.example.grub.Application

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static io.restassured.RestAssured.*
import static org.hamcrest.Matchers.*
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.never
import static org.mockito.Mockito.times
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest(randomPort=true)
class HelloworldControllerTest {

    @Value('\${local.server.port}')
    private int port

    @Before
    void setUp() {
        RestAssured.port = port
    }

    @After
    void tearDown() {
        RestAssured.reset()
    }

    @Test
    void testCORSHeaders() throws Exception {
        given().header("Origin", "fake.host.to.trigger.cors").when().get("/v1").then()
                .header("Access-Control-Allow-Origin", "fake.host.to.trigger.cors")
    }

    @Test
    void testSayHello() throws Exception {

        // test data
        String user = "John"
        String message = "Hello little "

        // expected json
        String expectedJson = """
        {
          "message": "\${message}",
          "user": "\${user}"
        }
        """

        // Rest call and asserts
        given().contentType(ContentType.JSON)
                .when()
                .param("user", user)
                .param("message", message)
                .get('/v1').then()
                .statusCode(HttpStatus.OK.value)
                .contentType(ContentType.JSON)
                .body(sameJSONAs(expectedJson).allowingExtraUnexpectedFields())
    }
    
    
    @Test
    void testSayHello_NoParams() throws Exception {

        // expected json
        String expectedJson = """
        {
          "message": null,
          "user": null
        }
        """

        // Rest call and asserts
        given().contentType(ContentType.JSON)
                .when()
                .get('/v1').then()
                .statusCode(HttpStatus.OK.value)
                .contentType(ContentType.JSON)
                .body(sameJSONAs(expectedJson).allowingExtraUnexpectedFields())
    }
}
