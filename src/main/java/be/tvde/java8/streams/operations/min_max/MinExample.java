package be.tvde.java8.streams.operations.min_max;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MinExample {
   public static void main(String[] args) {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

      //max using stream max function
      final Optional<Integer> minNumber = numbers.stream().min(Integer::compareTo);
      System.out.println(minNumber);

      final Integer minNumber2 = numbers.stream().reduce(Integer.MAX_VALUE, (x, y)->x < y ? x : y);
      System.out.println(minNumber2);

      final Optional<Integer> minNumber3 = numbers.stream().reduce((x, y)->x < y ? x : y);
      System.out.println(minNumber3);

      final Optional<Integer> minNumber4 = numbers.stream().reduce(Integer::min);
      System.out.println(minNumber4);
   }
}
