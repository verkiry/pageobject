package pages;

import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.utils;

import java.util.List;

public class workspacePage {
    WebDriver driver;
    By descriptionSearchField = By.id("searchInput");
    By fromSearchField = By.id("fasetFromDate");
    By toSearchField = By.id("fasetToDate");
    By firstLevelFilter = By.xpath("//div[@class='b-filterItem_head checkGroup']");
    By amountOfEvents = By.xpath("//span[@class='app-content-title-count']");
    By applyButton = By.xpath("//button[@class='btn btn-primary']");
    By resetFilterButton = By.xpath("//button[@class='filter-reset']");
    By event = By.xpath("//div[@class='app-mailBox-item-link']");
    By amountOfSetKE = By.xpath("//span[@title='Ключевые доказательства']/../..//span[@class='b-filterItem_count f_count']");
    By amountOfPages= By.xpath("//span[@class='app-pagination-count']");
    By nextArrow=By.xpath("//a[@class='app-pagination-arrow-next']");


    public workspacePage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isHaveFrom (WebElement event){
        try {
            return event.findElement(By.xpath(".//div[text()='От']/..//div[@class='app-mailBox-item-link-info-user-name-text']")).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false;
        }
    }

    public boolean isHaveTo (WebElement event){
        try {
            return event.findElement(By.xpath(".//div[text()='Кому']/..//div[@class='app-mailBox-item-link-info-user-name-text']")).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException ignored) {
            return false;
        }
    }

