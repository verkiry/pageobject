package tests;

import org.openqa.selenium.WebElement;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class utils {
    public static String[] checkSortAscending(List<String> dates) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int counter = 0;
        int problemNumber = 0;
        String[] result = new String[2];
        for (int i = 0; i < dates.size() - 1; i++) {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = dateFormat.parse(dates.get(i));
            } catch (ParseException e) {
            }
            try {
                date2 = dateFormat.parse(dates.get(i + 1));
            } catch (ParseException e) {
            }
            if (date1.before(date2) == true | date1.equals(date2)) {
                counter++;
            } else {
                problemNumber = i;
            }

        }
        if (counter == dates.size() - 1) {
            result[0] = "true";
        } else {
            result[0] = "false";
            result[1] = Integer.toString(problemNumber);
        }
        return result;
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
        }
    }

    public static String[] checkSortDescending(List<String> dates) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int counter = 0;
        int problemNumber = 0;
        String[] result = new String[2];
        for (int i = 0; i < dates.size() - 1; i++) {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = dateFormat.parse(dates.get(i));
            } catch (ParseException e) {
            }
            try {
                date2 = dateFormat.parse(dates.get(i + 1));
            } catch (ParseException e) {
            }
            if (date2.before(date1) == true | date2.equals(date1)) {
                counter++;
            } else {
                problemNumber = i;
            }

        }
        if (counter == dates.size() - 1) {
            result[0] = "true";
        } else {
            result[0] = "false";
            result[1] = Integer.toString(problemNumber);
        }
        return result;
    }

    public static int random(List<WebElement> cases) {
        int randNumber = (int) (Math.random() * cases.size());
        return randNumber;
    }

    public static int randomNubmer(int max) {
        int randNumber = (int) (Math.random() * max);
        return randNumber;
    }
}

