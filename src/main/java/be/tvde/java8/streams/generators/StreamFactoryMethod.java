package be.tvde.java8.streams.generators;

import java.util.Random;
import java.util.stream.Stream;

public class StreamFactoryMethod {

   public static void main(String[] args) {
      //of
      final Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
      numbers.forEach(n -> System.out.print(n + " "));
      System.out.println("");

      final Stream<Integer> numbers1 = Stream.iterate(0, i->i + 2).limit(5);
      numbers1.forEach(n -> System.out.print(n + " "));
      System.out.println("");

      final Stream<Integer> numbers2 = Stream.generate(new Random()::nextInt).limit(5);
      numbers2.forEach(n -> System.out.print(n + " "));
      System.out.println("");
   }
}
