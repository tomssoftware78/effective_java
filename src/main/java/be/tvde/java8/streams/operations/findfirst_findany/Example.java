package be.tvde.java8.streams.operations.findfirst_findany;

import java.util.Comparator;
import java.util.Optional;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class Example {

   public static void main(String[] args) {
      final Optional<Instructor> instructorOptional = Instructors.getAll()
                                                  .stream()
                                                  .findAny();
      System.out.println(instructorOptional);

      final Optional<Instructor> firstOptional = Instructors.getAll()
                                                    .stream()
                                                    .findFirst();
      System.out.println(firstOptional);

      final Optional<Instructor> firstByNameOptional = Instructors.getAll()
                                                    .stream()
                                                    .sorted(Comparator.comparing(Instructor::getName))
                                                    .findFirst();
      System.out.println(firstByNameOptional);


   }
}
