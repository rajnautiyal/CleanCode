package Playgraound;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UniqueNumberOfOccurrences {

    public static void main(String[] args) {

    }
    public static boolean uniqueOccurrences(int[] arr) {
      List<Long> values=  Arrays.asList(arr).stream().collect(Collectors.groupingBy(n->n,Collectors.counting())).values().stream().collect(Collectors.toList());
      Set<Long> valuesSet=new HashSet<>(values);
      return  values.size()==valuesSet.size();
    }
}
