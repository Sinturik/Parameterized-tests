package guru.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DribbleSearchCompaniesTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.startMaximized = true;
    }

    //обычные тесты
    @DisplayName("Поиск на сайте Dribble.com названия компании Purrweb")
    @Tag("Middle")
    @Test
    void searchPurrwebCompanyTest() {
        Selenide.open("https://dribbble.com/");
        $("[data-site-nav-element='Search']").click();
        $("[id=search]").setValue("Purrweb").pressEnter();
        $(".search-results-heading").shouldHave(text("Purrweb"));
    }

    @DisplayName("Поиск на сайте Dribble.com названия компании Ronas")
    @Tag("Middle")
    @Test
    void searchRonasCompanyTest() {
        Selenide.open("https://dribbble.com/");
        $("[data-site-nav-element='Search']").click();
        $("[id=search]").setValue("Ronas").pressEnter();
        $(".search-results-heading").shouldHave(text("Ronas"));
    }

    //поиск purrweb, проверка по другому локатору
    @Test
    void searchPurrwebCompanyTest2() {
        Selenide.open("https://dribbble.com/");
        $("[data-site-nav-element='Search']").click();
        $("[id=search]").setValue("Purrweb").pressEnter();
        $(".search-results-description").shouldHave(text("435 unique Purrweb work, designs, illustrations, and graphic elements"));
    }

    //параметризованный тест с одинаковым ожидаемым результатом для параметров
    @ValueSource(strings = {"Purrweb", "Ronas"})
    @ParameterizedTest(name = "Поиск на сайте Dribble.com названия компании {0}")
    void searchCompaniesTest(String companyName) {
        Selenide.open("https://dribbble.com/");
        $("[data-site-nav-element='Search']").click();
        $("[id=search]").setValue(companyName).pressEnter();
        $(".search-results-heading").shouldHave(text(companyName));
    }

    //параметризованный тест с разным ожидаемым результатом для параметров
    @CsvSource(value = {
            "Purrweb| 435 unique Purrweb work, designs, illustrations, and graphic elements",
            "Acreo| 0 unique Acreo designs, illustrations, and graphic element"},
            delimiter = '|')
    @ParameterizedTest(name = "Поиск на сайте Dribble.com названия компании {0} и проверка отображения текста {1}")
    void searchCompanies2Test(String companyName, String expectedResult) {
        Selenide.open("https://dribbble.com/");
        $("[data-site-nav-element='Search']").click();
        $("[id=search]").setValue(companyName).pressEnter();
        $(".search-results-description").shouldHave(text(expectedResult));
    }
}


