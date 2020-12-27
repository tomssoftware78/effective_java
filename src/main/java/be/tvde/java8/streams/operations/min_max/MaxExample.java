package be.tvde.java8.streams.operations.min_max;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MaxExample {

   public static void main(String[] args) {
      final List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

      //max using stream max function
      final Optional<Integer> maxNumber = numbers.stream().max(Integer::compareTo);
      System.out.println(maxNumber);

      final Integer maxNumber2 = numbers.stream().reduce(0, (x, y)->x > y ? x : y);
      System.out.println(maxNumber2);

      final Optional<Integer> maxNumber3 = numbers.stream().reduce((x, y)->x > y ? x : y);
      System.out.println(maxNumber3);

      final Optional<Integer> maxNumber4 = numbers.stream().reduce(Integer::max);
      System.out.println(maxNumber4);
   }
}
