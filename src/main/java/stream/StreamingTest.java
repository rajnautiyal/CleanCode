package stream;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamingTest {
    public static void main(String[] args) {
        Stream<Integer> stream=Stream.of(5, 13, 4,
                21, 13, 27,
                2, 59, 59, 34);
        findDuplicate(stream);

    }

    public static  void findDuplicate( Stream<Integer> integerStream){
        Set<Integer> nonDuplicat=new HashSet<>();
       List<Integer> result =integerStream.filter(no->!nonDuplicat.add(no)).collect(Collectors.toList());
        result.forEach(n-> System.out.println(n));
    }
}
