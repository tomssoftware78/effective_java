package be.tvde.java8.streams.collectors.summingInt;

import java.util.IntSummaryStatistics;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class CollectorSummingIntExample {

   public static void main(String[] args) {
      //sum of years of experience of all instructors
      final Integer totalYearsOfExperience = Instructors.getAll()
                                         .stream()
                                         .collect(
                                               Collectors.summingInt(
                                                     Instructor::getYearsOfExperience
                                               )
                                         );
      System.out.println("Total years of experience: " + totalYearsOfExperience);


   }
}
