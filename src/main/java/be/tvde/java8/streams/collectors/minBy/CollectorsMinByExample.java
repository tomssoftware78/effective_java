package be.tvde.java8.streams.collectors.minBy;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class CollectorsMinByExample {

   public static void main(String[] args) {
      //instructor with minimum years of experience

      Optional<Instructor> newestInstructor = Instructors.getAll()
                                                  .stream()
                                                  .min(
                                                        Comparator.comparing(Instructor::getYearsOfExperience)
                                                      );

      System.out.println(newestInstructor);

      newestInstructor = Instructors.getAll()
                                                      .stream()
                                                      .collect(
                                                            Collectors.minBy(
                                                                  Comparator.comparing(
                                                                        Instructor::getYearsOfExperience
                                                                                      )
                                                                            )
                                                              );
      System.out.println(newestInstructor);
   }
}
