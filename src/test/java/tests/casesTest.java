package tests;

import io.qameta.allure.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import pages.casesPage;

import pages.loginPage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class casesTest {
    private static WebDriver driver;
    loginPage objLogin;
    casesPage objCases;

    @Attachment
    public byte[] attachScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Before
    public void before() {

        System.setProperty("webdriver.chrome.driver", "/work/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://192.168.4.222/login");
    }

    @After
    public void after() {
//System.out.print ("Login success");
        driver.quit();
    }

    @Test
    @Epic("Проверка сортировки по датам")
    @Feature("Сортировка по дате создания дела в Дела по убыванию")
    @Description("Сортировка по дате создания дела в Дела по убыванию")
    public void CasesCreationDateSortAscending() {
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        System.out.println(objCases.getAmountOfCases());
        objCases.SortCaseCreationDatesAscending();
        List<WebElement> currentCases = objCases.getListOfCases();
        List<String> casesCreationDates = new ArrayList<String>();
        for (int i = 0; i < currentCases.size(); i++) {
            casesCreationDates.add(objCases.getCraetionDateOfCase(currentCases.get(i)));
        }
        String[] checkResult = utils.checkSortAscending(casesCreationDates);
        if (checkResult[0] == "true") {
            Allure.addAttachment("Верная сортировка по возрастанию", "Passed");
            attachScreenshot();
        } else {
            currentCases.get(Integer.parseInt(checkResult[1])).click();
            utils.sleep(800);
            Allure.addAttachment("Неверная сортировка по возрастанию", objCases.getCraetionDateOfCase(currentCases.get(Integer.parseInt(checkResult[1]))) +
                    " не раньше, чем " + objCases.getCraetionDateOfCase(currentCases.get(Integer.parseInt(checkResult[1]) + 1)));
            attachScreenshot();
        }
        Assert.assertEquals("true", checkResult[0]);
    }

    @Test
    @Epic("Проверка сортировки по датам")
    @Feature("Сортировка по дате создания дела в Дела по возрастанию")
    @Description("Сортировка по дате создания дела в Дела по возрастанию")
    public void CasesCreationDateSortDescending() {
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        System.out.println(objCases.getAmountOfCases());
        objCases.SortCaseCrearionDateDescending();
        List<WebElement> currentCases = objCases.getListOfCases();
        List<String> casesCreationDates = new ArrayList<String>();
        for (int i = 0; i < currentCases.size(); i++) {
            casesCreationDates.add(objCases.getCraetionDateOfCase(currentCases.get(i)));
        }
        String[] checkResult = utils.checkSortDescending(casesCreationDates);
        if (checkResult[0] == "true") {
            Allure.addAttachment("Верная сортировка по убыванию", "Passed");
            attachScreenshot();
        } else {
            currentCases.get(Integer.parseInt(checkResult[1])).click();
            utils.sleep(800);
            Allure.addAttachment("Неверная сортировка по убыванию", objCases.getCraetionDateOfCase(currentCases.get(Integer.parseInt(checkResult[1]))) +
                    " не позже, чем " + objCases.getCraetionDateOfCase(currentCases.get(Integer.parseInt(checkResult[1]) + 1)));
            attachScreenshot();
        }
        Assert.assertEquals("true", checkResult[0]);
    }

    @Test
    @Epic("Работа с делами")
    @Feature("Добавление дела")
    @Description("Добавление дела")
    public void addNewCase() {
        String number="123";
        String name="123";
        Boolean caseAddSuccess=false;
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        int amountOfCasesBeforeAdding=Integer.parseInt(objCases.getAmountOfCases());
        objCases.createNewCase(number, name);
        int amountOfCasesAfterAdding=Integer.parseInt(objCases.getAmountOfCases());
        List<WebElement> cases=objCases.getListOfCases();
        for (int i=0; i<cases.size(); i++) {
            if (objCases.getNumberOfCase(cases.get(i)).equals(number) && objCases.getNameOfCase(cases.get(i)).equals(name)) {
                caseAddSuccess=true;
                break;
            }
            else {
                caseAddSuccess=false;
            }
        }
        if (amountOfCasesAfterAdding==amountOfCasesBeforeAdding+1 && caseAddSuccess) {
            Allure.addAttachment("Дело успешно добавлено", number+" "+name );
            attachScreenshot();
            Assert.assertEquals(true, caseAddSuccess);
            System.out.println("Успех");
        }
        else {
            Allure.addAttachment("Не удалось добавить дело", number+" "+name );
            attachScreenshot();
            Assert.assertEquals(true, caseAddSuccess);
            System.out.println("Провал");
        }
    }
    @Test
    @Epic("Работа с делами")
    @Feature("Удаление")
    @Description("Удаление дела")
    public void deleteAddedCase() {
        String number="123";
        String name="123";
        Boolean caseDeleteSuccess=false;
        int counter=0;

        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        int amountOfCasesBeforeDeleting=Integer.parseInt(objCases.getAmountOfCases());
        List<WebElement> cases=objCases.getListOfCases();
        attachScreenshot();
        objCases.deleteCaseNamed(number, name, cases);
        List<WebElement> casesAfterDeleting=objCases.getListOfCases();
        System.out.println(casesAfterDeleting.size());
        int amountOfCasesAfterDeleting=Integer.parseInt(objCases.getAmountOfCases());
        for (int i=0; i<casesAfterDeleting.size(); i++) {
            if (!objCases.getNumberOfCase(casesAfterDeleting.get(i)).equals(number) && !objCases.getNameOfCase(casesAfterDeleting.get(i)).equals(name)) {
              counter++;
            }
        }
        System.out.println(counter);
        if (counter==casesAfterDeleting.size()){
            caseDeleteSuccess=true;
        }
        if (amountOfCasesAfterDeleting==amountOfCasesBeforeDeleting-1 && caseDeleteSuccess) {
            Allure.addAttachment("Дело успешно удалено", number+" "+name );
            attachScreenshot();
            Assert.assertEquals(true, caseDeleteSuccess);
            System.out.println("Успех");
        }
        else {
            Allure.addAttachment("Не удалось удалить дело", number+" "+name );
            attachScreenshot();
            Assert.assertEquals(true, caseDeleteSuccess);
            System.out.println("Провал");
        }
    }

    @Test
    @Epic("Работа с делами")
    @Feature("Поиск в Дела")
    @Description("Поиск в Дела")
    public void searchInCases() {
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        List<WebElement> cases=objCases.getListOfCases();
        String caseNumberToSearch=objCases.getNumberOfCase(cases.get(utils.random(cases)));
        objCases.caseSearch(caseNumberToSearch);
        List<WebElement> casesAfterSearch=objCases.getListOfCases();
        if (objCases.isNumberSearchCorrect(casesAfterSearch, caseNumberToSearch)){
            Allure.addAttachment("Поиск работает корректно", caseNumberToSearch);
            attachScreenshot();
            Assert.assertEquals(true, objCases.isNumberSearchCorrect(casesAfterSearch, caseNumberToSearch));
            System.out.println("Успех");
        }
        else {
            Allure.addAttachment("Проблемы с поиском", caseNumberToSearch);
            attachScreenshot();
            Assert.assertEquals(true, objCases.isNumberSearchCorrect(casesAfterSearch, caseNumberToSearch));
            System.out.println("Провал");
        }
    }
}
