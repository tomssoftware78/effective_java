package be.tvde.java8.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class StreamExample {

   public static void main(String[] args) {
      final List<Integer> numbers = Arrays.asList(2, 3, 4, 5, 6);
      final List<Integer> squares = numbers.stream().map(x->x * x).collect(Collectors.toList());
      System.out.println(squares);

      List<String> names = Arrays.asList("Tom","Ted", "Ron", "Jo");
      final List<String> tNames = names.stream().filter(s->s.startsWith("T")).collect(Collectors.toList());
      System.out.println(tNames);

      final List<String> sortedList = names.stream().sorted().collect(Collectors.toList());
      System.out.println(sortedList);

      final Integer sum = numbers.stream().reduce(0, (a, b)->a + b);
      System.out.println("Sum: " + sum);

      Predicate<Instructor> p1 = (i) -> i.isOnlineCourses();
      Predicate<Instructor> p2 = (i) -> i.getYearsOfExperience() > 10;

      final List<Instructor> instructors = Instructors.getAll();
      final Stream<Instructor> stream = instructors.stream();
      final Map<String, List<String>> map = stream
                                                       .filter(p1)
                                                       .filter(p2)
                                                       .collect(Collectors.toMap(Instructor::getName, Instructor::getCourses));
      System.out.println(map);

      IntStream.iterate(1, n ->n + 1)
               .skip(Integer.parseInt("2"))
               .limit(Integer.parseInt("7"))
               .forEach(System.out::println);
   }
}
