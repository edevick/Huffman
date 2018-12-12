import java.util.*;
import java.util.stream.Collectors;


    public class K

    {
        private static boolean ASC = true;
        private static boolean DESC = false;
        public static void main(String[] args)
        {

            // Creating dummy unsorted map
            Map<String, Integer> unsortMap = new HashMap<>();
            unsortMap.put("22", 55);
            unsortMap.put("33", 20);
            unsortMap.put("66", 20);
            unsortMap.put("77", 70);

            System.out.println("Before sorting......");
            printMap(unsortMap);

            System.out.println("After sorting ascending order......");
            Map<String, Integer> sortedMapAsc = sortByValue(unsortMap, ASC);
            printMap(sortedMapAsc);


            System.out.println("After sorting descending order......");
            Map<String, Integer> sortedMapDesc = sortByValue(unsortMap, DESC);
            printMap(sortedMapDesc);
        }

        private static Map<String, Integer> sortByValue(Map<String, Integer> unsortMap, final boolean order)
        {
            List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

            // Sorting the list based on values
            list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                    ? o1.getKey().compareTo(o2.getKey())
                    : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                    ? o2.getKey().compareTo(o1.getKey())
                    : o2.getValue().compareTo(o1.getValue()));
            return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        }

        private static void printMap(Map<String, Integer> map)
        {
            map.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
        }
}
