import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoNumberCrawler {
    String linkToCrawlFrom;

    public LottoNumberCrawler(String linkToCrawlFrom) {
        this.linkToCrawlFrom = linkToCrawlFrom;
    }

    void crawlNumbers() {
        List<String> dates = getDates();
        if (!dates.isEmpty()) {
            dates.stream().forEach((date) -> {
                try {
                    saveCombination(date);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void saveCombination(String date) throws IOException {
        Document drawPage = crawlPage(linkToCrawlFrom, date);
        String numbers = getNumbers(drawPage);
        String stars = getStars(drawPage);
        DBHandler dbh = new DBHandler();
        dbh.pushDataToTable("lotto_numbers", drawPage.getElementById("formattedFilterDate").val(), numbers, stars);
    }

    private Document crawlPage(String link, String date) throws IOException {
        Map<String, String> params = getParamsMap(date);
        Connection connection = Jsoup.connect(link);
        connection.userAgent("Mozilla").timeout(1500).data(params);
        Document domContent = connection.post();
        return domContent;
    }

    private Map<String, String> getParamsMap(String date) {
        Map<String, String> params = new HashMap();
        params.put("formattedFilterDate", date);
        params.put("filterDate", date);
        params.put("currentDate",new Date().toString());
        return params;
    }

    private String getNumbers(Document domContent) {
        String numbers = "";
        Elements childrenSpan = domContent.select(".quotes__game > .euro-millions-logo +" +
                " .actual-numbers___body > ul.actual-numbers__numbers > " +
                ".actual-numbers__number___normal > span.transform__center");
        for (Element numberSpan : childrenSpan) {
            numbers += numberSpan.text()+",";
        }
        return numbers.substring(0, numbers.length() - 1);
    }

    private String getStars(Document domContent) {
        String stars = "";
        Elements childrenSpan = domContent.select(".quotes__game > .euro-millions-logo +" +
                " .actual-numbers___body > ul.actual-numbers__numbers > " +
                ".actual-numbers__number___superstar > span.transform__center");
        for (Element numberSpan : childrenSpan) {
            stars += numberSpan.text()+",";
        }
        return stars.substring(0, stars.length() - 1);
    }

    private List<String> getDates() {
        FileHandler fh = new FileHandler();
        DBHandler dbh = new DBHandler();
        List<String> dates = fh.readFileContent(DrawDates.fileName);
        List<Map<String, AttributeValue>> fullTableScan = dbh.scanLottoNumbersFromDynamoDB();
        for (Map<String, AttributeValue> item : fullTableScan) {
            String itemDate = item.get("date").getS();
            if (dates.contains(itemDate)) {
                dates.remove(itemDate);
            }
        }
        return dates;
    }
}
