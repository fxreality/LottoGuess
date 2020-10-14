import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBHandler {
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
    static DynamoDB dynamoDB = new DynamoDB(client);

    static List<Map<String, AttributeValue>> scanLottoNumbersFromDynamoDB() {
        List<Map<String, AttributeValue>> result = new ArrayList<Map<String, AttributeValue>>();
        ScanRequest scanRequest = new ScanRequest().withTableName("lotto_numbers");
        ScanResult scanResult = client.scan(scanRequest);
        for (Map<String, AttributeValue> item : scanResult.getItems()){
            result.add(item);
        }
        return result;
    }

    static void pushDataToTable(String tableName, String date, String Numbers, String Stars) {
        Table table = dynamoDB.getTable(tableName);
        try {
            Item item = new Item().withPrimaryKey("date", date)
                    .withString("num_combination", Numbers)
                    .withString("stars", Stars);
            table.putItem(item);
        }
        catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());
        }
    }

    static Item getItem(String tableName, String date) {
        Table table = dynamoDB.getTable(tableName);
        Item item = null;
        try {
            item = table.getItem("date", date, "num_combination,stars", null);
            System.out.println(item.toJSONPretty());
        }
        catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }
        return item;
    }

}
