import au.com.bytecode.opencsv.CSVWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class getAmountOfData {
    public static void main (String[] args){

    }
    private static WebDriver driver;
    public static void getAmount(String time) throws IOException {
        System.setProperty("webdriver.chrome.driver", "/work/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://192.168.4.222/login");

        WebElement loginField = driver.findElement(By.name("UID"));
        loginField.sendKeys("maxim");
        WebElement passwordField = driver.findElement(By.name("PWD"));
        passwordField.sendKeys("12345");
        passwordField.sendKeys(Keys.ENTER); //залогинились
        WebElement workspace = driver.findElement(By.xpath("/html/body/div[1]/wa-root/wa-cases/div[1]/wa-header/div/div/div/div[2]/ul/li[2]/a/span[2]"));
        int t = 0;
        while (t < 80) try { //входим в воркспейс
            try {
                //ДЕЛАЕМ
                Thread.sleep(500);
                t++;
                //500 - 0.5 сек
            } catch (InterruptedException ex) {
            }
            workspace.click();
            break;
        } catch (Exception ex) {
        }
        WebDriverWait wait = new WebDriverWait(driver, 80);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div"))); //ждем закрытия прелоадера
        WebElement amountOfStrings = driver.findElement(By.xpath("//span[@class='app-content-title-count']"));
        String amountOfData = amountOfStrings.getAttribute("innerText");
        String csv = "C:\\Users\\Evgeny\\Documents\\trend1\\test2.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        String[] record = (time + "," +amountOfData).split(",");
        writer.writeNext(record);
        writer.close();
        driver.quit();
    }
}