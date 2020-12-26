package be.tvde.java8.streams.operations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class MapExample {

   public static void main(String[] args) {
      final List<String> names = Instructors.getAll()
                                            .stream()
                                            .map(Instructor::getName)
                                            .map(String::toUpperCase)
                                            .collect(Collectors.toList());

      System.out.println(names);
   }
}
