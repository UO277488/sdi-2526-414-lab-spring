package com.uniovi.sdi.grademanager;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

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
    void PR01() {
        Assertions.assertNotNull(driver);
    }

    @Test
    @Order(2)
    void PR02() {
        Assertions.assertTrue(driver.getCurrentUrl().contains("localhost"));
    }

    @Test
    @Order(3)
    void PR03() {
        Assertions.assertEquals(URL, driver.getCurrentUrl());
    }

    @Test
    @Order(4)
    void PR04() {
        Assertions.assertNotNull(driver.getTitle());
    }

    @Test
    @Order(5)
    void PR05() {
        Assertions.assertNotNull(driver.getPageSource());
    }

    @Test
    @Order(6)
    void PR06() {
        Assertions.assertFalse(driver.getWindowHandle().isBlank());
    }

    @Test
    @Order(7)
    void PR07() {
        Assertions.assertTrue(driver.getWindowHandles().size() >= 1);
    }

    @Test
    @Order(8)
    void PR08() {
        Assertions.assertNotNull(driver.manage());
    }

    @Test
    @Order(9)
    void PR09() {
        Assertions.assertNotNull(driver.navigate());
    }

    @Test
    @Order(10)
    void PR10() {
        Assertions.assertNotNull(driver.getCurrentUrl());
    }

}
