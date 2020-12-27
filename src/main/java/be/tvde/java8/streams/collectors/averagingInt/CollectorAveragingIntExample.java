package be.tvde.java8.streams.collectors.averagingInt;

import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class CollectorAveragingIntExample {

   public static void main(String[] args) {
      final Double average = Instructors.getAll()
                                        .stream()
                                        .collect(
                                              Collectors.averagingInt(
                                                    Instructor::getYearsOfExperience
                                                                     )
                                                );

      System.out.println("Average year of experience: " + average);
   }
}
