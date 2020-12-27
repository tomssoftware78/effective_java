package be.tvde.java8.streams.collectors.maxBy;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class CollectorsMaxByExample {

   public static void main(String[] args) {
      Optional<Instructor> oldestInstructor = Instructors.getAll()
                                                  .stream()
                                                  .max(
                                                        Comparator.comparing(Instructor::getYearsOfExperience)
                                                      );
      System.out.println(oldestInstructor);

      oldestInstructor = Instructors.getAll()
                                                      .stream()
                                                      .collect(
                                                            Collectors.maxBy(
                                                                  Comparator.comparing(Instructor::getYearsOfExperience)
                                                                            )
                                                              );

      System.out.println(oldestInstructor);
   }
}
