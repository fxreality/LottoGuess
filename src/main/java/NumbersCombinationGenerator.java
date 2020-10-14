import java.util.*;

public class NumbersCombinationGenerator {

    List<Integer> generateCombinations(int maxNumber, int count) {
        Set<Integer> pickedStars = new HashSet<Integer>();
        do {
            int randomNumber = (int) (Math.random() * maxNumber) +1;
            pickedStars.add(randomNumber);
        } while(pickedStars.size() < count);
        List<Integer> intList = new ArrayList<Integer>(pickedStars);
        return sortList(intList);
    }

    private List<Integer> sortList(List<Integer> listOfIntegers) {
        Collections.sort(listOfIntegers, new Comparator<Integer>() {
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        return listOfIntegers;
    }
}
