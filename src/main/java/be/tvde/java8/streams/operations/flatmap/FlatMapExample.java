package be.tvde.java8.streams.operations.flatmap;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class FlatMapExample {

   public static void main(String[] args) {
      final Set<String> courses = Instructors.getAll()
                                             .stream()
                                             .map(Instructor::getCourses)
                                             .flatMap(List::stream)
                                             .collect(Collectors.toSet());

      System.out.println(courses);
   }
}
