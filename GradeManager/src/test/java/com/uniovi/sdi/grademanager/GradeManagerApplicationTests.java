package com.uniovi.sdi.grademanager;

import com.uniovi.sdi.grademanager.pageobjects.PO_HomeView;
import com.uniovi.sdi.grademanager.pageobjects.PO_LoginView;
import com.uniovi.sdi.grademanager.pageobjects.PO_PrivateView;
import com.uniovi.sdi.grademanager.pageobjects.PO_Properties;
import com.uniovi.sdi.grademanager.pageobjects.PO_SignUpView;
import com.uniovi.sdi.grademanager.pageobjects.PO_View;
import com.uniovi.sdi.grademanager.util.SeleniumUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GradeManagerApplicationTests {
    static String PathFirefox = System.getenv().getOrDefault("FIREFOX_BIN", "/usr/bin/firefox");
    static String Geckodriver = System.getenv().getOrDefault("GECKODRIVER_BIN", "/usr/local/bin/geckodriver");

    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090/";

    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
        //Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver, PO_Properties.getSPANISH());
        Assertions.assertEquals(
                welcomeMessageElement.getFirst().getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH())
        );
    }

    @Test
    @Order(3)
    public void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    @Test
    @Order(4)
    public void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    @Test
    @Order(5)
    public void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    @Test
    @Order(6)
    void PR05() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_View.checkElementBy(
                driver,
                "free",
                "//input[@name='dni']/parent::div/span[contains(@class,'text-danger')]"
        );
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertFalse(result.getFirst().getText().isBlank());
    }

    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990B", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_View.checkElementBy(
                driver,
                "free",
                "//input[@name='name']/parent::div/span[contains(@class,'text-danger')]"
        );
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertFalse(result.getFirst().getText().isBlank());
    }

    @Test
    @Order(9)
    public void PR07() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
    }

    @Test
    @Order(10)
    public void PR08() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        // Verificación por estructura de navegación (estable frente a idioma).
        List<WebElement> marksMenu = PO_View.checkElementBy(driver, "id", "navbarDropdown");
        Assertions.assertFalse(marksMenu.isEmpty());
        // La opción /mark/add está dentro de un dropdown; primero hay que desplegarlo.
        marksMenu.getFirst().click();
        List<WebElement> addMarkOption = SeleniumUtils.waitLoadElementsBy(
                driver, "free", "//a[@href='/mark/add']", PO_View.getTimeout()
        );
        Assertions.assertFalse(addMarkOption.isEmpty());
    }

    @Test
    @Order(11)
    public void PR09() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999988F", "123456");
        // El menú de usuarios solo aparece para admin.
        List<WebElement> usersMenu = PO_View.checkElementBy(driver, "id", "users-menu");
        Assertions.assertFalse(usersMenu.isEmpty());
    }

    @Test
    @Order(12)
    public void PR10() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123457");
        List<WebElement> usernameInput = PO_View.checkElementBy(driver, "id", "username");
        Assertions.assertFalse(usernameInput.isEmpty());
    }

    @Test
    @Order(13)
    public void PR11() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_HomeView.clickOption(driver, "logout", "text", loginText);
    }

    @Test
    @Order(14)
    public void PR12() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
        List<WebElement> marksList = SeleniumUtils.waitLoadElementsBy(driver, "free", "//tbody/tr", PO_View.getTimeout());
        Assertions.assertEquals(4, marksList.size());
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    @Test
    @Order(15)
    public void PR13() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999990A", "123456");
        String checkText = "Notas del usuario";
        PO_View.checkElementBy(driver, "text", checkText);
        By detailsLink = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]/a[contains(@href, '/mark/details/')]");
        driver.findElement(detailsLink).click();
        checkText = "Detalles de la nota";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.getFirst().getText());
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    @Test
    @Order(16)
    public void PR14() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        PO_View.checkElementBy(driver, "text", "99999993D");
        List<WebElement> marksMenu = PO_View.checkElementBy(driver, "id", "navbarDropdown");
        marksMenu.getFirst().click();
        List<WebElement> addMarkOption = PO_View.checkElementBy(driver, "free", "//a[contains(@href, '/mark/add')]");
        addMarkOption.getFirst().click();
        String checkText = "Nota sistemas distribuidos";
        PO_PrivateView.fillFormAddMark(driver, 3, checkText, "8");
        List<WebElement> pageLinks = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        pageLinks.getLast().click();
        List<WebElement> createdMark = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, createdMark.getFirst().getText());
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    @Test
    @Order(17)
    public void PR15() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillLoginForm(driver, "99999993D", "123456");
        PO_View.checkElementBy(driver, "text", "99999993D");
        List<WebElement> marksMenu = PO_View.checkElementBy(driver, "id", "navbarDropdown");
        marksMenu.getFirst().click();
        List<WebElement> listMarkOption = PO_View.checkElementBy(driver, "free", "//a[contains(@href, '/mark/list')]");
        listMarkOption.getFirst().click();
        List<WebElement> pageLinks = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        pageLinks.getLast().click();
        List<WebElement> deleteLink = PO_View.checkElementBy(driver, "free",
                "//td[contains(text(), 'Nota sistemas distribuidos')]/following-sibling::*/a[contains(@href, '/mark/delete/')]");
        deleteLink.getFirst().click();
        pageLinks = PO_View.checkElementBy(driver, "free", "//a[contains(@class, 'page-link')]");
        pageLinks.getLast().click();
        SeleniumUtils.waitTextIsNotPresentOnPage(driver, "Nota sistemas distribuidos", PO_View.getTimeout());
        String loginText = PO_HomeView.getP().getString("signup.message", PO_Properties.getSPANISH());
        PO_PrivateView.clickOption(driver, "logout", "text", loginText);
    }

    @Test
    @Order(30)
    void AUX01() {
        Assertions.assertNotNull(driver.manage());
    }

    @Test
    @Order(31)
    void AUX02() {
        Assertions.assertNotNull(driver.navigate());
    }

    @Test
    @Order(32)
    void AUX03() {
        Assertions.assertNotNull(driver.getCurrentUrl());
    }

}
