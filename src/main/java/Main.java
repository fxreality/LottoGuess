import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*
        Examples, how can you use the LottoGuess project - have fun !
         */

        //** Populate local text file with the dates when the draw happened **//
        DrawDates dd = new DrawDates();
        dd.populateDateOfDraws();

        //** Crawl all the numbers for Euromillions and store them to DynamoDB table **//
        LottoNumberCrawler lnc = new LottoNumberCrawler("https://www.swisslos.ch/en/euromillions/information/winning-numbers/winning-numbers.html");
        lnc.crawlNumbers();

        //** Generate combination to play on Euromillions **//
        NumbersCombinationGenerator ncg = new NumbersCombinationGenerator();
        //Numbers//
        List test = ncg.generateCombinations(50,5);
        System.out.println(test);
        //Stars//
        System.out.println(ncg.generateCombinations(12,2));

        CombinationApproval ca = new CombinationApproval(test);

        //Check if the combination is already happened in past - how many matched//
        System.out.println(ca.historyCheck());
        //Check the combinations if the average (example: 1,2,3,4,5 can be dropped) is to high or to low//
        System.out.println(ca.averageCheck(50));
    }
}