package be.tvde.java8.streams.collectors.joining;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class JoiningExample {

   public static void main(String[] args) {
      final String s = Stream.of("A", "B", "C", "D").collect(Collectors.joining());
      System.out.println(s);

      final String s1 = Stream.of("A", "B", "C", "D").collect(Collectors.joining("-"));
      System.out.println(s1);

      final String s2 = Stream.of("A", "B", "C", "D").collect(Collectors.joining("-", "[", "]"));
      System.out.println(s2);

      //instructors names separated by ' and prefix { and suffix }
      final String names = Instructors.getAll()
                                        .stream()
                                        .map(Instructor::getName)
                                        .collect(Collectors.joining("'", "{", "}"));
      System.out.println(names);
   }
}
