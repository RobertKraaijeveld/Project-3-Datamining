package Calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jls on 4/1/15.
 */
public class CustomDate {
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy:dd:MM HH:mm:ss");
    private Date date;

    public CustomDate() {
    }

    public CustomDate(final Date date) {
        this.date = date;
    }

    public String getDate() {
        return dateFormat.format(date).substring(0, 10).replaceAll("[^a-zA-Z0-9]", "");
    }

    public String getDateWithSplit() {
        return dateFormat.format(date).substring(0, 10).replaceAll("[^a-zA-Z0-9]", "-");
    }

    public String getTime() {
        return dateFormat.format(date).substring(11, 19).replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String grabDate(String str) {
        return new StringBuilder(grabYear(str)).append("-").append(grabMonthDay(str)).append("-").append(grabIntMonth(str)).toString().replaceAll("\\s", "");
    }

    public static String grabYear(String str) {
        str = str.replaceAll("[^0-9]+", "");
        str = str.substring(8, 12);
        return str;
    }

    public static String filterYear(String str) {
        str = str.substring(0, 4);
        return str;
    }

    public static String filterDay(String str) {
        str = str.substring(5, 7);
        return str;
    }

    public static String filterMonth(String str) {
        str = str.substring(8, 10);
        return str;
    }

    public static String filterHour(String str) {
        str = str.substring(0, 2);
        return str;
    }

    public static String filterMinute(String str) {
        str = str.substring(3, 5);
        return str;
    }

    public static String filterSecond(String str) {
        str = str.substring(6, 8);
        return str;
    }

    private static String convertMonth(String str) {
        String i = "";
        switch (str) {
            case "Jan ":
                i = "01 ";
                break;
            case "Feb ":
                i = "02 ";
                break;
            case "Mar ":
                i = "03 ";
                break;
            case "Apr ":
                i = "04 ";
                break;
            case "May ":
                i = "05 ";
                break;
            case "Jun ":
                i = "06 ";
                break;
            case "Jul ":
                i = "07 ";
                break;
            case "Aug ":
                i = "08 ";
                break;
            case "Sep ":
                i = "09 ";
                break;
            case "Oct ":
                i = "10 ";
                break;
            case "Nov ":
                i = "11 ";
                break;
            case "Dec ":
                i = "12 ";
                break;
        }
        return i;
    }

    public static String grabMonth(String str) {
        String[] months = {"Jan ", "Feb ", "Mar ", "Apr ", "May ", "Jun ", "Jul ", "Aug ", "Sep ", "Oct ", "Nov ", "Dec "};

        for (String month : months) {
            if (str.contains(month)) {
                str = month;
                break;
            }
        }
        return str;
    }

    public static String grabIntMonth(String str) {
        return convertMonth(grabMonth(str));
    }

    public static String grabMonthDay(String str) {
        str = str.replaceAll("[^0-9]+", "");
        str = str.substring(0, 2);
        return str;
    }

    public static String grabTime(String str) {
        str = str.replaceAll("[^:0-9]+", "");
        str = str.substring(2, 10);
        return str.trim();
    }

    public static void grabDay(String str) {
        String[] days = {"Mon ", "Tue ", "Wed ", "Thu ", "Fri ", "Sat ", "Sun "};

        for (String day : days) {
            if (str.contains(day)) {
                System.out.println(day);
                break;
            }
        }
    }
}

