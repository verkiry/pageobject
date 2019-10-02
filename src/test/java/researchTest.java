
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class researchTest {

    private static WebDriver driver;

   // @Attachment
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    //@Before
    public void before() {

        System.setProperty("webdriver.chrome.driver", "/work/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://192.168.4.222/login");
    }

    //@After
    public void after() {
//System.out.print ("Login success");
        driver.quit();
    }




    @Epic(value = "Проверка поиска по делам")
    //@Test
    public void DevicesSearchTest() {
        int counter=0;
        WebElement loginField = driver.findElement(By.name("UID"));
        loginField.sendKeys("maxim");
        WebElement passwordField = driver.findElement(By.name("PWD"));
        passwordField.sendKeys("12345");
        passwordField.sendKeys(Keys.ENTER); //залогинились
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
        WebDriverWait wait1 = new WebDriverWait(driver, 60);
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div"))); //ждем закрытия прелоадера
        driver.get("http://192.168.4.222/devices");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        WebElement selectElem= driver.findElement(By.xpath("//select[@name='sizePage']"));
        Select select=new Select(selectElem);
        selectElem.click();
        select.selectByVisibleText("100 на странице"); //отображаем 100 событий на странице
        List<WebElement> devices=driver.findElements(By.xpath("//div[@class='eventsContent-link']")); //список всех дел
        int randNumber=(int) (Math.random() * devices.size());
        String deviceNameToSearch=devices.get(randNumber).findElement(By.xpath("./div[@class='eventsItemChild itemEvent itemEventData'][1]//div[@class='text-overflow']")).getAttribute("innerText"); //берем имя случайного устройства
        WebElement searchField=driver.findElement(By.id("flt"));
        searchField.sendKeys(deviceNameToSearch);//ввели имя случайного устройства в строку поиска
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
        List<WebElement> resultCases=driver.findElements(By.xpath("//div[@class='eventsContent-link']")); //список устройств после ввода имени в строку поиска
        String [] resultCasesNames=new String[resultCases.size()];
        for (int i=0; i<resultCases.size(); i++){
            resultCasesNames[i]=resultCases.get(i).findElement(By.xpath("./div[@class='eventsItemChild itemEvent itemEventData'][1]//div[@class='text-overflow']")).getAttribute("innerText"); //записали имена всех устройств в массив
        }
        for (int j=0; j<resultCasesNames.length; j++){
            if (resultCasesNames[j].toLowerCase().contains(deviceNameToSearch.toLowerCase()) == false) { //проверяем, есть ли в именах устройств строка, которую вводили, если нет, наращиваем счетчик
                counter++;
            }
        }
        if (counter!=0) { //если счетчик не нулевой, значит, хотя бы в одном из имен устройств нет строки, которую вводили в поле поиска, значит- Test Failed
            System.out.println("!!!!!!!!!!!!");
            attachScreenshot();
            Allure.addAttachment("Неверный результат поиска по имени устройства " + deviceNameToSearch, " ");
            Assert.assertEquals(0, counter);
        }

    }
}



