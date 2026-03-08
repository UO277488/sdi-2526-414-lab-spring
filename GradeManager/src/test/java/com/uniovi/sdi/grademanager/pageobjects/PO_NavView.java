package com.uniovi.sdi.grademanager.pageobjects;

import com.uniovi.sdi.grademanager.util.SeleniumUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PO_NavView extends PO_View {

    /**
     * Clic en una de las opciones principales (a href o id) y comprueba que se va a la vista destino.
     *
     * @param driver navegador en ejecución.
     * @param textOption valor del href o id de la opción.
     * @param criterio criterio para comprobar elemento en la vista destino.
     * @param targetText texto asociado al criterio en la vista destino.
     */
    public static void clickOption(WebDriver driver, String textOption, String criterio, String targetText) {
        // Compatibilidad con la guía (href) y con el markup actual del proyecto (id en nav-*).
        List<WebElement> elements = SeleniumUtils.waitLoadElementsBy(driver, "@href", textOption, getTimeout());
        if (elements.isEmpty()) {
            elements = SeleniumUtils.waitLoadElementsBy(driver, "id", textOption, getTimeout());
        }

        Assertions.assertFalse(elements.isEmpty(), "No se encontró la opción de navegación: " + textOption);
        elements.getFirst().click();

        List<WebElement> targetElements = SeleniumUtils.waitLoadElementsBy(driver, criterio, targetText, getTimeout());
        Assertions.assertFalse(targetElements.isEmpty(), "No se encontró el elemento destino: " + targetText);
    }

    /**
     * Selecciona el enlace de idioma correspondiente al id textLanguage.
     *
     * @param driver navegador en ejecución.
     * @param textLanguage id del idioma (btnEnglish, btnSpanish, btnAsturian).
     */
    public static void changeLanguage(WebDriver driver, String textLanguage) {
        List<WebElement> languageButton = SeleniumUtils.waitLoadElementsBy(driver, "id", "btnLanguage", getTimeout());
        Assertions.assertFalse(languageButton.isEmpty(), "No se encontró el botón de idioma");
        languageButton.getFirst().click();

        SeleniumUtils.waitLoadElementsBy(driver, "id", "languageDropdownMenuButton", getTimeout());

        List<WebElement> selectedLanguage = SeleniumUtils.waitLoadElementsBy(driver, "id", textLanguage, getTimeout());
        Assertions.assertFalse(selectedLanguage.isEmpty(), "No se encontró el idioma: " + textLanguage);
        selectedLanguage.getFirst().click();

        // Espera explícita a navegación/actualización para evitar carreras tras el click.
        new WebDriverWait(driver, Duration.ofSeconds(getTimeout()))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("lang="),
                        ExpectedConditions.presenceOfElementLocated(org.openqa.selenium.By.tagName("body"))
                ));
    }

    // Alias para mantener compatibilidad con implementaciones anteriores de la práctica.
    public static void changeLanguaje(WebDriver driver, String textLanguage) {
        changeLanguage(driver, textLanguage);
    }
}
