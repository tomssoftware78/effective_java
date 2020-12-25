package be.tvde.java8.biconsumer;

import java.util.function.BiConsumer;

public class BiConsumerExample {

   public static void main(String[] args) {
      //printing 2 numbers
      BiConsumer<Integer, Integer> biConsumer = (x, y) -> System.out.println("x: " + x + ", y: " + y);
      biConsumer.accept(17, 23);

      //sum of 2 numbers
      BiConsumer<Integer, Integer> sumConsumer = (x, y) -> System.out.println("x+y: " + (x+y));
      sumConsumer.accept(17, 23);

      BiConsumer<String, Integer> agePrinter = (name, age) -> System.out.println(name + " is " + age + " years old");
      agePrinter.accept("Tom", 42);
   }
}
