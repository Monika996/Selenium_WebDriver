package d19_09_2022;

import java.io.File;


import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BootstrapTableTests {
//	1.Zadatak
//	Kreirati BootstrapTableTests klasu koja ima:
//	Base url: https://s.bootsnipp.com
//	Test #1: Edit Row
//	Podaci:
//	First Name: ime polaznika
//	Last Name: prezime polaznika
//	Middle Name: srednje ime polanzika
//	Koraci:
//	Ucitati stranu /iframe/K5yrx
//	Verifikovati naslov stranice Table with Edit and Update Data - Bootsnipp.com
//	Klik na Edit dugme prvog reda
//	Sacekati da dijalog za Editovanje bude vidljiv
//	Popuniti formu podacima. 
//	Bice potrebno da pre unosa tekst pobrisete tekst koji vec postoji, za to se koristi metoda clear. Koristan link
//	Klik na Update dugme
//	Sacekati da dijalog za Editovanje postane nevidljiv
//	Verifikovati da se u First Name celiji prvog reda tabele javlja uneto ime
//	Verifikovati da se u Last Name celiji prvog reda tabele javlja uneto prezime
//	Verifikovati da se u Middle Name celiji prvog reda tabele javlja uneto srednje ime
//	Za sve validacije ispisati odgovarajuce poruke u slucaju greske
//	Test #2: Delete Row
//	Podaci:
//	First Name: ime polaznika
//	Last Name: prezime polaznika
//	Middle Name: srednje ime polanzika
//	Koraci:
//	Ucitati stranu /iframe/K5yrx
//	Verifikovati naslov stranice Table with Edit and Update Data - Bootsnipp.com
//	Klik na Delete dugme prvog reda
//	Sacekati da dijalog za brisanje bude vidljiv
//	Klik na Delete dugme iz dijaloga 
//	Sacekati da dijalog za Editovanje postane nevidljiv
//	Verifikovati da je broj redova u tabeli za jedan manji
//	Za sve validacije ispisati odgovarajuce poruke u slucaju greske
//
//	Test #3: Take a Screenshot
//	Koraci:
//	Ucitati stranu  /iframe/K5yrx
//	Verifikovati naslov stranice Table with Edit and Update Data - Bootsnipp.com
//	Kreirati screenshot stranice. Koristan link https://www.guru99.com/take-screenshot-selenium-webdriver.html
//	Fajl cuvajte na putanji gde su vam bile slike od proslog domaceg. Na putanji: src/paket_za_domaci/nazivslike.png

	private WebDriver driver;
	private WebDriverWait wait;
	private String baseUrl = "https://s.bootsnipp.com";

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.get("baseUrl");
	}

	@Test(priority = 10)
	public void EditRow() {
		driver.get(baseUrl + "/iframe/K5yrx");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Table with Edit and Update Data - Bootsnipp.com";

		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: Unexpected title.");

		driver.findElement(By.xpath("//*[@id = 'd1']/td[5]/button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("modal-dialog")));

		driver.findElement(By.id("fn")).clear();
		driver.findElement(By.id("ln")).clear();
		driver.findElement(By.id("mn")).clear();

		driver.findElement(By.id("fn")).sendKeys("Monika");
		driver.findElement(By.id("ln")).sendKeys("Petrovic");
		driver.findElement(By.id("mn")).sendKeys("Niki");
		driver.findElement(By.id("up")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-dialog")));

		Assert.assertEquals(driver.findElement(By.id("f1")).getText(), "Monika", "ERROR: Wrong first name.");
		Assert.assertEquals(driver.findElement(By.id("l1")).getText(), "Petrovic", "ERROR: Wrong last name.");
		Assert.assertEquals(driver.findElement(By.id("m1")).getText(), "Niki", "ERROR: Wrong middle name.");

	}

	@Test(priority = 20)
	public void DeleteRow() {
		driver.get(baseUrl + "/iframe/K5yrx");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Table with Edit and Update Data - Bootsnipp.com";

		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: Unexpected title.");

		driver.findElement(By.xpath(("//*[contains(@data-target,'#delete')]"))).click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("del")));
		driver.findElement(By.id("del")).click();

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("modal-dialog")));

		boolean deleted = driver.findElements(By.xpath("//tbody/tr")).size() < 2;
		Assert.assertFalse(deleted, "Row isn't deleted");
	}

	@Test(priority = 30)
	public void takeAScreenshot() throws Exception {
		driver.get(baseUrl + "/iframe/K5yrx");
		String actualTitle = driver.getTitle();
		String expectedTitle = "Table with Edit and Update Data - Bootsnipp.com";

		Assert.assertEquals(actualTitle, expectedTitle, "ERROR: Unexpected title.");

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File source = scrShot.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source, new File("/src/d19_09_2022/smajli.png"));

	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("After Method");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
