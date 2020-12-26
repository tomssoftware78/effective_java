package be.tvde.java8.streams.operations.distinct_count_sorted;

import java.util.List;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class StreamsOperations {

   public static void main(String[] args) {
      //count
      final long count = Instructors.getAll()
                                    .stream()
                                    .map(Instructor::getCourses)
                                    .flatMap(List::stream)
                                    .count();
      System.out.println(count);

      //distinct
      final long distinctCount = Instructors.getAll()
                                     .stream()
                                     .map(Instructor::getCourses)
                                     .flatMap(List::stream)
                                     .distinct()
                                     .count();
      System.out.println(distinctCount);

      //sorted
      final List<String> list = Instructors.getAll()
                                              .stream()
                                              .map(Instructor::getCourses)
                                              .flatMap(List::stream)
                                              .distinct()
                                              .sorted()
                                              .collect(Collectors.toList());

      System.out.println(list);
   }
}
