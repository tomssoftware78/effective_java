package be.tvde.java8.streams.operations.any_all_none_match;

import java.util.List;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class StreamsOperations {

   public static void main(String[] args) {
      final boolean b = Instructors.getAll()
                                   .stream()
                                   .map(Instructor::getCourses)
                                   .flatMap(List::stream)
                                   .anyMatch(s->s.startsWith("J")); //allMatch nonMatch
      System.out.println(b);
   }
}
