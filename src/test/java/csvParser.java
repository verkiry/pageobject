import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileWriter;
import java.util.concurrent.TimeUnit;

import au.com.bytecode.opencsv.CSVWriter;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Epic;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class csvParser {
    @SuppressWarnings("resource")
    public  static void main(String[] args) throws Exception {
        String currentTime=getTime();
        System.out.println(currentTime);
        getAmountOfData.getAmount(currentTime);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());
        char[] timeStampChar = timeStamp.toCharArray();
        timeStampChar[10] = 'T';
        String timeResult = String.valueOf(timeStampChar);
        System.out.println(timeResult);
        //Build reader instance
        CSVReader reader = new CSVReader(new FileReader("C:\\Users\\Evgeny\\.jenkins\\workspace\\tester\\allure-report\\data\\suites.csv"), ',', '"', 1);
        //Read all rows at once
        List<String[]> allRows = reader.readAll();
        //Read CSV line by line and use the string array as you want
        for (String[] row : allRows) {
            System.out.println(Arrays.toString(row));
        }
        int sumTime = 0;
        for (int i = 0; i < allRows.size(); i++) {
            sumTime = sumTime + Integer.parseInt(allRows.get(i)[3]);
        }
        int sumTimeMinutes = sumTime / 1000 / 60;
        String time = String.valueOf(sumTimeMinutes);
        System.out.println(sumTimeMinutes);

        String csv = "C:\\Users\\Evgeny\\Documents\\trend1\\test.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(csv, true));
        String[] record = (currentTime + "," + time).split(",");
        writer.writeNext(record);
        writer.close();

        int amountOfTests = allRows.size();
        String amountOfTestsString = String.valueOf(amountOfTests);
        String csv1 = "C:\\Users\\Evgeny\\Documents\\trend1\\test1.csv";
        CSVWriter writer1 = new CSVWriter(new FileWriter(csv1, true));
        String[] record1 = (currentTime + "," + amountOfTestsString).split(",");
        writer1.writeNext(record1);
        writer1.close();

        /*String csv2 = "C:\\Users\\Evgeny\\Documents\\trend1\\test2.csv";
        CSVWriter writer2 = new CSVWriter(new FileWriter(csv2, true));
        String[] record2 = (currentTime + "," +data).split(",");
        writer2.writeNext(record2);
        writer2.close(); */
    }
    static String getTime (){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());
        char[] timeStampChar = timeStamp.toCharArray();
        timeStampChar[10] = 'T';
        String timeResult = String.valueOf(timeStampChar);
        return timeResult;
    }

}

