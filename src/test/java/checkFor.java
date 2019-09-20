import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import au.com.bytecode.opencsv.CSVReader;

public class checkFor {
    public static void main(String[] args) {
        int a;
        for (a=0; a<10; a++){
        }
        System.out.println(a);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(Calendar.getInstance().getTime());
        char[] timeStampChar = timeStamp.toCharArray();
        timeStampChar[10]='T';
        String timeResult=String.valueOf(timeStampChar);
        System.out.println(timeResult);
    }

}
