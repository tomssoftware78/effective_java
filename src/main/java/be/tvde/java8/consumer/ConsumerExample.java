package be.tvde.java8.consumer;

import java.util.function.Consumer;

public class ConsumerExample {

   public static void main(String[] args) {
      Consumer<String> c = (x) -> {
         System.out.println(x.length() + " the value of x: " + x);
      };

      Consumer<String> c1 = (x) -> {
         System.out.println("To uppercase: " + x.toUpperCase());
      };

      c.andThen(c1).accept("azerty");
   }
}
