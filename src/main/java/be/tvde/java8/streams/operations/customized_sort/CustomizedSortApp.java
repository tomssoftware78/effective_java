package be.tvde.java8.streams.operations.customized_sort;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class CustomizedSortApp {

   public static void main(String[] args) {
      final List<Instructor> list = Instructors.getAll()
                                                  .stream()
                                                  .sorted(Comparator.comparing(Instructor::getName))
                                                  .collect(Collectors.toList());
      list.forEach(System.out::println);
      System.out.println();

      final List<Instructor> list1 = Instructors.getAll()
                                                  .stream()
                                                  .sorted(Comparator.comparing(Instructor::getName).reversed())
                                                  .collect(Collectors.toList());
      list1.forEach(System.out::println);
   }
}
