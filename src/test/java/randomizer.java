import org.openqa.selenium.WebElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
public class randomizer {
    @Test
    public static void main(String[] args) {
        String date1="17.01.2019 17:09:51";
        String date2="02.04.2019 14:11:02";
        DateFormat dateFormat=new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        Date date11=null;
        Date date22=null;
        try {
            date11=dateFormat.parse(date1);
        }
        catch (ParseException e){
        }
        try {
            date22=dateFormat.parse(date2);
        }
        catch (ParseException e){
        }
        if (date11.before(date22))
            System.out.println("Первая дата меньше");
        else
            System.out.println("Первая дата больше");
        String date111=dateFormat.format(date11);
        String date222=dateFormat.format(date22);
        System.out.println(date11);
        System.out.println(date22);
    }
}
