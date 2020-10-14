import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CombinationApproval {

    private List<Integer> combination;
    private int count;
    public CombinationApproval(List<Integer> combination) {
        this.combination = combination;
        this.count = combination.size();
    }

    int averageCheck(int maxNumber) {
        int average = 0;
        for (int number : this.combination) {
            average += number;
        }
        return (average/this.count)-(maxNumber/2);
    }

    public int historyCheck() {
        int level = 0;
        DBHandler dbh = new DBHandler();
        List<Map<String, AttributeValue>> fullTableScan = dbh.scanLottoNumbersFromDynamoDB();
        for (Map<String, AttributeValue> item : fullTableScan) {
            List<Integer> integerList = commonIntegers(item);
            level = integerList.size()>level?integerList.size():level;
        }
        return level;
    }

    private List<Integer> commonIntegers(Map<String, AttributeValue> item) {
        List<String> itemCombination = Arrays.asList((item.get("num_combination").getS().split(",")));
        List<Integer> integerList = new ArrayList<>();
        for(String s : itemCombination) integerList.add(Integer.valueOf(s));
        integerList.retainAll(this.combination);
        return integerList;
    }
}
