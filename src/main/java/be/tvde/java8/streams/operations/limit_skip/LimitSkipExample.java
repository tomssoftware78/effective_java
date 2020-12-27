package be.tvde.java8.streams.operations.limit_skip;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LimitSkipExample {

   public static void main(String[] args) {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
      final List<Integer> list1 = numbers.stream()
                                         .limit(3)
                                         .collect(Collectors.toList());
      System.out.println(list1);

      final List<Integer> list2 = numbers.stream()
                                           .skip(2)
                                           .limit(4)
                                           .collect(Collectors.toList());
      System.out.println(list2);
   }
}
