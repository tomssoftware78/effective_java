package be.tvde.java8.bipredicate;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class BiPredicateExample {

   public static void main(String[] args) {
      List<Instructor> instructors = Instructors.getAll();

      BiPredicate<Boolean, Integer> p = (online, experience) -> {
         return online==Boolean.TRUE && experience > 10;
      };

      BiConsumer<String, List<String>> biConsumer = (name, courses) -> {
         System.out.println("name: " + name + ", courses: " + courses);
      };

      instructors.forEach(instructor -> {
         if (p.test(instructor.isOnlineCourses(), instructor.getYearsOfExperience())) {
            biConsumer.accept(instructor.getName(), instructor.getCourses());
         }
      });
   }

}
