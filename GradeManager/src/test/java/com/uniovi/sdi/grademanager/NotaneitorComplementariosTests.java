package com.uniovi.sdi.grademanager;

import com.uniovi.sdi.grademanager.pageobjects.PO_LoginView;
import com.uniovi.sdi.grademanager.pageobjects.PO_Properties;
import com.uniovi.sdi.grademanager.pageobjects.PO_SignUpView;
import com.uniovi.sdi.grademanager.pageobjects.PO_View;
import com.uniovi.sdi.grademanager.util.SeleniumUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
class NotaneitorComplementariosTests {

    static String pathFirefox = System.getenv().getOrDefault("FIREFOX_BIN", "/usr/bin/firefox");
    static String geckodriver = System.getenv().getOrDefault("GECKODRIVER_BIN", "/usr/local/bin/geckodriver");
    static WebDriver driver = getDriver(pathFirefox, geckodriver);
    static String URL = resolveBaseUrl();

    private static WebDriver getDriver(String pathFirefox, String geckodriver) {
        System.setProperty("webdriver.firefox.bin", pathFirefox);
        System.setProperty("webdriver.gecko.driver", geckodriver);
        return new FirefoxDriver();
    }

    private static String resolveBaseUrl() {
        String baseUrl = System.getProperty(
                "test.baseUrl",
                System.getenv().getOrDefault("TEST_BASE_URL", "http://localhost:8080/")
        );
        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }

    @BeforeEach
    void setUp() {
        driver.navigate().to(URL);
    }

    @AfterEach
    void tearDown() {
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    static void end() {
        driver.quit();
    }

    @Test
    @Order(1)
    void registroUsuarioConDatosValidos() {
        driver.navigate().to(URL + "signup");
        String uniqueDni = "AUTO" + (System.currentTimeMillis() % 1_000_000_000L);
        String password = "123456";
        PO_SignUpView.fillForm(driver, uniqueDni, "Carlos", "Gonzalez", password, password);

        if (driver.findElements(By.id("nav-logout")).isEmpty()) {
            loginAs(uniqueDni, password);
        }
        Assertions.assertFalse(PO_View.checkElementBy(driver, "id", "nav-logout").isEmpty());
        Assertions.assertFalse(PO_View.checkElementBy(driver, "text", uniqueDni).isEmpty());
    }

    @Test
    @Order(2)
    void registroDepartamentoConDatosInvalidos() {
        loginAs("99999988F", "123456");
        driver.navigate().to(URL + "department/add");

        fillDepartmentForm(" Nombre inválido ", "COMP9999Z", "Facultad de Ciencias", "985111111", "5");
        Assertions.assertFalse(PO_View.checkElementByKey(driver, "department.validation.name.trimmedNotBlank", PO_Properties.getSPANISH()).isEmpty());

        driver.navigate().to(URL + "department/add");
        fillDepartmentForm("Departamento Prueba", "CODIGO", "Facultad de Ciencias", "985111111", "5");
        Assertions.assertFalse(PO_View.checkElementByKey(driver, "department.validation.code.format", PO_Properties.getSPANISH()).isEmpty());
    }

    @Test
    @Order(3)
    void soloUsuariosAutorizadosPuedenDarAltaDepartamento() {
        driver.navigate().to(URL + "department/add");
        Assertions.assertFalse(PO_View.checkElementBy(driver, "id", "username").isEmpty());

        loginAs("99999990A", "123456");
        driver.navigate().to(URL + "department/add");
        assertForbiddenAccessToDepartmentForm();
        logout();

        loginAs("99999993D", "123456");
        driver.navigate().to(URL + "department/add");
        assertForbiddenAccessToDepartmentForm();
        logout();

        loginAs("99999988F", "123456");
        driver.navigate().to(URL + "department/add");
        Assertions.assertFalse(PO_View.checkElementBy(driver, "id", "name").isEmpty());
        Assertions.assertFalse(PO_View.checkElementByKey(driver, "department.add.title", PO_Properties.getSPANISH()).isEmpty());
    }

    private void loginAs(String dni, String password) {
        driver.navigate().to(URL + "login");
        PO_LoginView.fillLoginForm(driver, dni, password);
    }

    private void logout() {
        driver.navigate().to(URL + "logout");
    }

    private void fillDepartmentForm(String name, String code, String faculty, String phone, String professors) {
        WebElement nameInput = driver.findElement(By.name("name"));
        nameInput.clear();
        nameInput.sendKeys(name);

        WebElement codeInput = driver.findElement(By.name("code"));
        codeInput.clear();
        codeInput.sendKeys(code);

        WebElement facultyInput = driver.findElement(By.name("faculty"));
        facultyInput.clear();
        facultyInput.sendKeys(faculty);

        WebElement phoneInput = driver.findElement(By.name("phone"));
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        WebElement professorsInput = driver.findElement(By.name("professors"));
        professorsInput.clear();
        professorsInput.sendKeys(professors);

        driver.findElement(By.className("btn")).click();
    }

    private void assertForbiddenAccessToDepartmentForm() {
        SeleniumUtils.waitSeconds(driver, 1);
        String pageSource = driver.getPageSource();
        boolean has403 = pageSource.contains("403") || pageSource.contains("Forbidden");
        Assertions.assertTrue(has403, "Se esperaba un acceso denegado (403) para /department/add");
        Assertions.assertTrue(driver.findElements(By.id("name")).isEmpty(), "Un usuario no autorizado no debería ver el formulario de alta");
    }
}
