package be.tvde.java8.streams;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class StreamExample2 {

   public static void main(String[] args) {
      //creating a map of names and courses of instructors who teach online
      // having more than 10 years of experience
      Predicate<Instructor> p1 = (i) -> i.isOnlineCourses();
      Predicate<Instructor> p2 = (i) -> i.getYearsOfExperience() > 10;

      final List<Instructor> instructors = Instructors.getAll();
      final Stream<Instructor> stream = instructors.stream();
      final Map<String, List<String>> map = stream.filter(p1)
                                                      .filter(p2)
                                                      .collect(Collectors.toMap(Instructor::getName, Instructor::getCourses));

      System.out.println(map);
   }
}
