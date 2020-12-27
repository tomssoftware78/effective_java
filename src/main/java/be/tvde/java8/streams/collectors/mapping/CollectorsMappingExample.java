package be.tvde.java8.streams.collectors.mapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class CollectorsMappingExample {

   public static void main(String[] args) {
      //map
      List<String> namesList = Instructors.getAll()
                                          .stream()
                                          .map(Instructor::getName)
                                          .collect(Collectors.toList());
      namesList.forEach(name->System.out.print(name + " "));
      System.out.println("");

      namesList = Instructors.getAll()
                             .stream()
                             .collect(Collectors.mapping(Instructor::getName, Collectors.toList()));
      namesList.forEach(name->System.out.print(name + " "));
      System.out.println("");

      final Map<Integer, List<String>> instructorNamesByExperience = Instructors.getAll()
                                                            .stream()
                                                            .collect(
                                                                  Collectors.groupingBy(
                                                                        Instructor::getYearsOfExperience,
                                                                        Collectors.mapping(Instructor::getName, Collectors.toList())
                                                                                       )
                                                                    );
      System.out.println(instructorNamesByExperience);

   }

}
