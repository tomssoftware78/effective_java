package be.tvde.java8.streams.operations.reduce;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamReduceExample {

   public static void main(String[] args) {
      final List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

      final Integer sum = integers.stream()
                                     .reduce(0, (a, b)->a + b);
      System.out.println(sum);

      final Integer product = integers.stream()
                                     .reduce(1, (a, b)->a * b);
      System.out.println(product);

      //reduce without identity --> returns an optional
      final Optional<Integer> optionalSum = integers.stream()
                                               .reduce((a, b)->a + b);

      if (optionalSum.isPresent()) {
         System.out.println(optionalSum);
      }
   }
}
