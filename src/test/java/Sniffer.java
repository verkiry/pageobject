import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

public class Sniffer {
    private static WebDriver driver;
    @Attachment
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    @Before
    public void before()
    {

        System.setProperty("webdriver.chrome.driver", "/work/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://192.168.4.222/login");
    }
    @After
    public void after(){
//System.out.print ("Login success");
        driver.quit();
    }
    @Epic(value = "Поиск непереведенных строк в гриде")
    @Test
    public void RightSidebarSniffer() {
        WebElement loginField = driver.findElement(By.name("UID"));
        loginField.sendKeys("maxim");
        WebElement passwordField = driver.findElement(By.name("PWD"));
        passwordField.sendKeys("12345");
        passwordField.sendKeys(Keys.ENTER);
        int t=0;
        WebElement workspace = driver.findElement(By.xpath("/html/body/div[1]/wa-root/wa-cases/div[1]/wa-header/div/div/div/div[2]/ul/li[2]/a/span[2]"));
        while (t<80) try {
            try {
                Thread.sleep(500);
                t++;
                //500 - 0.5 сек
            } catch (InterruptedException ex) {
            }
            workspace.click();
            break;
        } catch (Exception ex) {
        }

        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
        WebElement devices= driver.findElement(By.xpath("/html/body/div[1]/wa-root/wa-workspace/div/div[2]/ws-tab/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div[1]/div[2]/span[1]"));
        devices.click();
        WebElement deviceToCheck=driver.findElement(By.xpath("/html/body/div[1]/wa-root/wa-workspace/div/div[2]/ws-tab/div/div[1]/div[1]/div[2]/div/div[1]/div[3]/div[2]/div[2]/div[76]/div/div[1]/div/input"));
        deviceToCheck.click();
        WebElement acceptButton= driver.findElement(By.xpath("/html/body/div[1]/wa-root/wa-workspace/div/div[2]/ws-tab/div/div[1]/div[2]/button"));
        acceptButton.click();
        WebDriverWait wait1 = new WebDriverWait(driver, 80);
        wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
        String amountOfPagesString=driver.findElement(By.xpath("//*[@id=\"ws-grid-pg\"]/div/div[2]/span[2]")).getText();
        int amountOfPages=Integer.parseInt(amountOfPagesString);
        List<String> bufferTexts= new ArrayList<String>();
        List<String> currentTexts = new ArrayList<String>();
        List<String> results= new ArrayList<String>();
        for (int i=0; i<amountOfPages;i++){
                List<WebElement> linesOnThePage = driver.findElements(By.xpath("//img[@class='app-mailBox-item-link-info-direction-device-img']"));
                List<WebElement> namesOfLines= driver.findElements(By.xpath("//div[@class='app-mailBox-item-link-info-direction-name-text']"));
                int amountOfLines=linesOnThePage.size();
            for (int n=0; n<amountOfLines; n++) {
                linesOnThePage.get(n).click();
                try {
                    Thread.sleep(500);
                    t++;
                } catch (InterruptedException ex) {
                }
                List<WebElement> headersOnRightSidebar = driver.findElements(By.xpath("//div[@class='app-sideBarTab-item-ContentItem-title']"));

                int amountOnRightSidebar = headersOnRightSidebar.size();
                currentTexts.clear();
                for (int k = 0; k < amountOnRightSidebar; k++) {
                    currentTexts.add(headersOnRightSidebar.get(k).getText());
                }
                Collections.sort(bufferTexts);
                Collections.sort(currentTexts);
                if (!currentTexts.equals(bufferTexts)) {
                    bufferTexts.clear();
                    for (int j = 0; j < amountOnRightSidebar; j++) {
                        String rightSideBarText = headersOnRightSidebar.get(j).getText();
                        bufferTexts.add(rightSideBarText);
                        Boolean isenglish = checkLangeage.checker(headersOnRightSidebar.get(j).getText());
                        if (isenglish == true) {
                            String typeAndCategory=namesOfLines.get(n).getText()+ " "+rightSideBarText;
                           if (!results.contains(typeAndCategory)){
                               results.add(typeAndCategory);
                               Allure.addAttachment("fullyEnglishInGrid " + typeAndCategory, typeAndCategory);
                               attachScreenshot();
                           }
                        }
                    }
                }
            }
            WebElement nextButton= driver.findElement(By.xpath("//a[@class='app-pagination-arrow-next']"));
            nextButton.click();
            try {
                Thread.sleep(700);
                t++;
                //500 - 0.5 сек
            } catch (InterruptedException ex) {
            }
        }
    }

}
