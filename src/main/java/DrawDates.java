import com.google.gson.Gson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DrawDates {

    public static String fileName = "dates.txt";
    private String dFormat = "Y-MM-dd";
    private String start = "2020-10-01";
    //private String start = "2005-01-01";

    void populateDateOfDraws() {
        List<String> drawDays = drawDates();
        FileHandler fh = new FileHandler();
        Collections.sort(drawDays);
        if (fh.createFile(fileName) || !drawDays.equals(fh.readFileContent(fileName))) {
            String json = new Gson().toJson(drawDays);
            fh.writeToFile(fileName, json);
        }
    }

    private List<String> drawDates() {
        List<String> drawDays = new ArrayList<>();
        DateTime startDate = dateFormatter(start);
        DateTime endDate = dateFormatter(currentDate());

        while (startDate.isBefore(endDate)){
            if (startDate.getDayOfWeek() == DateTimeConstants.FRIDAY || startDate.getDayOfWeek() == DateTimeConstants.TUESDAY){
                drawDays.add(formatDateForSwisslos(startDate.toString(dFormat)));
            }
            startDate = startDate.plusDays(1);
        }
        return drawDays;
    }

    private DateTime dateFormatter(String dateString) {
        DateTimeFormatter pattern = DateTimeFormat.forPattern(dFormat);
        DateTime resultDate = pattern.parseDateTime(dateString);
        return resultDate;
    }

    private String currentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dFormat);
        String now = simpleDateFormat.format(new Date());
        return now;
    }

    private String formatDateForSwisslos(String date) {
        String[] dateParts = date.split("-");
        return dateParts[2] + "." + dateParts[1] + "." + dateParts[0];
    }
}
