package pages;

import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.*;

public class DemoWebShopPage {

    public void openPageWithAuth(String authToken, String userEmail) {
        open("/Themes/DefaultClean/Content/images/logo.png");
        Cookie authCookie = new Cookie("NOPCOMMERCE.AUTH", authToken);
        WebDriverRunner.getWebDriver().manage().addCookie(authCookie);
        open("");
        $(".account").shouldHave(text(userEmail));
    }

    public void editAndCheckProfile(String firstName, String lastName, String userEmail) {
        open("/customer/info");
        $("#FirstName").setValue(firstName);
        $("#LastName").setValue(lastName);
        $("#Email").setValue(userEmail);
        $("[value='Save']").click();
        refresh();
        $("#FirstName").shouldHave(value(firstName));
        $("#LastName").shouldHave(value(lastName));
        $("#Email").shouldHave(value(userEmail));
    }
}
