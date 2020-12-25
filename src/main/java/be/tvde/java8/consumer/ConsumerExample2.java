package be.tvde.java8.consumer;

import java.util.function.Consumer;
import be.tvde.java8.model.Instructor;
import be.tvde.java8.model.Instructors;

public class ConsumerExample2 {

   public static void main(String[] args) {
      Consumer<Instructor> c = (instructor) -> {
         System.out.println(instructor);
      };
      Instructors.getAll().forEach(c);
      System.out.println("------------------");
      Consumer<Instructor> c1 = (instructor) -> {
         System.out.println(instructor.getName());
      };
      Instructors.getAll().forEach(c1);

      System.out.println("------------------");
      Instructors.getAll().forEach(c.andThen(c1));
      System.out.println("------------------");
      Instructors.getAll().forEach((instructor) -> {
            if(instructor.getYearsOfExperience() > 10) {
               c1.accept(instructor);
            }
      });
      System.out.println("------------------");
      Instructors.getAll().forEach(c1.andThen(c)::accept);
   }
}
