package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.utils;

import java.util.List;

public class casesPage {
   WebDriver driver;
   By amountOfCases=By.xpath("//span[@class='app-content-title-count']");
   By workspaceButton=By.xpath("//span[text()='Рабочее пространство']");
   By cases=By.xpath("//div[@class='eventsContent-link']");
   By caseNumberSortButton=By.xpath("//div[@class='eventsHeader-item itemEvent'][1]//div[@class='eventsHeader-label']");
   By caseNameSortButton=By.xpath("//div[@class='eventsHeader-item itemEvent'][2]//div[@class='eventsHeader-label']");
   By caseNoteSortButton=By.xpath("//div[@class='eventsHeader-item itemEvent'][3]//div[@class='eventsHeader-label']");
   By caseCreationDateSortButton=By.xpath("//div[@class='eventsHeader-item itemEvent'][4]//div[@class='eventsHeader-label']");
   By caseSearchField=By.id("flt");
   By addCaseButton=By.xpath("//div[@class='dataTableSort-addTrigger']");
   By caseNumberFieldSidebar=By.id("editableNumber");
   By caseNameFieldSidebar=By.id("editableName");
   By caseDepartmentFieldSidebar=By.id("editableDepartment");
   By addCaseButtonBlue=By.id("btn-case");
   By deleteCaseButton=By.xpath("//button[text()='Удалить дело']");
   By workspaceThreeDotsButton=By.xpath("//button[text()='Рабочее пространство']");
   By threeDots=By.xpath("//div[@class='barMenu-trigger']");

   public casesPage(WebDriver driver){
       this.driver=driver;
   }

   public String getAmountOfCases() {
       return driver.findElement(amountOfCases).getAttribute("innerText");
   }
   public List <WebElement> getListOfCases() {
       List<WebElement> cases=driver.findElements(By.xpath("//div[@class='eventsContent-link']"));
       return cases;
   }
   public String getNumberOfCase(WebElement element) {
       return element.findElement(By.xpath(".//div[@class='eventsItemChild itemEvent itemEventData'][1]//div[@class='text-overflow']")).getAttribute("innerText");
   }
   public String getNameOfCase(WebElement element) {
       return element.findElement(By.xpath(".//div[@class='eventsItemChild itemEvent itemEventData'][2]//div[@class='text-overflow']")).getAttribute("innerText");
   }
    public String getNoteOfCase(WebElement element) {
        return element.findElement(By.xpath(".//div[@class='eventsItemChild itemEvent itemEventData'][3]//div[@class='text-overflow']")).getAttribute("innerText");
    }
    public String getCraetionDateOfCase(WebElement element) {
        return element.findElement(By.xpath(".//div[@class='eventsItemChild itemEvent itemEventData'][4]//div[@class='text-overflow']")).getAttribute("innerText");
    }
    public void SortCaseCreationDatesAscending() {
       driver.findElement(caseCreationDateSortButton).click();
    }
    public void SortCaseCrearionDateDescending() {
        driver.findElement(caseCreationDateSortButton).click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
        }
        driver.findElement(caseCreationDateSortButton).click();
    }
    public void caseSearch (String caseName) {
       driver.findElement(caseSearchField).sendKeys(caseName);
       driver.findElement(caseSearchField).sendKeys(Keys.ENTER);
    }
    public void addCaseButtonClick () {
       driver.findElement(addCaseButton).click();
    }
    public void addCaseNumberOnSidebar (String name) {
       driver.findElement(caseNumberFieldSidebar).sendKeys(name);
    }
    public void addCaseNameOnSidebar (String number) {
       driver.findElement(caseNameFieldSidebar).sendKeys(number);
    }
    public void addCaseDepartmentOnSidebar (String department){
       driver.findElement(caseDepartmentFieldSidebar).sendKeys(department);
    }
    public void createNewCase (String number, String name) {
       driver.findElement(addCaseButton).click();
       driver.findElement(caseNumberFieldSidebar).sendKeys(number);
       driver.findElement(caseNameFieldSidebar).sendKeys(name);
       driver.findElement(addCaseButtonBlue).click();
       utils.sleep(700);
       WebDriverWait wait = new WebDriverWait(driver, 60);
       wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
    }
    public void deleteCaseNamed (String number, String name, List<WebElement> cases) {
       for (int i=0; i<cases.size(); i++) {
           if (getNumberOfCase(cases.get(i)).equals(number) && getNameOfCase(cases.get(i)).equals(name)) {
               System.out.println("Ура");
               cases.get(i).click();
               driver.findElement(threeDots).click();
               utils.sleep(500);
               driver.findElement(deleteCaseButton).click();
               Alert alert= (new WebDriverWait(driver,10)).until(ExpectedConditions.alertIsPresent());
               alert.accept();
               utils.sleep(700);
               WebDriverWait wait = new WebDriverWait(driver, 60);
               wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
               break;
           }
           else{
               System.out.println("dfgsdfg");
           }
       }
    }
    public boolean isNumberSearchCorrect (List <WebElement> cases, String number){
       int counter=0;
       for (int i=0; i<cases.size(); i++) {
           if (getNumberOfCase(cases.get(i)).toLowerCase().contains(number.toLowerCase())) {
               counter++;
           }
       }
       if (counter==cases.size()){
           return true;
       }
       else
           return false;
    }
    public void goToWorkspace () {
       driver.findElement(workspaceButton).click();
       utils.sleep(500);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/wa-root/wa-wait/div/div/div")));
    }
}
