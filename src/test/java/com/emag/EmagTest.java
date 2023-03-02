package com.emag;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class EmagTest {

    private WebDriver driver;
    @BeforeMethod
    public void setUp() {
        //crearea broser Chrome
        driver = new ChromeDriver();
        //punem fereastra de browser pe tot ecranul
        driver.manage().window().maximize();
        //deschide pagina Emag
        driver.get("https://www.emag.ro");
    }

    @Test(priority = 1)
    public void cautareProdusInexistent() {
        //cautare element search bar
        WebElement mySearch = driver.findElement(By.id("searchboxTrigger"));

        //scriere nume produse inexistent
        mySearch.sendKeys("ggxeavfd");

        //cautam element lupa din bara de search
        WebElement myFindButton = driver.findElement(By.className("searchbox-submit-button"));

        //apasa buton lupa
        myFindButton.click();

        //cautare element(text) care indica numarul de produse gasite
        String myEmagSearchResult = driver.findElement(By.className("listing-grid-title")).getText();

        //verificare ca textul incepe cu 0 (exemplu: "0 rezultate pentru: "ggxeavfd"")
        Assert.assertTrue(myEmagSearchResult.startsWith("0"));
    }

    @Test(priority = 2)
    public void adaugaProduInCos() {
        //cautare element search bar
        WebElement mySearch = driver.findElement(By.id("searchboxTrigger"));

        //scriere nume produse
        mySearch.sendKeys("aspirator");

        //cautam element lupa din bara de search
        WebElement myFindButton = driver.findElement(By.className("searchbox-submit-button"));

        //apasa buton lupa
        myFindButton.click();

        //o folosim cand asteptam incarcarea elementelor in pagina (5 secunde)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //cautare element(button) de adaugare in cos si il apasam
        driver.findElement(By.xpath("//button[text()='Adauga in Cos']")).click();

        // dam timp cosului de cumparaturi sa se actualizeze
        // asteptam ca numarul de produse din cos sa fie vizibil
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementNumarProduseInCos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"my_cart\"]/span[1]")));

        // extragem si verificam numarul de produse din cos
        String textNumarProduseInCos = elementNumarProduseInCos.getText();
        Assert.assertEquals(textNumarProduseInCos, "1");
    }

    @Test(priority = 3)
    public void adaugaLaFavorite() {
        //cautare element search bar
        WebElement mySearch = driver.findElement(By.id("searchboxTrigger"));

        //scriere nume produs
        mySearch.sendKeys("televizor");

        //cautam element lupa din bara de search
        WebElement myFindButton = driver.findElement(By.className("searchbox-submit-button"));

        //apasa buton lupa
        myFindButton.click();

        //o folosim cand asteptam incarcarea elementelor in pagina (5 secunde)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //adaugam produsul la favorite
        driver.findElement(By.className("add-to-favorites")).click();

        // dam timp listei de favorite sa se actualizeze
        // asteptam ca numarul de produse din cos sa fie vizibil
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement elementNumarProduseFavorite = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"my_wishlist\"]/span[1]")));

        // extragem si verificam numarul de produse favorite
        String textNumarProduseFavorite = elementNumarProduseFavorite.getText();
        Assert.assertEquals(textNumarProduseFavorite, "1");
    }

    @AfterMethod
    private void tearDown(){
        //inchidem pagina si browserul
        //driver.quit();
    }

}
