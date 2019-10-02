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
import pages.workspacePage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class workspaceTest {
    private static WebDriver driver;
    loginPage objLogin;
    casesPage objCases;
    workspacePage objWorkspace;

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
        driver.quit();
    }

    @Test
    @Epic("Проверка фасетных фильтров")
    @Feature("Проверка фильтров первого и второго уровней")
    @Description("Проверка фильтров первого и второго уровней")
    public void firstSecondLevelFasetFilterTest() {
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        objCases.goToWorkspace();
        objWorkspace= new workspacePage(driver);
        int counerOfErrors=0;
        List <WebElement> firstLevelFiltersFirst=objWorkspace.getFirstLevelFiltersList();
        for (int i=0; i<firstLevelFiltersFirst.size()-1; i++) { //-1 это костыль для игнорирования Словарей
            List <WebElement> firstLevelFilters=objWorkspace.getFirstLevelFiltersList();
            if (objWorkspace.isHaveHiddenSubfilters(firstLevelFilters.get(i))) {
                objWorkspace.clickFilterArrow(firstLevelFilters.get(i));
                List<WebElement> subfiltersFirst=objWorkspace.getSecondLevelFiltersList(firstLevelFilters.get(i));
                for (int j=0; j<subfiltersFirst.size(); j++) {
                    List <WebElement> firstLevelFiltersCurrent=objWorkspace.getFirstLevelFiltersList();
                    List<WebElement> subfilters=objWorkspace.getSecondLevelFiltersList(firstLevelFiltersCurrent.get(i));
                    if (utils.randomNubmer(3)==0) {
                        int filterCounter=objWorkspace.getSecondLevelFilterCounter(subfilters.get(j));
                        objWorkspace.applyFilter(subfilters.get(j));
                        int amountOfEvents=objWorkspace.getAmountOfEvents();
                        if (filterCounter!=amountOfEvents){
                            Allure.addAttachment("Не совпало количество в гриде и на сайдбаре у фильтра " + objWorkspace.getFirstLevelFilterName(firstLevelFiltersCurrent.get(i)) + " "
                                    + objWorkspace.getSecondOrThirdLevelFilterName(subfilters.get(j)), objWorkspace.getFirstLevelFilterName(firstLevelFiltersCurrent.get(i)) + " "
                                    + objWorkspace.getSecondOrThirdLevelFilterName(subfilters.get(j)));
                            attachScreenshot();
                            counerOfErrors++;
                            System.out.println("Ахтунг");
                        }
                        objWorkspace.resetFilter();

                    }
                }
            }
            else {
                int filterCounter=objWorkspace.getFirstLevelFilterCounter(firstLevelFilters.get(i));
                objWorkspace.applyFilter(firstLevelFilters.get(i));
                int amountOfEvents=objWorkspace.getAmountOfEvents();
                if (filterCounter!=amountOfEvents){
                    Allure.addAttachment("Не совпало количество в гриде и на сайдбаре у фильтра " + objWorkspace.getFirstLevelFilterName(firstLevelFilters.get(i)), objWorkspace.getFirstLevelFilterName(firstLevelFilters.get(i)));
                    attachScreenshot();
                    counerOfErrors++;
                    System.out.println("Ахтунг");
                }
                objWorkspace.resetFilter();
            }
        }
        Assert.assertEquals(0, counerOfErrors);
    }

    @Test
    @Epic("Проверка фасетных фильтров")
    @Feature("Проверка фильтров третьего уровня")
    @Description("Проверка фильтров третьего уровня")
    public void ThirdLevelFasetFilterTestShit() {
        int counterOfErrors=0;
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        objCases.goToWorkspace();
        objWorkspace = new workspacePage(driver);
        List<WebElement> filters=objWorkspace.getFirstLevelFiltersList();
        WebElement categories=objWorkspace.findFilterByName(filters, "Категории");
        Assert.assertNotNull("Нет фильтра с таким именем", categories);
        objWorkspace.clickFilterArrow(categories);
        List<WebElement> categoriesSecondLevelFilters=objWorkspace.getSecondLevelFiltersList(categories);
        for (int i=0; i<categoriesSecondLevelFilters.size(); i++){
            List<WebElement> filtersCurrent=objWorkspace.getFirstLevelFiltersList();
            WebElement categoriesCurrent=objWorkspace.findFilterByName(filtersCurrent, "Категории");
            List<WebElement> categoriesSecondLevelFiltersCurrent=objWorkspace.getSecondLevelFiltersList(categoriesCurrent);
            if (objWorkspace.isHaveHiddenSubfilters(categoriesSecondLevelFiltersCurrent.get(i))) {
                objWorkspace.clickFilterArrow(categoriesSecondLevelFiltersCurrent.get(i));
                List<WebElement> thirdLevelFilters = objWorkspace.getThirdLevelFiltersList(categoriesSecondLevelFiltersCurrent.get(i));
                for (int j = 0; j < thirdLevelFilters.size(); j++) {
                    List<WebElement> filtersCurrentNew=objWorkspace.getFirstLevelFiltersList();
                    WebElement categoriesCurrentNew=objWorkspace.findFilterByName(filtersCurrentNew, "Категории");
                    List<WebElement> categoriesSecondLevelFiltersCurrentNew=objWorkspace.getSecondLevelFiltersList(categoriesCurrentNew);
                    List<WebElement> thirdLevelFiltersNew = objWorkspace.getThirdLevelFiltersList(categoriesSecondLevelFiltersCurrentNew.get(i));
                    //objWorkspace.clickFilterArrow(categoriesSecondLevelFiltersCurrent.get(i));
                    if (utils.randomNubmer(0) == 0) {
                        int sidebarCounter = objWorkspace.getThirdLevelFilterCounter(thirdLevelFiltersNew.get(j));
                        objWorkspace.applyFilter(thirdLevelFiltersNew.get(j));
                        int amountOfEvents=objWorkspace.getAmountOfEvents();
                        if (sidebarCounter!=amountOfEvents){
                            counterOfErrors++;
                            Allure.addAttachment("Разные значения на сайдбаре и в гриде", amountOfEvents + "в гриде "+ sidebarCounter + " на сайдбаре");
                            attachScreenshot();
                            objWorkspace.resetFilter();
                            System.out.println("Error");
                            List<WebElement> filtersCurrentNew1=objWorkspace.getFirstLevelFiltersList();
                            WebElement categoriesCurrentNew1=objWorkspace.findFilterByName(filtersCurrentNew1, "Категории");
                            List<WebElement> categoriesSecondLevelFiltersCurrentNew1=objWorkspace.getSecondLevelFiltersList(categoriesCurrentNew1);
                            objWorkspace.clickFilterArrow(categoriesSecondLevelFiltersCurrentNew1.get(i));
                        }
                        else {
                            objWorkspace.resetFilter();
                            List<WebElement> filtersCurrentNew1=objWorkspace.getFirstLevelFiltersList();
                            WebElement categoriesCurrentNew1=objWorkspace.findFilterByName(filtersCurrentNew1, "Категории");
                            List<WebElement> categoriesSecondLevelFiltersCurrentNew1=objWorkspace.getSecondLevelFiltersList(categoriesCurrentNew1);
                            objWorkspace.clickFilterArrow(categoriesSecondLevelFiltersCurrentNew1.get(i));
                        }
                    }
                }
            }
            else {
                int sidebarCounter = objWorkspace.getSecondLevelFilterCounter(categoriesSecondLevelFiltersCurrent.get(i));
                objWorkspace.applyFilter(categoriesSecondLevelFiltersCurrent.get(i));
                int amountOfEvents= objWorkspace.getAmountOfEvents();
                if (sidebarCounter!=amountOfEvents){
                    counterOfErrors++;
                    Allure.addAttachment("Разные значения на сайдбаре и в гриде", amountOfEvents + "в гриде "+ sidebarCounter + " на сайдбаре");
                    attachScreenshot();
                    objWorkspace.resetFilter();
                    System.out.println("Error");
                }
                else {
                    objWorkspace.resetFilter();
                }
            }
        }
        Assert.assertEquals("Больше одной ошибки", 0, counterOfErrors);
    }

    @Test //добавить проверку всех страниц, когда данных будет много
    @Epic("Поиск")
    @Feature("Поиск в Workspace по описанию")
    @Description("Поиск в Workspace по описанию")
    public void descriptionSearch() {
        int errorCounter = 0;
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        objCases.goToWorkspace();
        objWorkspace = new workspacePage(driver);
        objWorkspace.showHundredEvents();
        String checkword = objWorkspace.searchRandomWord();
        int amountOfPages = objWorkspace.getAmountOfPages();
        for (int n = 0; n < amountOfPages; n++) {
            List<WebElement> events = objWorkspace.getListOfEvents();
            System.out.println(events.size());
            for (int i = 0; i < events.size(); i++) {
                events.get(i).click();
                List<String> checkFields = new ArrayList<String>();
                String description = objWorkspace.getDescriptionOfEvent(events.get(i));
                System.out.println(description);
                if (!description.toLowerCase().contains(checkword.toLowerCase())) {
                    checkFields.add(description);
                    String from = objWorkspace.getFromOfEvent(events.get(i));
                    if (from != null) {
                        System.out.println(from);
                        checkFields.add(from);
                    }
                    String to = objWorkspace.getToOfEvent(events.get(i));
                    if (to != null) {
                        System.out.println(to);
                        checkFields.add(to);
                    }
                    int counter = 0;
                    for (int j = 0; j < checkFields.size(); j++) {
                        if (!checkFields.get(j).toLowerCase().contains(checkword.toLowerCase())) {
                            counter++;
                        }
                    }
                    if (counter == checkFields.size()) {
                        System.out.println("Error");
                        errorCounter++;
                        Allure.addAttachment("Неверный результат поиска", "Неверный результат поиска по слову " + checkword);
                        attachScreenshot();
                    }
                }
            }
            objWorkspace.nextPage();
        }
        Assert.assertEquals("Обнаружены ошибки", 0, errorCounter);
    }

    @Test
    @Epic("KE")
    @Feature("Проверка функционала КЕ")
    @Description("Проверка функционала КЕ")
    public void keyEvidenceTest() {
        objLogin = new loginPage();
        objLogin.loginPage(driver);
        objLogin.loginToAutoQA("maxim", "12345");
        objCases = new casesPage(driver);
        objCases.goToWorkspace();
        objWorkspace = new workspacePage(driver);
        objWorkspace.showHundredEvents();
        int amountOfYellowKE = objWorkspace.getAmountOfSetKE();
        List<WebElement> events = objWorkspace.getListOfEvents();
        int randNumber = utils.randomNubmer(5);
        System.out.println(randNumber);
        Allure.addAttachment("Количество установленных КЕ до установки и обновления страницы", "Количество установленных КЕ до");
        attachScreenshot();
        int[] setKE = objWorkspace.setNke(randNumber, events);
        objWorkspace.refreshPage();
        objWorkspace.showHundredEvents();
        Allure.addAttachment("Количество установленных КЕ после установки и обновления страницы, устанавливали "+randNumber+" штук", "Количество установленных КЕ после");
        attachScreenshot();
        List<WebElement> eventsAfterRefresh=objWorkspace.getListOfEvents();
        int amountOfYellowKEAfterSet = objWorkspace.getAmountOfSetKE();
        for (int i = 0; i < setKE.length; i++) {
            if (!objWorkspace.isEventsKEYellow(eventsAfterRefresh.get(setKE[i]))) {
                events.get(setKE[i]).click();
                Allure.addAttachment("Не сохранилась отметка КЕ после обновления страницы", "Ставили на фокусную запись на скриншоте");
                attachScreenshot();
            }
        }
        if (amountOfYellowKE != amountOfYellowKEAfterSet - randNumber) {
            Assert.assertEquals("Количество КЕ до обновления страницы не соответствует количеству после + количество установленных", amountOfYellowKE, amountOfYellowKEAfterSet - randNumber);
        }
        objWorkspace.cleanSetKE(eventsAfterRefresh, setKE);
    }
}
