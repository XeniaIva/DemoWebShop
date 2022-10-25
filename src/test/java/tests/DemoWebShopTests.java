package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Locale;
import static io.restassured.RestAssured.given;

import pages.DemoWebShopPage;

public class DemoWebShopTests extends TestBase {

    Faker user = new Faker(new Locale("en"));
    String firstName = user.name().firstName(),
            lastName = user.name().lastName(),
            userEmail = user.internet().emailAddress(),
            password = user.numerify("##########");

    Faker newUser = new Faker(new Locale("en"));
    String newFirstName = newUser.name().firstName(),
            newLastName = newUser.name().lastName(),
            newUserEmail = newUser.internet().emailAddress();

    private final String header = "__RequestVerificationToken";
    private final String headerValue = "PynBwHJHHyE7NvukB2MS2E9qsBJ__lB_Z1O6XpZ2-rKosYrhvIo11z4xz0vMDhoedru0FkeItqJcjckdwMKWILIJZrld-v_GQ5I1AEtRs0c1";
    private final String paramValue = "58mBp8S83XW634lGOjTPfqAtCiWD3Ff_YPwqRlJm-YwcfGXkw7U8g_BOd-Kja2n-uSVW8Mmx8dao38JdIj56b-Gb00uoy0Hg0e2dtIp2SKM1";

    DemoWebShopPage page = new DemoWebShopPage();

    @BeforeEach
    void registration() {
        given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam(header, paramValue)
                .formParam("FirstName", firstName)
                .formParam("LastName", lastName)
                .formParam("Email", userEmail)
                .formParam("Password", password)
                .formParam("ConfirmPassword", password)
                .cookie(header, headerValue)
                .post("/register")
                .then();
    }

    @Test
    @DisplayName("Тест на регистрацию пользователя")
    void registrationTest() {
        registration();
        String authToken = given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("Email", userEmail)
                .formParam("Password", password)
                .post("/login")
                .then()
                .extract()
                .cookie("NOPCOMMERCE.AUTH");
        page.openPageWithAuth(authToken,userEmail);
    }

    @Test
    @DisplayName("Тест на редактирование профиля пользователя")
    void editProfileTest(){
       registration();
        String authToken = given()
                .when()
                .contentType("application/x-www-form-urlencoded; charset=utf-8")
                .formParam("Email", userEmail)
                .formParam("Password", password)
                .post("/login")
                .then()
                .extract()
                .cookie("NOPCOMMERCE.AUTH");
        page.openPageWithAuth(authToken,userEmail);
        page.editAndCheckProfile(newFirstName,newLastName,newUserEmail);
    }
}