    public boolean isHaveHiddenSubfilters(WebElement filter) {
        try {
            if (filter.findElement(By.xpath(".//span[@class='b-filterItem_arrow f_chO']")).isDisplayed()) {
                return true;
            } else {
                return false;
            }
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void nextPage (){
        driver.findElement(nextArrow).click();
        utils.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
    }

    public WebElement getFilterCheckbox(WebElement filter) {
        return filter.findElement(By.xpath(".//div[@class='cbx']"));
    }

    public WebElement getFilterArrow(WebElement filter) {
        return filter.findElement(By.xpath(".//span[@class='b-filterItem_arrow f_chO']"));
    }

    public void clickFilterArrow(WebElement filter) {
        getFilterArrow(filter).click();
    }


    public int getAmountOfEvents() {
        return Integer.parseInt(driver.findElement(amountOfEvents).getAttribute("innerText").replaceAll("[^0-9]", ""));
    }

    public String getSubFilterName(WebElement filter) {
        return filter.findElement(By.xpath(".//span[@class='text-overflow']")).getAttribute("innerText"); //работает для фильтров 2, и 3 уровней
    }

    public List<WebElement> getFirstLevelFiltersList() {
        return driver.findElements(firstLevelFilter);
    }

    public String getFirstLevelFilterName(WebElement filter) {
        return filter.findElement(By.xpath(".//span[@class='b-filterItem_name text-overflow']")).getAttribute("innerText");
    }

    public int getFirstLevelFilterCounter(WebElement filter) {
        return Integer.parseInt(filter.findElement(By.xpath(".//span[@class='b-filterItem_count f_count']")).getAttribute("innerText").replaceAll("[^0-9]", ""));
    }

    public List<WebElement> getSecondLevelFiltersList(WebElement filter) {
        return filter.findElements(By.xpath("./..//div[@class='b-filterItem_head']"));
    }

    public int getSecondLevelFilterCounter(WebElement filter) {
        return Integer.parseInt(filter.findElement(By.xpath(".//div[@class='b-filterItem_bvar-count f_count']")).getAttribute("innerText").replaceAll("[^0-9]", ""));
    }

    public String getSecondOrThirdLevelFilterName(WebElement filter) {
        return filter.findElement(By.xpath(".//span[@class='text-overflow']")).getAttribute("innerText");
    }

    public List<WebElement> getThirdLevelFiltersList(WebElement filter) {
        return filter.findElements(By.xpath("./..//div[@class='bvar_dropdown-item']"));
    }

    public int getThirdLevelFilterCounter(WebElement filter) {
        return Integer.parseInt(filter.findElement(By.xpath(".//div[@class='b-filterItem_bvar-count']")).getAttribute("innerText").replaceAll("[^0-9]", ""));
    }


    public void applyFilter(WebElement filter) {
        getFilterCheckbox(filter).click();
        driver.findElement(applyButton).click();
        utils.sleep(500);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
    }

    public void resetFilter() {
        driver.findElement(resetFilterButton).click();
        utils.sleep(500);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
        utils.sleep(500);
    }

    public List<WebElement> getListOfEvents() {
        return driver.findElements(event);
    }

    public WebElement getKEofEvent(WebElement event) {
        return event.findElement(By.xpath(".//div[@placement='right']"));
    }

    public boolean isEventsKEYellow(WebElement event) {
        if (getKEofEvent(event).getAttribute("class").equals("app-mailBox-item-link-favourit-icon active")) {
            return true;
        } else {
            return false;
        }
    }

    public int[] setNke(int amountOfKeToSet, List<WebElement> events) {
        int[] numberOfSetKE = new int[amountOfKeToSet];
        int counter = 0;
        for (int i = 0; i < events.size(); i++) {
            if (isEventsKEYellow(events.get(i)) == false) {
                getKEofEvent(events.get(i)).click();
                utils.sleep(300);
                numberOfSetKE[counter] = i;
                counter++;
            }
            if (counter == amountOfKeToSet) {
                break;
            }
        }
        return numberOfSetKE;
    }

    public void cleanSetKE (List<WebElement> events, int [] setKE) {
        for (int i=0; i<setKE.length; i++) {
            getKEofEvent(events.get(setKE[i])).click();
        }
    }

    public void showHundredEvents() {
        WebElement selectElem = driver.findElement(By.xpath("//select[@name='sizePage']"));
        Select select = new Select(selectElem);
        selectElem.click();
        select.selectByVisibleText("100 На странице");
        utils.sleep(200);
    }

    public int getAmountOfSetKE() {
        return Integer.parseInt(driver.findElement(amountOfSetKE).getAttribute("innerText").replaceAll("[^0-9]", ""));
    }

    public String getTypeOfEvent(WebElement event) {
        return event.findElement(By.xpath("./div[@class='app-mailBox-item-link-info app-mailBox-type']//div[@class='app-mailBox-item-link-info-direction-name-text']")).getAttribute("innerText");
    }

    public String getDescriptionOfEvent(WebElement event) {
        return event.findElement(By.xpath(".//div[text()='Description']/..//div[@class='app-mailBox-item-link-desc-item-info-text']")).getAttribute("innerText");
    }

    public String getDateOfEvent(WebElement event) {
        try {
            return event.findElement(By.xpath(".//div[text()='Дата']/../div[@class='app-mailBox-item-link-date-item-info']")).getAttribute("innerText");
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public String getTimeOfEvent(WebElement event) {
        try {
            return event.findElement(By.xpath(".//div[text()='Время']/../div[@class='app-mailBox-item-link-date-item-info']")).getAttribute("innerText");
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public WebElement findFilterByName(List<WebElement> filters, String name) {
        int number=-1;
        for (int i=0; i<filters.size(); i++){
            if (filters.get(i).findElement(By.xpath(".//span[@class='b-filterItem_name text-overflow']")).getAttribute("title").equals(name)) {
                number=i;
            }
        }
        if (number!=-1){
            return filters.get(number);
        }
        else {
            return null;
        }
    }

    public String searchRandomWord() {
        String [] wordsToCheck={"вечер", "утро", "деньги", "лом", "компьютер", "байт", "бит", "высок", "река", "фонд", "валюта", "банк", "12345", "8916", "8910", "8922", "1999", "1991", "777", "776", "3351", "0000001", "1111111", "0@gmail.com", "@@", "///", "**", "0$", "€", "?!", "!!!", "{}", "[]", "2+2", "Anna", "Jim", "Ben", "room", "mate", "police", "fire", "male", "female", "zoo", "DRUGS", "RUSSIA", "Rome", "lil", "meme", "food", "cage", "lord", "of the b", "have no", "Nexus", "mail.ru", "none", "calm"};
        String checkWord=wordsToCheck[utils.randomNubmer(wordsToCheck.length)];
        //String checkWord="1234";
        driver.findElement(descriptionSearchField).sendKeys(checkWord);
        driver.findElement(descriptionSearchField).sendKeys(Keys.ENTER);
        utils.sleep(1000);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
        utils.sleep(500);
        return checkWord;
    }

    public String getFromOfEvent(WebElement event) {
       if (isHaveFrom(event))
        {
            return event.findElement(By.xpath(".//div[text()='От']/..//div[@class='app-mailBox-item-link-info-user-name-text']")).getAttribute("innerText");
        }
        else {
            return null;
       }
    }

    public String getToOfEvent(WebElement event) {
        if (isHaveTo(event))
        {
            return event.findElement(By.xpath(".//div[text()='До']/..//div[@class='app-mailBox-item-link-info-user-name-text']")).getAttribute("innerText");
        }
        else {
            return null;
        }
    }
    public void refreshPage () {
        driver.navigate().refresh();
        utils.sleep(100);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
        utils.sleep(100);
    }
    public int getAmountOfPages () {
        return Integer.parseInt(driver.findElement(amountOfPages).getAttribute("innerText").replaceAll("[^0-9]", ""));
    }
}
