package be.tvde.java8.streams.operations.filter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class FilterApp {

   public static void main(String[] args) {
      //return instructors sorted by their name and have more than 10 years of experience

      final List<Instructor> instructors = Instructors.getAll()
                                                  .stream()
                                                  .filter(instructor->instructor.getYearsOfExperience() > 10)
                                                  .sorted(Comparator.comparing(Instructor::getName))
                                                  .collect(Collectors.toList());
      instructors.forEach(System.out::println);
   }
}
