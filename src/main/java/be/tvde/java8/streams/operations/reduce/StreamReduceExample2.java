package be.tvde.java8.streams.operations.reduce;

import java.util.Optional;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class StreamReduceExample2 {

   public static void main(String[] args) {
      //instructor who has the highest years of experience
      final Optional<Instructor> instructor = Instructors.getAll()
                                                     .stream()
                                                     .reduce((s1, s2)->{
                                                        if (s1.getYearsOfExperience() > s2.getYearsOfExperience()) {
                                                           return s1;
                                                        } else {
                                                           return s2;
                                                        }
                                                     });
      System.out.println(instructor);
   }
}
