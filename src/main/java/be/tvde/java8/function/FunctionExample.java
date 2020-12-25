package be.tvde.java8.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class FunctionExample {

   public static void main(String[] args) {
      Function<Integer, Double> sqrt = n -> Math.sqrt(n);
      System.out.println("Square root of 64: " + sqrt.apply(64));
      System.out.println("Square root of 64: " + sqrt.apply(81));

      Function<String, String> lowerCaseFunction = s -> s.toLowerCase();
      System.out.println(lowerCaseFunction.apply("PROGRAMMing"));

      Function<String, String> addSuffix = s -> s.concat(" In Java");
      System.out.println(lowerCaseFunction.andThen(addSuffix).apply("PrograMMIng"));
      System.out.println(addSuffix.andThen(lowerCaseFunction).apply("PrograMMIng"));

      Function<List<Instructor>, Map<String, Integer>> mapFunction =
            (instructors -> {
               Map<String, Integer> map = new HashMap<>();
               instructors.forEach(instructor -> {
                     map.put(instructor.getName(), instructor.getYearsOfExperience());
               }
                                  );
               return map;
            });
      System.out.println(mapFunction.apply(Instructors.getAll()));
   }
}
