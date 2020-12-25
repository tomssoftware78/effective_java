package be.tvde.java8.predicate;

import java.util.function.Predicate;

public class PredicateExample {

   public static void main(String[] args) {
      //if number is > 10 return true, else false
      Predicate<Integer> p1 = (i) -> i > 10;
      System.out.println(p1.test(100));

      //number is even (i%2 == 0)
      Predicate<Integer> p2 = (i) -> i % 2 == 0;
      System.out.println(p2.test(6));

      //i>10 && number is even
      System.out.println(p1.and(p2).test(12));
      System.out.println(p1.and(p2).test(11));

      //i<=10 || number is even
      System.out.println(p2.negate().or(p2).test(8 ));
   }
}
