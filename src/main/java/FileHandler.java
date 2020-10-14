import com.google.gson.Gson;

import java.io.*;
import java.util.List;

public class FileHandler {
    boolean createFile(String fileName) {
        Boolean fileCreated = false;
        try {
            File myObj = new File(fileName);
            fileCreated = myObj.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return fileCreated;
    }

    void writeToFile(String fileName, String content) {
        if (!createFile(fileName)) {
            try {
                FileWriter myWriter = new FileWriter(fileName);
                myWriter.write(content);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    List<String> readFileContent(String fileName) {
        List content = null;
        Gson gson = new Gson();

        try (Reader reader = new FileReader(fileName)) {
            content = gson.fromJson(reader, List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}