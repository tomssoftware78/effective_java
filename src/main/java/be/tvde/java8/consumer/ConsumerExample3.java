package be.tvde.java8.consumer;

import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class ConsumerExample3 {

   public static void main(String[] args) {
      IntConsumer intConsumer = (i) -> System.out.println(i*10);
      intConsumer.accept(17);

      LongConsumer longConsumer = (l) -> System.out.println(l*10l);
      longConsumer.accept(17l);

      DoubleConsumer doubleConsumer = (d) -> System.out.println(d * 10);
      doubleConsumer.accept(17.5);
   }
}
