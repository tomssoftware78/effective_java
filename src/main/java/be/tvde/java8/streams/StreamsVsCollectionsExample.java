package be.tvde.java8.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsVsCollectionsExample {

   public static void main(String[] args) {
      List<String> names = new ArrayList<>();
      names.add("mike");
      names.add("syed");
      names.add("rajeev");
      System.out.println("--------------");
      System.out.println(names);

      names.remove("syed");
      System.out.println("--------------");
      System.out.println(names);
      for (String name : names) {
         System.out.println(name);
      }

      System.out.println("=================");
      Stream<String> namesStream = names.stream();
      namesStream.forEach(System.out::println);

      final List<String> list = names.stream().filter(s->s.startsWith("m"))
                                         .collect(Collectors.toList());
      System.out.println(list);
   }
}
